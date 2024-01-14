package uk.ac.york.eng2.clients;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.eng2.cli.clients.SubscriptionRecord;

import java.util.List;


@Client("${subscription.url:`http://localhost:8082/subscription`}")
public interface SubscriptionClient {
    @Get("/")
    List<SubscriptionRecord> listAllSubscriptions();

    @Get("/{userId}/{hashtagId}")
    List<Long> listVideosSubscription(Long userId, Long hashtagId);

    @Put("/{hashtagId}/{userId}")
    HttpResponse<Void> subscribe(Long hashtagId, Long userId);

    @Delete("/{userId}/{hashtagId}")
    HttpResponse<Void> unsubscribe(Long userId, Long hashtagId);

    @Get("/user/{userId}")
    List<SubscriptionRecord> listUserSubscriptions(Long userId);


}