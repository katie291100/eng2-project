package uk.ac.york.eng2.trendingHashtags.clients;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

import java.util.List;


@Client("/trendingHashtags")
public interface TrendingClient {
    @Get("/")
    List<Long> list();

}