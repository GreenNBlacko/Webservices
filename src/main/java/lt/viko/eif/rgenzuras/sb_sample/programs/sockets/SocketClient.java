package lt.viko.eif.rgenzuras.sb_sample.programs.sockets;

import lt.viko.eif.rgenzuras.sb_sample.model.Order;
import lt.viko.eif.rgenzuras.sb_sample.model.Receipt;
import lt.viko.eif.rgenzuras.sb_sample.programs.Application;
import lt.viko.eif.rgenzuras.sb_sample.util.MarshallerXML;
import lt.viko.eif.rgenzuras.sb_sample.util.ValidatorXSD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;


/**
 * Class implementing the socket client application to communicate with (and send information to) the server
 *
 * @author ramunas.genzuras@stud.viko.lt
 * @see SocketServer
 */
public class SocketClient implements Application {
	private final Logger Log = LoggerFactory
			.getLogger(SocketClient.class);

	private final PrintStream Console = System.out;
	private final java.util.Scanner Scanner = new Scanner(System.in);

	private Scanner in;
	private PrintWriter out;

	private Thread shutdownHook;

	private Socket sock = null;

	private final MarshallerXML marshaller = new MarshallerXML();
	private final ValidatorXSD validator = new ValidatorXSD();

	@Override
	public void Run() {
		boolean serverConnected = false;

		shutdownHook = new Thread(this::Close);

		Runtime.getRuntime().addShutdownHook(shutdownHook);

		Log.info("Starting the client...");

		while (!serverConnected) {
			Log.info("Attempting to connect to server on port 2025...");
			try {
				Thread.sleep(500);

				sock = new Socket("localhost", 2025);

				Log.info("Connection made successfully! Awaiting commands...");
				serverConnected = true;
			} catch (Exception e) {
				Log.warn("Connection has failed, retrying in 5 seconds...");
				try {
					Thread.sleep(5000);
				} catch (Exception ignored) { }
			}
		}

		try {
			// Get the required IO
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new Scanner(sock.getInputStream());

			Log.info("Sending welcome message.");

			out.println("Hello server! I am client.");
			Log.info("Awaiting response from the server...");
			var response = in.nextLine();
			Console.println(response);
			while (!sock.isClosed()) {
				Console.println("Select your desired operation");
				Console.println("[1] Start transaction");
				Console.println("[2] View transaction history");
				Console.println("[0] Disconnect");
				Console.print("Your choice: ");

				var selection = Scanner.nextInt();

				out.println(selection);

				switch (selection) {
					case 1:
						HandleTransaction();
						break;

					case 2:
						ViewTransactions();
						break;

					default:
						Log.info("Closing socket");
						Close();
						break;
				}
			}
		} catch (Exception e) {
			// java, just why... I have to induce a crash just to jump to a different location.. thanks a lot, whoever decided this is good.
			// entirely your fault. Coulda just been a simple goto, but oh well.
			// but wait, we still gotta log it cause I just know shit's gonna fuck up, and then I'm gonna wonder why the app closed. thanks again
			Log.error(e.getMessage());
		}

		Runtime.getRuntime().removeShutdownHook(shutdownHook);

		Log.info("Client going offline...");
	}

	public void Close() {
		Log.info("Client disconnection requested");
		try {
			new PrintWriter(sock.getOutputStream(), true).println(0); // Attempt graceful shutdown
			sock.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void HandleTransaction() {
		int selection = -1;

		while(selection != 0) {
			Console.println(in.nextLine().replace('~', '\n'));
			Console.print("Your selection: ");

			selection = Scanner.nextInt();

			out.println(selection);
		}

		Console.println("Transaction ended. Printing the order summary...");
		var xmlData = in.nextLine();
		Log.info("Validating XML data received...");
		if(!validator.validate(xmlData, "schema/receipt.xsd")) {
			Log.error("Validation failed! Data received:\n%s", xmlData);
			return;
		}
		Log.info("Data successfully validated!");
		var receipt = marshaller.Unmarshal(Receipt.class, xmlData);
		Console.println(receipt.toString());
	}

	private void ViewTransactions() {
		int selection = -1;

		while(selection < 0 || selection > 9) {
			Console.println(in.nextLine().replace('~', '\n'));
			Console.print("Your selection: ");

			selection = Scanner.nextInt();

			out.println(selection);
		}

		if(selection == 0)
			return;

		Console.println("Printing selected order...");
		var xmlData = in.nextLine();
		Log.info("Validating XML data received...");
		if(!validator.validate(xmlData, "schema/order.xsd")) {
			Log.error("Validation failed! Data received:\n%s", xmlData);
			return;
		}
		Log.info("Data successfully validated!");
		var order = marshaller.Unmarshal(Order.class, xmlData);
		Console.println(order.toString());
	}
}
