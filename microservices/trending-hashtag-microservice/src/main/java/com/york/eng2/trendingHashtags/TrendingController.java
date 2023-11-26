package com.york.eng2.trendingHashtags;


import io.micronaut.configuration.kafka.streams.InteractiveQueryService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.ValueAndTimestamp;


import java.time.Instant;

@Controller("/trending")
public class TrendingController {
    
    @Inject
    InteractiveQueryService interactiveQueryService;

    @Get("/")
    public void list() {
        System.out.println("JERE");
        ReadOnlyKeyValueStore<Object, ValueAndTimestamp<Object>> queryableStore = interactiveQueryService.getQueryableStore(
                "trending-hashtag-store", QueryableStoreTypes.timestampedKeyValueStore()).orElse(null);
        assert queryableStore != null;
        System.out.println(Instant.now().toEpochMilli());
        System.out.println(Instant.now().toEpochMilli());
        queryableStore.all().forEachRemaining((keyValue) -> {
            System.out.println(keyValue.key + " " + keyValue.value);
        });
        System.out.println("JERE");

    }


}