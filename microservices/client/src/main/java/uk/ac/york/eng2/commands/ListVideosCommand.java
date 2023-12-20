package uk.ac.york.eng2.commands;

import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.clients.UsersClient;
import uk.ac.york.eng2.clients.VideosClient;
import uk.ac.york.eng2.domain.User;
import uk.ac.york.eng2.domain.Video;

@CommandLine.Command(
        name = "list-videos",
        description = "lists all videos in the database",
        mixinStandardHelpOptions = true)
public class ListVideosCommand implements Runnable {
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
