package lt.viko.eif.rgenzuras.sb_sample;

import lt.viko.eif.rgenzuras.sb_sample.programs.rest.RESTServer;
import lt.viko.eif.rgenzuras.sb_sample.programs.soap.SOAPClient;
import lt.viko.eif.rgenzuras.sb_sample.programs.soap.SOAPServer;
import lt.viko.eif.rgenzuras.sb_sample.programs.sockets.SocketApplication;
import lt.viko.eif.rgenzuras.sb_sample.util.ListSelection;
import org.springframework.core.io.ClassPathResource;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * Main application serving to launch the child applications, such as sockets or webservices
 * @author ramunas.genzuras@stud.viko.lt
 * @see SocketApplication
 */

public class SpringbootSampleApplication {
	private final PrintStream Console = System.out;

	private final Scanner Scanner = new Scanner(System.in);

	private final List<String> Choices = List.of(new String[]{"Socket application", "SOAP endpoint", "SOAP client", "REST endpoint"});

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

		Console.println(new ClassPathResource("banner.txt").getContentAsString(StandardCharsets.UTF_8));
		Console.println("Welcome!");
		while (choice != 0) {
			Console.println("Please select the program you wish to run");

			choice = ListSelection.DisplaySelection(Choices, "Exit");

			switch (choice) {
				case 1:
					SocketApplication.Run();
					break;

				case 2:
					new SOAPServer().Run();
					break;

				case 3:
					new SOAPClient().Run();
					break;

				case 4:
					new RESTServer().Run();
					break;

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
