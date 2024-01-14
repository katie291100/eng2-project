package uk.ac.york.eng2.trendingHashtags.controllers;


import io.micronaut.configuration.kafka.streams.InteractiveQueryService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import jakarta.persistence.Tuple;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.ValueAndTimestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller("/trendingHashtags")
public class TrendingController implements TrendingControllerInterface{
    
    @Inject
    InteractiveQueryService interactiveQueryService;

    @Get("/")
    public List<Long> listAll() {
        ReadOnlyKeyValueStore<Long, ValueAndTimestamp<Long>>  queryableStore = getStore();
        HashMap<Long, Long> values = new HashMap<>();

        queryableStore.all().forEachRemaining((keyValue) -> {
            values.put(keyValue.key, keyValue.value.value());
        });

        List<Long> keys = new ArrayList<>(values.keySet());
        keys.sort((o1, o2) -> values.get(o2).compareTo(values.get(o1)));

        if (values.keySet().size() > 10) {
            return keys.subList(0,10);
        }
        else {
            return keys;
        }
    }

    private ReadOnlyKeyValueStore<Long, ValueAndTimestamp<Long>> getStore() {
        return interactiveQueryService.getQueryableStore(
                "trending-hashtag-store", QueryableStoreTypes.<Long, Long>timestampedKeyValueStore()).orElse(null);
    }
}