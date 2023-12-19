package uk.ac.york.eng2.clients;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

import java.util.List;

@Client("${trending.url:`http://localhost:8082/trendingHashtags`}")
public interface TrendingClient {
    @Get("/")
    List<Long> list();

}