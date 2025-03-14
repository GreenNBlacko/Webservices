package lt.viko.eif.rgenzuras.sb_sample.programs.sockets;

import lt.viko.eif.rgenzuras.sb_sample.db.DatabaseContext;
import lt.viko.eif.rgenzuras.sb_sample.model.Order;
import lt.viko.eif.rgenzuras.sb_sample.model.Receipt;
import lt.viko.eif.rgenzuras.sb_sample.programs.Application;
import lt.viko.eif.rgenzuras.sb_sample.util.MarshallerXML;
import lt.viko.eif.rgenzuras.sb_sample.util.ValidatorXSD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class used for client communication threads, so that the server program would be able to have many clients connected at any given time
 * @author ramunas.genzuras@stud.viko.lt
 * @see SocketServer
 * @see SocketClient
 */
public class ClientThread implements Application {
    private final Socket client;

    private DatabaseContext ctx;

    private final Logger Log = LoggerFactory
            .getLogger(ClientThread.class);

    private final PrintStream Console = System.out;
    private final java.util.Scanner Scanner = new Scanner(System.in);

    private final Scanner in;
    private final PrintWriter out;

    private final MarshallerXML marshaller = new MarshallerXML();
    private final ValidatorXSD validator = new ValidatorXSD();

    public ClientThread(Socket client, DatabaseContext ctx) {
        this.ctx = ctx;

        this.client = client;

        try {
            out = new PrintWriter(client.getOutputStream(), true);
            in = new Scanner(client.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void Run() {
        try {
            in.nextLine();

            out.println("Welcome client! I'm server, waiting for instructions");

            // 1 - start transaction, 2 - view transaction history, 0 - disconnect
            var choice = in.nextInt();
            while (choice != 0) {
                Console.println(choice);
                switch (choice) {
                    case 1: // start transaction
                        HandleTransaction();
                        break;

                    case 2: // view transaction history
                        ViewTransactions();
                        break;

                    default:
                        out.println("Unsupported operation! try again");
                        break;
                }
                choice = in.nextInt();
            }

            Console.println("Client has disconnected! Connection closing...");
        } catch (RuntimeException e) { throw new RuntimeException(e); }
    }

    private void HandleTransaction() {
        var customers = ctx.ListCustomers();

        var customersList = new ArrayList<String>();

        for (int i = 0; i < customers.size(); i++) {
            customersList.add(String.format("%s %s", customers.get(i).getName(), customers.get(i).getSurname()));
        }

        var selection = PaginatedMenu(customersList); // selecting the customer

        Console.println("Customer selection: " + selection);

        if(selection == -1){
            Console.println("Transaction initiation cancelled.. exiting the transaction");
            out.println("Transaction initiation cancelled");
            return;
        }

        var customer = customers.get(selection);

        var order = ctx.createOrder(customer);

        Console.println("Order created");

        var items = ctx.ListItems();

        var itemList = items.stream().map(item -> String.format("%s\t\t\t%f", item.getItemName(), item.getItemPrice())).toList();
        
        while(true) {

            selection = PaginatedMenu(itemList, "End transaction");

            Console.println("Order selection: " + selection);

            if(selection == -1)
                break;

            order.getPurchasedItems().add(items.get(selection));
            Console.printf("Added %s to the order #%d", items.get(selection).getItemName(), order.getID());
        }

        ctx.UpdateOrder(order);

        var receipt = new Receipt(customer, order.getPurchasedItems());
        var xml = marshaller.Marshal(receipt);
        Console.println(marshaller.Marshal(receipt, true));
        if(!validator.validate(xml, "schema/receipt.xsd"))
            throw new RuntimeException("Validation failed");

        out.println(xml);
    }

    private void ViewTransactions() {
        var orders = ctx.ListOrders();

        var ordersList = orders.stream().map(order -> String.format("Order %d: %s", order.getID(), order.getPurchasingCustomer().toString())).toList();

        var selection = PaginatedMenu(ordersList);

        if(selection == -1)
            return;

        var order = orders.get(selection);

        var xml = marshaller.Marshal(order);
        Console.println(marshaller.Marshal(order, true));
        if(!validator.validate(xml, "schema/order.xsd"))
            throw new RuntimeException("Validation failed");

        out.println(xml);
    }

    public void Close() {
        try {
            client.close();
        } catch (IOException ignored) { }
    }

    private int PaginatedMenu(List<String> options) {
        return PaginatedMenu(options, "Cancel");
    }

    private int PaginatedMenu(List<String> options, String CancelMessage) {
        int itemsPerPage = 7;
        int page = 0;
        int pageCount = (int)Math.ceil((double)options.size() / itemsPerPage);

        int selection = 10;

        while(true) {
            String output = "";

            if(options.size() > itemsPerPage)
                output += String.format("Page %d / %d\n", page + 1, pageCount);

            for (int i = page * pageCount; i < options.size() && i < (page + 1) * itemsPerPage; i++) {
                int label = i % itemsPerPage;

                output += String.format("[%d] %s\n", label + 1, options.get(i));
            }

            if(page > 0)
                output += "[8] Previous page\n";

            if(page < pageCount - 1)
                output += "[9] Next page\n";

            output += "[0] " + CancelMessage;

            out.println(output.replace('\n', '~'));

            while(selection < 0 || selection > 9)
                selection = in.nextInt();

            Console.println(selection);

            switch (selection) {
                case 0:
                    return -1;

                case 8: // previous page
                    if(page == 0)
                        break;

                    page--;
                    break;

                case 9: // next page
                    if(page == pageCount - 1)
                        break;

                    page++;
                    break;

                default: // 1 - 7
                    return selection - 1 + page * itemsPerPage;
            }
        }
    }
}
