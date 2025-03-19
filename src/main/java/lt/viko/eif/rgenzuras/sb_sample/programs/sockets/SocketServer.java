package lt.viko.eif.rgenzuras.sb_sample.programs.sockets;

import lt.viko.eif.rgenzuras.sb_sample.db.DatabaseContext;
import lt.viko.eif.rgenzuras.sb_sample.programs.Application;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class implementing the socket server application to communicate with (and receive information from) the client
 * @author ramunas.genzuras@stud.viko.lt
 * @see SocketClient
 */
public class SocketServer implements Application {
    private final DatabaseContext ctx;

    public SocketServer(DatabaseContext ctx) {
        this.ctx = ctx;
    }

    private final Logger Log = LoggerFactory
            .getLogger(SocketServer.class);

    private final PrintStream Console = System.out;
    private final java.util.Scanner Scanner = new Scanner(System.in);

    private ServerSocket server = null;
    private final ArrayList<Pair<Thread, ClientThread>> threads = new ArrayList<>();

    @Override
    public void Run() {
        Log.info("Server going online on port 2025");
        try {
            server = new ServerSocket(2025);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Thread shutdownHook = new Thread(this::Close);
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        var exitThread = new Thread(() -> {
            Console.println("Press 0 to exit.");
            while (!server.isClosed()) if(Scanner.nextInt() == 0) Close();
        });

        exitThread.start();

        while(!server.isClosed()) {
            try {
                var client = server.accept();

                Log.info("Client connected with IP " + client.getLocalAddress());

                var app = new ClientThread(client, ctx);
                var thread = new Thread(app::Run);
                thread.start();

                threads.add(Pair.of(thread, app));
            } catch (IOException ignored) { }
        }

        Log.info("Server going offline! Goodbye world!");
        Runtime.getRuntime().removeShutdownHook(shutdownHook);
    }

    public void Close() {
        for (Pair<Thread, ClientThread> thread : threads) {
            thread.getRight().Close();
            thread.getLeft().interrupt();
        }

	    try {
		    server.close();
	    } catch (IOException e) {
		    throw new RuntimeException(e);
	    }
    }
}
