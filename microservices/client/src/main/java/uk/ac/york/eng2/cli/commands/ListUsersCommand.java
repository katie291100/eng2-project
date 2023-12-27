package uk.ac.york.eng2.cli.commands;

import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.cli.clients.UsersClient;
import uk.ac.york.eng2.cli.domain.User;

@CommandLine.Command(
        name = "list-users",
        description = "lists all users in the database",
        mixinStandardHelpOptions = true)
public class ListUsersCommand implements Runnable {
    @Inject
    private UsersClient client;

    @Override
    public void run() {
        System.out.println("Listing all users:");

        Iterable<User> users = client.list();
        for (User u : users) {
            System.out.println(u.getId() + " - " + u.getName());
        }
    }
}
