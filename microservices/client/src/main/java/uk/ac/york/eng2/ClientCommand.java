package uk.ac.york.eng2;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.micronaut.context.ApplicationContext;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import uk.ac.york.eng2.commands.*;

@Command(name = "client", description = "...",
        mixinStandardHelpOptions = true,
        subcommands = {AddUserCommand.class,
                PostVideoCommand.class,
                LikeVideoCommand.class,
                DislikeVideoCommand.class,
                TrendingHashtagsCommand.class})
public class ClientCommand implements Runnable {

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    @Option(names = {"-d", "--debug"}, description = "...")
    boolean debug;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(ClientCommand.class, args);
    }

    public void run() {
        // business logic here
        if (verbose) {
            System.out.println("Hi!");
        }
    }
}
