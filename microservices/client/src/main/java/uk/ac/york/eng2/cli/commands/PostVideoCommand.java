package uk.ac.york.eng2.cli.commands;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.cli.clients.UsersClient;
import uk.ac.york.eng2.cli.clients.VideosClient;
import uk.ac.york.eng2.cli.domain.User;
import uk.ac.york.eng2.cli.dto.HashtagDTO;
import uk.ac.york.eng2.cli.dto.VideoDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CommandLine.Command(
        name = "post-video",
        description = "adds a new video record to the database",
        mixinStandardHelpOptions = true)
public class PostVideoCommand implements Runnable {
    @Inject
    private VideosClient client;

    @Inject
    private UsersClient userClient;
    @CommandLine.Parameters(index = "0")
    private String title;
    @CommandLine.Parameters(index = "1")
    private Long userId;
    @CommandLine.Parameters(index = "2..")
    private List<String> hashtags;

    @Override
    public void run() {
        User user = userClient.getUser(userId);
        if (user == null) {
            System.out.println("User with id " + userId + " does not exist");
            return;
        }
        VideoDTO newVideo = new VideoDTO();
        newVideo.setTitle(title);
        newVideo.setPostedBy(userId);
        Set<HashtagDTO> hashtagDTOS = hashtags.stream().map(HashtagDTO::new).collect(Collectors.toSet());
        newVideo.setHashtags(hashtagDTOS);
        HttpResponse<Void> result = client.add(newVideo);
        Long videoId = result.code() == HttpStatus.CREATED.getCode() ? Long.parseLong(result.header("location").split("/")[2]) : null;
        if (result.code() == HttpStatus.CREATED.getCode()) {
            System.out.println("Successfully created video with id " + videoId);
        }
    }
}
