package uk.ac.york.eng2.commands;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.clients.UsersClient;
import uk.ac.york.eng2.clients.VideosClient;
import uk.ac.york.eng2.domain.User;
import uk.ac.york.eng2.domain.Video;

@CommandLine.Command(
        name = "dislike-video",
        description = "adds a new video record to the database",
        mixinStandardHelpOptions = true)
public class DislikeVideoCommand implements Runnable {
    @Inject
    private VideosClient client;

    @Inject
    private UsersClient userClient;
    @CommandLine.Parameters(index = "0")
    private Long userId;
    @CommandLine.Parameters(index = "1")
    private Long videoId;

    @Override
    public void run() {
        User user = userClient.getUser(userId);
        if (user == null) {
            System.out.println("User with id " + userId + " does not exist");
            return;
        }
        Video video = client.getVideo(videoId);
        if (video == null) {
            System.out.println("Video with id " + userId + " does not exist");
            return;
        }
        HttpResponse<Void> result = client.dislikeVideo(videoId, userId);
        if (result.code() == HttpStatus.OK.getCode()) {
            System.out.println("Successfully disliked video with id " + videoId);
        }
    }
}