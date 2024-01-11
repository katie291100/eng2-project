package uk.ac.york.eng2.subscription.controllers;

import io.micronaut.configuration.kafka.streams.InteractiveQueryService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import io.micronaut.runtime.EmbeddedApplication;
import jakarta.inject.Inject;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.ValueAndTimestamp;
import uk.ac.york.eng2.subscription.domain.Video;
import uk.ac.york.eng2.subscription.events.SubscriptionIdentifier;
import uk.ac.york.eng2.subscription.events.SubscriptionStream;
import uk.ac.york.eng2.subscription.events.SubscriptionValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


@Controller("/subscription")
public class SubscriptionController implements SubscriptionControllerInterface{

    @Inject
    InteractiveQueryService interactiveQueryService;

    @Inject
    SubscriptionStream subscriptionStream;

    @Get("/{userId}/{hashtagId}")
    public List<Long> listVideosSubscription(Long userId, Long hashtagId) {
        ReadOnlyKeyValueStore<SubscriptionIdentifier, List<Long>> queryableStore = getStore();
        HashMap<Long, Long> values = new HashMap<>();

        List<Long> videos = queryableStore.get(new SubscriptionIdentifier(userId, hashtagId));
        System.out.println(queryableStore.approximateNumEntries());
        System.out.println("THE THING2");
        System.out.println(queryableStore.all().next());

        List<Long> video = queryableStore.all().next().value;
        return video;
    }

    @Get("/")
    public Set<Video> listAllSubscriptions() {
        SubscriptionValue subscriptionValue = new SubscriptionValue();
        subscriptionValue.addVideoId(new ArrayList<Long>(List.of(1L, 2L, 3L)));
        SubscriptionValue subscriptionValue2 = new SubscriptionValue();
        subscriptionValue2.addVideoId(subscriptionValue.getVideoIds());
        System.out.println(subscriptionValue2);
        System.out.println(subscriptionValue);
        ReadOnlyKeyValueStore<SubscriptionIdentifier, List<Long>> queryableStore = getStore();

        System.out.println(queryableStore.approximateNumEntries());
        queryableStore.all().forEachRemaining((value) -> System.out.println(value.key + " => " + value.value));

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

    private ReadOnlyKeyValueStore<SubscriptionIdentifier, List<Long>> getStore() {

        return interactiveQueryService.getQueryableStore("subscription-store", QueryableStoreTypes.<SubscriptionIdentifier, List<Long>>keyValueStore()).orElse(null);
    }
}

