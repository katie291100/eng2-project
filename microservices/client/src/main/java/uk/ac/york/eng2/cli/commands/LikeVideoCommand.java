package uk.ac.york.eng2.cli.commands;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.cli.clients.UsersClient;
import uk.ac.york.eng2.cli.clients.VideosClient;
import uk.ac.york.eng2.cli.domain.User;
import uk.ac.york.eng2.cli.domain.Video;

@CommandLine.Command(
        name = "like-video",
        description = "likes a video as a user",
        mixinStandardHelpOptions = true)
public class LikeVideoCommand implements Runnable {
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
            System.out.println("Video with id " + videoId + " does not exist");
            return;
        }
        HttpResponse<Void> result = client.likeVideo(videoId, userId);
        if (result.code() == HttpStatus.OK.getCode()) {
            System.out.println("Successfully liked video with id " + videoId);
        }
    }
}
