package uk.ac.york.eng2.subscription.controllers;

import io.micronaut.configuration.kafka.streams.InteractiveQueryService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import io.micronaut.runtime.EmbeddedApplication;
import jakarta.inject.Inject;
import org.apache.kafka.streams.KeyValue;
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
        ReadOnlyKeyValueStore<SubscriptionIdentifier, SubscriptionValue> queryableStore = getStore("hashtag-video-store-final");
        ReadOnlyKeyValueStore<Long, Long> queryableStoreWatchers = interactiveQueryService.getQueryableStore("watch-video-store", QueryableStoreTypes.<Long, Long>keyValueStore()).orElse(null);

        SubscriptionValue video = queryableStore.get(new SubscriptionIdentifier(userId, hashtagId));
        video.getVideoIds().sort(
                (o1, o2) -> {
                    Long o1Watchers = queryableStoreWatchers.get(o1);
                    Long o2Watchers = queryableStoreWatchers.get(o2);
                    if (o1Watchers == null) {
                        o1Watchers = 0L;
                    }
                    if (o2Watchers == null) {
                        o2Watchers = 0L;
                    }
                    return o2Watchers.compareTo(o1Watchers);
                }
        );
        return video.getVideoIds();
    }


    @Get("/")
    public  List<SubscriptionRecord> listAllSubscriptions() {
        ReadOnlyKeyValueStore<SubscriptionIdentifier, SubscriptionValue> queryableStore = getStore("hashtag-video-store-final");

        List<SubscriptionRecord> videos = new ArrayList<>();
        queryableStore.all().forEachRemaining((value) -> {
            videos.add(new SubscriptionRecord(value.key.getId(), value.key.getHashtagId(), value.value.getVideoIds()));
        });

        return videos;
    }

    @Put("/{hashtagId}/{userId}")
    public HttpResponse<Void> subscribe(Long hashtagId, Long userId) {
        return null;
    }

    @Delete("/{hashtagId}/{userId}")
    public HttpResponse<Void> unsubscribe(Long hashtagId, Long userId) {
        return null;
    }

    private ReadOnlyKeyValueStore<SubscriptionIdentifier, SubscriptionValue> getStore(String name) {

        return interactiveQueryService.getQueryableStore(name, QueryableStoreTypes.<SubscriptionIdentifier, SubscriptionValue>keyValueStore()).orElse(null);
    }
}

