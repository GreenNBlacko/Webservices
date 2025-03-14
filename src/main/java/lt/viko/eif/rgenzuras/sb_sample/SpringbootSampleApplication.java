package lt.viko.eif.rgenzuras.sb_sample;

import lt.viko.eif.rgenzuras.sb_sample.db.DatabaseContext;
import lt.viko.eif.rgenzuras.sb_sample.programs.Application;
import lt.viko.eif.rgenzuras.sb_sample.programs.sockets.SocketApplication;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Main application serving to launch the child applications, such as sockets or webservices
 * @author ramunas.genzuras@stud.viko.lt
 * @see SocketApplication
 */
@SpringBootApplication
public class SpringbootSampleApplication implements CommandLineRunner {
	@Autowired
	private DatabaseContext ctx;

	private final Logger Log = LoggerFactory
			.getLogger(SpringbootSampleApplication.class);

	private final PrintStream Console = System.out;

	private final Scanner Scanner = new Scanner(System.in);

	private final String[] Choices = {"Socket application", "SOAP endpoint", "REST endpoint"};

	/**
	 * Entry point for the program
	 * @param args launch arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringbootSampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Log.info("Application is starting up...");

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

			Application app = null;

			switch (choice) {
				case 1:
					app = new SocketApplication(ctx);
					break;

				case 2:
					throw new NotImplementedException("SOAP endpoint not currently supported.");

				case 3:
					throw new NotImplementedException("REST endpoint not currently supported.");

				default:
					Log.warn("Option does not exist. Assuming exit selection...");
					choice = 0;
			}

			if (app != null)
				app.Run();
		}

		Log.info("Application has finished running");
	}
}
