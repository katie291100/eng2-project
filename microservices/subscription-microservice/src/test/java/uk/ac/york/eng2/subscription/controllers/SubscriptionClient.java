package uk.ac.york.eng2.subscription.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;

import java.util.List;


@Client("/subscription")
public interface SubscriptionClient {
    @Get("/")
    List<SubscriptionRecord> listAllSubscriptions();

    @Get("/{userId}/{hashtagId}")
    List<Long> listVideosSubscription(Long userId, Long hashtagId);

    @Put("/{hashtagId}/{userId}")
    HttpResponse<Void> subscribe(Long hashtagId, Long userId);

    @Delete("/{hashtagId}/{userId}")
    HttpResponse<Void> unsubscribe(Long hashtagId, Long userId);

    @Get("/user/{userId}")
    List<SubscriptionRecord> listUserSubscriptions(Long userId);


}