package lt.viko.eif.rgenzuras.sb_sample.util;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class ListSelection {
    private final static PrintStream Console = System.out;

    /**
     * Displays a selection menu with a list of options to choose from
     * @param options The options to display
     * @return Selected option index
     * @apiNote Use ExitOption to provide a label for the exit selection
     */
    public static int DisplaySelection(List<String> options) { return DisplaySelection(options, "Cancel"); }

    /**
     * Displays a selection menu with a list of options to choose from
     * @param options The options to display
     * @param ExitOption Label of the exit selection (optional)
     * @return Selected option index
     */
    public static int DisplaySelection(List<String> options, String ExitOption) {
        var Scanner = new Scanner(System.in);

        for (int i = 0; i < options.size(); i++) {
            Console.printf("[%d] %s%n", i + 1, options.get(i));
        }

        Console.printf("[0] %s%n", ExitOption);
        Console.print("Your choice: ");

        return Scanner.nextInt();
    }
}
