package lt.viko.eif.rgenzuras.sb_sample.programs.soap;

import lt.viko.eif.rgenzuras.sb_client.soap.*;
import lt.viko.eif.rgenzuras.sb_sample.programs.Application;
import lt.viko.eif.rgenzuras.sb_sample.util.*;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class SOAPClient implements Application {
    private final StorePort client = new StorePortService().getStorePortSoap11();

    private final PrintStream Console = System.out;
    private final Scanner Scanner = new Scanner(System.in);

    private final List<String> FunctionGroups = List.of(new String[]{"Items", "Customers"});
    private final List<String> Operations = List.of(new String[]{"Get", "Add", "Remove"});
    private final List<String> DisplayTypes = List.of(new String[]{"XML", "HTML", "PDF"});

    private int groupSelection;
    private int operationSelection;

    private final Marshaller marshaller = new MarshallerXML();
    private final Validator validator = new ValidatorXSD();
    private final Transformer html = new TransformerHTML();
    private final Transformer pdf = new TransformerPDF();

    @Override
    public void Run() {
        while (true) {
            String xml = "";
            String xsl = "transform/modify";

            SelectGroup();

            SelectOperation();

            switch (groupSelection) {
                case 1:
                    switch (operationSelection) {
                        case 1:
                            Scanner.reset();
                            Console.print("Enter the ID of the item you wish to get: ");
                            int getID = Scanner.nextInt();
                            var getItemRequest = new GetItemRequest();
                            getItemRequest.setID(getID);
                            var getItemResponse = client.getItem(getItemRequest);
                            xml = marshaller.Marshal(getItemResponse, true);
                            xsl = "transform/getItem";
                            break;

                        case 2:
                            Scanner.reset();
                            Console.print("Enter the new item's name: ");
                            var name = Scanner.nextLine();

                            Scanner.reset();
                            Console.print("Enter the new item's price: ");
                            var price = Scanner.nextFloat();

                            var addItemRequest = new AddItemRequest();
                            var addItem = new Item();

                            addItem.setName(name);
                            addItem.setPrice(price);

                            addItemRequest.setItem(addItem);

                            var addItemResponse = client.addItem(addItemRequest);
                            xml = marshaller.Marshal(addItemResponse, true);
                            break;

                        case 3:
                            Scanner.reset();
                            Console.print("Enter the ID of the item you wish to remove: ");
                            int removeID = Scanner.nextInt();
                            var removeItemRequest = new RemoveItemRequest();
                            removeItemRequest.setID(removeID);

                            var removeItemResponse = client.removeItem(removeItemRequest);
                            xml = marshaller.Marshal(removeItemResponse, true);
                            break;

                        default:
                            Console.println("Invalid selection, assuming exit");
                            continue;
                    }
                    break;

                case 2:
                    switch (operationSelection) {
                        case 1:
                            Scanner.reset();
                            Console.print("Enter the ID of the customer you wish to get: ");
                            int getID = Scanner.nextInt();

                            var getCustomerRequest = new GetCustomerRequest();
                            getCustomerRequest.setID(getID);

                            var getCustomerResponse = client.getCustomer(getCustomerRequest);
                            xml = marshaller.Marshal(getCustomerResponse, true);
                            xsl = "transform/getCustomer";
                            break;

                        case 2:
                            Scanner.reset();
                            Console.print("Enter the new customer's name: ");
                            var name = Scanner.nextLine();

                            Scanner.reset();
                            Console.print("Enter the new customer's surname: ");
                            var surname = Scanner.nextLine();

                            Scanner.reset();
                            Console.print("Enter the new customer's email: ");
                            var email = Scanner.nextLine();

                            var addCustomerRequest = new AddCustomerRequest();
                            var addCustomer = new Customer();

                            addCustomer.setName(name);
                            addCustomer.setSurname(surname);
                            addCustomer.setEmail(email);

                            addCustomerRequest.setCustomer(addCustomer);

                            var addCustomerResponse = client.addCustomer(addCustomerRequest);
                            xml = marshaller.Marshal(addCustomerResponse, true);
                            break;

                        case 3:
                            Scanner.reset();
                            Console.print("Enter the ID of the customer you wish to remove: ");
                            int removeID = Scanner.nextInt();
                            var removeCustomerRequest = new RemoveCustomerRequest();
                            removeCustomerRequest.setID(removeID);

                            var removeCustomerResponse = client.removeCustomer(removeCustomerRequest);
                            xml = marshaller.Marshal(removeCustomerResponse, true);
                            break;

                        default:
                            Console.println("Invalid selection, assuming exit");
                            continue;
                    }
                    break;

                case 0:
                    return;

                default:
                    Console.println("Invalid selection, assuming exit...");
            }

            if(!validator.validate(xml, "schema/store.xsd")) {
                Console.println("Validation failed. Exiting...");
                System.exit(1);
            }

            var displaySelection = ListSelection.DisplaySelection(DisplayTypes, "Cancel");

            switch (displaySelection) {
                case 1:
                    WriteToFile("output.xml", xml);

                    Console.println("Response written to output.xml");
                    break;

                case 2:
                    try {
                        var htmlTransformed = html.Transform(xml, xsl + "HTML.xsl");
                        WriteToFile("output.html", htmlTransformed);

                        Console.println("Response written to output.html");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 3:
                    try {
                        var pdfTransformed = pdf.Transform(xml, xsl + "PDF.xsl");
                        WriteToFile("output.pdf", java.util.Base64.getDecoder().decode(pdfTransformed));

                        Console.println("Response written to output.pdf");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
            }
        }
    }

    private void WriteToFile(String filename, String data) {
        var output = new File(filename);

        try {
            if (!output.exists())
                output.createNewFile();

            FileWriter stream = null;
            stream = new FileWriter(output);

            stream.write(data);
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void WriteToFile(String filename, byte[] data) {
        var output = new File(filename);

        try {
            if (!output.exists())
                output.createNewFile();

            FileOutputStream stream = null;
            stream = new FileOutputStream(output);

            stream.write(data);
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void SelectGroup() {
        Console.println("Select the function group");
        groupSelection = ListSelection.DisplaySelection(FunctionGroups);

        if (groupSelection == 0)
            System.exit(0);
    }

    private void SelectOperation() {
        Console.println("Select the operation");
        operationSelection = ListSelection.DisplaySelection(Operations);

        if (operationSelection == 0)
            Run();
    }
}
