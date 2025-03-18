package lt.viko.eif.rgenzuras.sb_sample.programs.sockets;

import lt.viko.eif.rgenzuras.sb_sample.db.DatabaseContext;
import lt.viko.eif.rgenzuras.sb_sample.programs.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Application for starting socket communications
 * @author ramunas.genzuras@stud.viko.lt
 * @see SocketServer
 * @see SocketClient
 */

@EntityScan(basePackages = "lt.viko.eif.rgenzuras.sb_sample.model")
@SpringBootApplication(scanBasePackages = {"lt.viko.eif.rgenzuras.sb_sample.db", "lt.viko.eif.rgenzuras.sb_sample.util"})
@EnableJpaRepositories(basePackages = "lt.viko.eif.rgenzuras.sb_sample.db.repositories")
public class SocketApplication implements CommandLineRunner {
    @Autowired
    private DatabaseContext ctx;

    private final Logger Log = LoggerFactory
            .getLogger(SocketApplication.class);

    private final PrintStream Console = System.out;
    private final Scanner Scanner = new Scanner(System.in);

    public static void Run() {
        var springApp = new SpringApplication(SocketApplication.class);
        springApp.setWebApplicationType(WebApplicationType.NONE);
        var context = springApp.run();
        SpringApplication.exit(context, () -> 0);
    }

    @Override
    public void run(String... args) throws Exception {
        Log.info("Now starting the socket application...");

        var choice = -1;

                while(choice != 0) {
                    Console.println("Select which type of socket you would like to run");
                    Console.println("[1] Server");
                    Console.println("[2] Client");
                    Console.println("[0] Return to program selection");
                    Console.print("Your choice: ");
                    choice = Scanner.nextInt();

                    Application app = null;

                    switch (choice) {
                        case 1:
                            app = new SocketServer(ctx);
                            break;

                        case 2:
                            app = new SocketClient();
                            break;

                        default:
                            if(choice == 0) break;

                            Log.warn("Option does not exist. Assuming exit selection...");
                            choice = 0;
                    }

                    if (app != null)
                        app.Run();
                }

                Log.info("Socket application going offline. Goodbye, world!");
    }
}
