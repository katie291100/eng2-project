package uk.ac.york.eng2.commands;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Inject;
import picocli.CommandLine;
import uk.ac.york.eng2.clients.HashtagsClient;
import uk.ac.york.eng2.clients.TrendingClient;
import uk.ac.york.eng2.clients.UsersClient;
import uk.ac.york.eng2.clients.VideosClient;
import uk.ac.york.eng2.domain.Hashtag;
import uk.ac.york.eng2.domain.User;
import uk.ac.york.eng2.dto.HashtagDTO;
import uk.ac.york.eng2.dto.VideoDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
