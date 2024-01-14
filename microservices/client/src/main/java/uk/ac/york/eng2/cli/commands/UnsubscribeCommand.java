package uk.ac.york.eng2.cli.commands;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.cli.clients.HashtagsClient;
import uk.ac.york.eng2.cli.clients.SubscriptionClient;
import uk.ac.york.eng2.cli.clients.TrendingClient;
import uk.ac.york.eng2.cli.clients.UsersClient;
import uk.ac.york.eng2.cli.domain.Hashtag;
import uk.ac.york.eng2.cli.domain.User;

import static java.lang.Thread.sleep;

@CommandLine.Command(
        name = "unsubscribe",
        description = "unsubscribe to a hashtag",
        mixinStandardHelpOptions = true)
public class UnsubscribeCommand implements Runnable {

    @Inject
    private HashtagsClient hashtagsClient;

    @Inject
    private UsersClient userClient;

    @Inject
    private SubscriptionClient subscriptionClient;

    @CommandLine.Parameters(index = "0")
    private Long hashtagId;
    @CommandLine.Parameters(index = "1")
    private Long userId;


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
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        HttpResponse<Void> result = subscriptionClient.unsubscribe(hashtagId, userId);
        if (result.code() == 200) {
            System.out.println("Successfully unsubscribed user " + userId + " from hashtag " + hashtagId);
        }
        else{
            System.out.println("Failed to unsubscribe user " + userId + " from hashtag " + hashtagId);
        }
    }
}
