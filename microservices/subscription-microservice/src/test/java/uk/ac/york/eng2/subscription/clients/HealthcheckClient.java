package uk.ac.york.eng2.subscription.clients;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;


@Client("/health")
public interface HealthcheckClient {
    @Get("/")
    HttpResponse<Void> health();

}