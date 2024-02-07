package uk.ac.york.eng2.trendingHashtags.clients;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

import java.util.List;


@Client("/health")
public interface HealthcheckClient {
    @Get("/")
    HttpResponse<Void> health();

}