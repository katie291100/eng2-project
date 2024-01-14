package uk.ac.york.eng2.cli.commands;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.cli.clients.HashtagsClient;
import uk.ac.york.eng2.cli.clients.SubscriptionClient;
import uk.ac.york.eng2.cli.clients.TrendingClient;
import uk.ac.york.eng2.cli.clients.UsersClient;
import uk.ac.york.eng2.cli.domain.Hashtag;
import uk.ac.york.eng2.cli.domain.User;

@CommandLine.Command(
        name = "subscribe",
        description = "subscribe to a hashtag",
        mixinStandardHelpOptions = true)
public class SubscribeCommand implements Runnable {

    @Inject
    private TrendingClient trendingClient;

    @Inject
    private HashtagsClient hashtagsClient;

    @Inject
    private UsersClient userClient;

    @Inject
    private SubscriptionClient subscriptionClient;

    @CommandLine.Parameters(index = "0")
    private Long userId;
    @CommandLine.Parameters(index = "1")
    private Long hashtagId;


    @Override
    public void run() {

        User user = userClient.getUser(userId);
        if (user == null) {
            System.out.println("User with id " + userId + " does not exist");
            return;
        }

        Hashtag hashtag = hashtagsClient.getHashtag(hashtagId);
        if (hashtag == null) {
            System.out.println("Hashtag with id " + hashtagId + " does not exist");
            return;
        }

        HttpResponse<Void> result = subscriptionClient.subscribe(1L, 2L);
        System.out.println(result);
        System.out.println(result.code());
        System.out.println(result.status());
        System.out.println(result.body());
        if (result.code() == 201) {
            System.out.println("Successfully subscribed user " + userId + " to hashtag " + hashtagId + ": #" + hashtag.getName());
        }
        else{
            System.out.println("Failed to subscribe user " + userId + " to hashtag " + hashtagId);
        }
    }
}
