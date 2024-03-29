package uk.ac.york.eng2.cli.commands;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.cli.clients.*;
import uk.ac.york.eng2.cli.domain.Hashtag;
import uk.ac.york.eng2.cli.domain.User;
import uk.ac.york.eng2.cli.clients.SubscriptionClient;

import java.util.List;

@CommandLine.Command(
        name = "subscription-videos",
        description = "Get videos from a subscription",
        mixinStandardHelpOptions = true)
public class SubscriptionVideosCommand implements Runnable {

    @Inject
    private HashtagsClient hashtagsClient;

    @Inject
    private VideosClient videosClient;

    @Inject
    private UsersClient userClient;

    @Inject
    private SubscriptionClient subscriptionClient;
    @CommandLine.Option(names = "-id")
    private Long hashtagId;
    @CommandLine.Option(names = "-name")
    private String hashtagName;
    @CommandLine.Parameters(index = "0")
    private Long userId;




    @Override
    public void run() {

        User user = userClient.getUser(userId);
        if (user == null) {
            System.out.println("User with id " + userId + " does not exist");
            return;
        }
        if (hashtagId == null) {
            Iterable<Hashtag> hashtagsAll = hashtagsClient.list();

            for (Hashtag hashtag : hashtagsAll) {
                if (hashtag.getName().equals(hashtagName)) {
                    hashtagId = hashtag.getId();
                    break;
                }
            }
            if (hashtagId == null) {
                System.out.println("Hashtag with name " + hashtagName + " does not exist");
                return;
            }
        }

        Hashtag hashtag = hashtagsClient.getHashtag(hashtagId);
        if (hashtag == null) {
            System.out.println("Hashtag with id " + hashtagId + " does not exist");
            return;
        }
        List<Long> result = subscriptionClient.listVideosSubscription(userId, hashtagId);
        if (result.isEmpty()) {
            System.out.println("User " +  userId + " has no videos to watch for hashtag " + hashtagId + " - #" + hashtag.getName());
        }
        else{
            System.out.println("User " + userId + " has the following videos to watch for hashtag " + hashtagId + " - #" + hashtag.getName() + ":");
            for (Long videoId : result) {
                System.out.println("Video " + videoId + " - " + videosClient.getVideo(videoId).getTitle());
            }
        }
    }
}
