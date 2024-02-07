package uk.ac.york.eng2.cli.commands;

import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.cli.clients.*;
import uk.ac.york.eng2.cli.domain.Hashtag;
import uk.ac.york.eng2.cli.domain.User;

import java.util.List;

@CommandLine.Command(
        name = "user-subscriptions",
        description = "get users subscriptions",
        mixinStandardHelpOptions = true)
public class GetUserSubscriptionsCommand implements Runnable {

    @Inject
    private HashtagsClient hashtagsClient;

    @Inject
    private UsersClient userClient;

    @Inject
    private SubscriptionClient subscriptionClient;

    @CommandLine.Parameters(index = "0")
    private Long userId;

    @Override
    public void run() {

        User user = userClient.getUser(userId);
        if (user == null) {
            System.out.println("User with id " + userId + " does not exist");
            return;
        }

        List<SubscriptionRecord> result = subscriptionClient.listUserSubscriptions(userId);
        if (result.isEmpty()) {
            System.out.println("User " + userId + " has no subscriptions");
        }
        else{
            System.out.println("User " + userId + " is subscribed to the following hashtag with videos ids to watch:");
            for (SubscriptionRecord record : result) {
                Hashtag hashtag = hashtagsClient.getHashtag(record.getHashtagId());
                System.out.println("Hashtag " + record.getHashtagId() + "- #" + hashtag.getName() + " with videos ids to watch: " + record.getVideoIds());

            }
        }
    }
}
