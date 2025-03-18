package lt.viko.eif.rgenzuras.sb_sample;

import lt.viko.eif.rgenzuras.sb_sample.programs.soap.SOAPApplication;
import lt.viko.eif.rgenzuras.sb_sample.programs.sockets.SocketApplication;
import org.apache.commons.lang3.NotImplementedException;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Main application serving to launch the child applications, such as sockets or webservices
 * @author ramunas.genzuras@stud.viko.lt
 * @see SocketApplication
 */

public class SpringbootSampleApplication {
	private final PrintStream Console = System.out;

	private final Scanner Scanner = new Scanner(System.in);

	private final String[] Choices = {"Socket application", "SOAP endpoint", "REST endpoint"};

	/**
	 * Entry point for the program
	 * @param args launch arguments
	 */
	public static void main(String[] args) {
        try {
            new SpringbootSampleApplication().run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	public void run() throws Exception {
		Console.println("Application is starting up...");

		var choice = -1;

		Console.println("Welcome!");
		while (choice != 0) {
			Console.println("Please select the program you wish to run");

			for (int i = 0; i < Choices.length; i++) {
				Console.printf("[%d] %s%n", i + 1, Choices[i]);
			}

			Console.println("[0] Exit");

			Console.print("Your choice: ");

			choice = Scanner.nextInt();

			switch (choice) {
				case 1:
					SocketApplication.Run();
					break;

				case 2:
					new SOAPApplication().Run();
					break;

				case 3:
					throw new NotImplementedException("REST endpoint not currently supported.");

				case 0:
					break;

				default:
					Console.println("Option does not exist. Assuming exit selection...");
					choice = 0;
					break;
			}
		}

		Console.println("Application has finished running");
	}
}
