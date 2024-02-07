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
        name = "unsubscribe",
        description = "subscribe to a hashtag",
        mixinStandardHelpOptions = true)
public class UnsubscribeCommand implements Runnable {

    @Inject
    private HashtagsClient hashtagsClient;

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
        if (hashtagId == null && hashtagName == null) {
            System.out.println("Please provide a hashtag id or name");
            return;
        }
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

        HttpResponse<Void> result = subscriptionClient.unsubscribe(hashtagId, userId);


        if (result.code() == 200) {
            System.out.println("Successfully unsubscribed user " + userId + " from hashtag " + hashtagId + ": #" + hashtag.getName());
        }
        else{
            System.out.println("Failed to unsubscribe user " + userId + " from hashtag " + hashtagId);
        }
    }
}
