package uk.ac.york.eng2.commands;

import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.clients.UsersClient;
import uk.ac.york.eng2.clients.VideosClient;
import uk.ac.york.eng2.domain.User;
import uk.ac.york.eng2.domain.Video;

import java.util.HashSet;
import java.util.Set;

@CommandLine.Command(
        name = "get-video",
        description = "lists videos in the database",
        mixinStandardHelpOptions = true)
public class GetVideoCommand implements Runnable {
    @Inject
    private VideosClient client;
    @Inject
    private UsersClient userClient;
    @CommandLine.Option(names = "-postedBy")
    private Long userId;
    @CommandLine.Option(names = "-hashtag")
    private String hashtag;
    @CommandLine.Option(names = "-id")
    private Long id;

    @Override
    public void run() {
        if (id != null){
            Video video = client.getVideo(id);
            if (video == null){
                System.out.println("Video with id " + id + " does not exist");
                return;
            }
            System.out.println(video.getId() + " - " + video.getTitle() + " - " + video.getHashtags());
            return;
        }
        Set<Video> videos = new HashSet<>();
        Set<Video> postedVideos = new HashSet<>();
        Set<Video> hashtagVideos = new HashSet<>();
        if (userId != null){
            User user = userClient.getUser(userId);

            if (user == null) {
                System.out.println("User with id " + userId + " does not exist");
                return;
            }
            postedVideos = userClient.getPostedByUser(userId);
        }

        if (hashtag != null){
            hashtagVideos = client.listVideosByHashtag(hashtag);
        }

        if (userId != null && hashtag != null){
            System.out.println("Videos posted by user " + userId + " with hashtag " + hashtag);
            videos.addAll(hashtagVideos);
            videos.retainAll(postedVideos);
        }
        else if (userId != null){
            System.out.println("Videos posted by user " + userId);

            videos.addAll(postedVideos);
        }
        else if (hashtag != null){
            System.out.println("Videos with hashtag " + userId);

            videos.addAll(hashtagVideos);
        }
        else{
            System.out.println("Error: no user or hashtag specified");
            return;
        }
        videos.forEach(v -> System.out.println(v.getId() + " - " + v.getTitle() + " - " + "Hashtags: [" + v.getHashtags().stream().map(h -> h.getName()).reduce("", (a, b) -> a + ", " + b).substring(2) + "]" + " - " + v.getPostedBy().getName()));

    }
}
