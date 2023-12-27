package uk.ac.york.eng2.cli.commands;

import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.cli.clients.HashtagsClient;
import uk.ac.york.eng2.cli.clients.TrendingClient;
import uk.ac.york.eng2.cli.domain.Hashtag;

@CommandLine.Command(
        name = "get-trending-hashtags",
        description = "gets top 10 trending hashtags",
        mixinStandardHelpOptions = true)
public class TrendingHashtagsCommand implements Runnable {

    @Inject
    private TrendingClient trendingClient;

    @Inject
    private HashtagsClient hashtagsClient;

    @Override
    public void run() {
        System.out.println("Top 10 trending hashtags:");
        trendingClient.list().forEach(hashtagId -> {
            Hashtag hashtag = hashtagsClient.getHashtag(hashtagId);
            if (hashtag != null){
                System.out.println(hashtag.getId() + " - " + hashtag.getName());
            }
        });

    }
}
