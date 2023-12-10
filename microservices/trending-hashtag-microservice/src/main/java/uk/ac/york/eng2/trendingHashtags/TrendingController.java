package uk.ac.york.eng2.trendingHashtags;


import io.micronaut.configuration.kafka.streams.InteractiveQueryService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.ValueAndTimestamp;

import java.util.ArrayList;
import java.util.List;

@Controller("/trending")
public class TrendingController {
    
    @Inject
    InteractiveQueryService interactiveQueryService;

    @Get("/")
    public List<Long> list() {
        ReadOnlyKeyValueStore<Long, ValueAndTimestamp<Long>>  queryableStore = getStore();
        List<Long> keys = new ArrayList<>();
        queryableStore.all().forEachRemaining((keyValue) -> {
            keys.add(keyValue.key);
        });

        return keys.subList(0,10);
    }

    private ReadOnlyKeyValueStore<Long, ValueAndTimestamp<Long>> getStore() {
        return interactiveQueryService.getQueryableStore(
                "trending-hashtag-store", QueryableStoreTypes.<Long, Long>timestampedKeyValueStore()).orElse(null);
    }
}