package lt.viko.eif.rgenzuras.sb_sample.programs.sockets;

import lt.viko.eif.rgenzuras.sb_sample.SpringbootSampleApplication;
import lt.viko.eif.rgenzuras.sb_sample.db.DatabaseContext;
import lt.viko.eif.rgenzuras.sb_sample.programs.Application;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Application for starting socket communications
 * @author ramunas.genzuras@stud.viko.lt
 * @see SocketServer
 * @see SocketClient
 */
public class SocketApplication implements Application {
    private DatabaseContext ctx;

    public SocketApplication(DatabaseContext ctx) {
        this.ctx = ctx;
    }

    private final Logger Log = LoggerFactory
            .getLogger(SocketApplication.class);

    private final PrintStream Console = System.out;
    private final Scanner Scanner = new Scanner(System.in);

    @Override
    public void Run() {
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
