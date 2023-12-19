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
        description = "adds a new video record to the database",
        mixinStandardHelpOptions = true)
public class TrendingHashtagsCommand implements Runnable {

    @Inject
    private TrendingClient trendingClient;

    @Inject
    private HashtagsClient hashtagsClient;

    @Override
    public void run() {
        trendingClient.list().forEach(hashtag -> {
            Hashtag hashtag1 = hashtagsClient.getHashtag(hashtag);
            if (hashtag1 != null){
                System.out.println(hashtag1.getName());
            }
        });

    }
}
