package uk.ac.york.eng2.subscription.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import io.micronaut.runtime.EmbeddedApplication;
import jakarta.inject.Inject;
import uk.ac.york.eng2.subscription.domain.Video;

import java.util.Set;

@Controller("/subscription")
public class SubscriptionController implements SubscriptionControllerInterface{

    @Override
    @Get("/")
    public Set<Video> listAllSubscriptions() {
        return null;
    }

    @Put("/{hashtagId}/{userId}")
    public HttpResponse<Void> subscribe(Long hashtagId, Long userId) {
        return null;
    }

    @Delete("/{hashtagId}/{userId}")
    public HttpResponse<Void> unsubscribe(Long hashtagId, Long userId) {
        return null;
    }
}
