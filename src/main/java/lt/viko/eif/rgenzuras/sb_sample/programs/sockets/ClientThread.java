package lt.viko.eif.rgenzuras.sb_sample.programs.sockets;

import lt.viko.eif.rgenzuras.sb_sample.db.DatabaseContext;
import lt.viko.eif.rgenzuras.sb_sample.model.Customer;
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

    private final DatabaseContext ctx;

    private final Logger Log = LoggerFactory
            .getLogger(ClientThread.class);

    private final PrintStream Console = System.out;

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
        } catch (RuntimeException e) {
            try {
                in.close();
                out.close();
                client.close();
            } catch (IOException ex) {
                Log.warn("Failed graceful shutdown of socket", ex);
            }
        }
    }

    private void HandleTransaction() {
        var customers = ctx.ListCustomers();

        var customersList = new ArrayList<String>();

        for (Customer value : customers) {
            customersList.add(String.format("%s %s", value.getName(), value.getSurname()));
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
        int maxLength = 0;

        for (var item : items) {
            maxLength = Math.max(maxLength, item.getItemName().length() + 3);
        }

        int finalMaxLength = maxLength;
        var itemList = items.stream().map(item -> {
            var itemPrice = String.format("%.2f", item.getItemPrice());

            return String.format("%s %s", item.getItemName(), itemPrice.indent(finalMaxLength - item.getItemName().length()));
        }).toList();
        
        while(true) {

            selection = PaginatedMenu(itemList, "End transaction");

            if(selection == -1)
                break;

            order.getPurchasedItems().add(items.get(selection));
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

        while(true) {
            int selection = 10;

            String output = "";

            if(options.size() > itemsPerPage)
                output += String.format("Page %d / %d\n", page + 1, pageCount);

            StringBuilder outputBuilder = new StringBuilder(output);
            for (int i = page * itemsPerPage; (i < options.size()) && (i < ((page + 1) * itemsPerPage)); i++) {
                int label = i % itemsPerPage;

                outputBuilder.append(String.format("[%d] %s\n", label + 1, options.get(i)));
            }
            output = outputBuilder.toString();

            if(page > 0)
                output += "[8] Previous page\n";

            if(page < pageCount - 1)
                output += "[9] Next page\n";

            output += "[0] " + CancelMessage;

            output = output.replace("\n\n", "\n");
            out.println(output.replace('\n', '~'));

            while(selection < 0 || selection > 9)
                selection = in.nextInt();

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
