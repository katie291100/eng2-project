package com.york.eng2;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;


@Client("/hashtags")
public interface TrendingClient {
    @Get("/")
    public void list();


}