package com.york.eng2.trendingHashtags;


import com.york.eng2.ExampleFactory;
import com.york.eng2.WindowedIdentifier;
import com.york.eng2.trendingHashtags.domain.Hashtag;
import io.micronaut.configuration.kafka.streams.InteractiveQueryService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.apache.kafka.streams.state.ValueAndTimestamp;


import java.net.URI;
import java.time.Instant;
import java.util.Optional;

@Controller("/Trending")
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