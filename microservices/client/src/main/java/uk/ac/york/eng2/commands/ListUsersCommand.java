package uk.ac.york.eng2.commands;

import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.clients.VideosClient;
import uk.ac.york.eng2.domain.Video;

@CommandLine.Command(
        name = "list-users",
        description = "lists all videos in the database",
        mixinStandardHelpOptions = true)
public class ListUsersCommand implements Runnable {
    @Inject
    private VideosClient client;

    @Override
    public void run() {
        System.out.println("Listing all videos:");

        Iterable<Video> video = client.list();
        for (Video v : video) {
            System.out.println(v.getId() + " - " + v.getTitle());
        }
    }
}
