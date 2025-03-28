package lt.viko.eif.rgenzuras.sb_sample.programs.soap;

import lt.viko.eif.rgenzuras.sb_sample.programs.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@EntityScan(basePackages = {"lt.viko.eif.rgenzuras.sb_sample.model", "lt.viko.eif.rgenzuras.sb_sample.schema"})
@SpringBootApplication(scanBasePackages = {"lt.viko.eif.rgenzuras.sb_sample.db", "lt.viko.eif.rgenzuras.sb_sample.util", "lt.viko.eif.rgenzuras.sb_sample.programs.soap"})
@EnableJpaRepositories(basePackages = "lt.viko.eif.rgenzuras.sb_sample.db.repositories")
public class SOAPServer implements Application {
    private final PrintStream Console = System.out;
    private final Scanner Scanner = new Scanner(System.in);

    private final CountDownLatch latch = new CountDownLatch(1);

    @Override
    public void Run() {
        Console.println("Starting SOAP application...");

        AtomicReference<ApplicationContext> context = new AtomicReference<>();

        var thread = new Thread(() -> {
            var application = new SpringApplication(SOAPServer.class);
            application.setWebApplicationType(WebApplicationType.SERVLET);
            context.set(application.run());

            latch.countDown();
        });
        thread.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Console.println("SOAP application started successfully.");
        Console.println("Press 0 to exit.");

        while (true) if (Scanner.nextInt() == 0) break;
        Console.println("SOAP application exiting... Goodbye, world!");
        SpringApplication.exit(context.get(), () -> 0);
    }
}
