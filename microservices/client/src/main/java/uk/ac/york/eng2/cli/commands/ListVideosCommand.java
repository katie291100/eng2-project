package uk.ac.york.eng2.cli.commands;

import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.cli.clients.VideosClient;
import uk.ac.york.eng2.cli.domain.Video;

@CommandLine.Command(
        name = "list-videos",
        description = "lists all users in the database",
        mixinStandardHelpOptions = true)
public class ListVideosCommand implements Runnable {
    @Inject
    private VideosClient client;

    @Override
    public void run() {
        System.out.println("Listing all videos:");

        Iterable<Video> videos = client.list();
        for (Video v : videos) {
            System.out.println(v.getId() + " - " + v.getTitle() + " - " + "Hashtags: [" + (v.getHashtags().size()>0 ? v.getHashtags().stream().map(h -> h.getName()).reduce("", (a, b) -> a + ", " + b).substring(2): "") + "]" + " - Posted By " + v.getPostedBy().getName());
        }
    }
}
