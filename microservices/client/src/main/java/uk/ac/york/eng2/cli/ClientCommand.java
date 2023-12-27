package uk.ac.york.eng2.cli;

import io.micronaut.configuration.picocli.PicocliRunner;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import uk.ac.york.eng2.cli.commands.*;

@Command(name = "client", description = "...",
        mixinStandardHelpOptions = true,
        subcommands = {AddUserCommand.class,
                PostVideoCommand.class,
                LikeVideoCommand.class,
                DislikeVideoCommand.class,
                TrendingHashtagsCommand.class,
        ListVideosCommand.class,
        ListUsersCommand.class,
        GetVideoCommand.class,
        WatchVideoCommand.class,
        GetUserCommand.class})
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
