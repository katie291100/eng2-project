package com.york.eng2;

import com.york.eng2.trendingHashtags.domain.Hashtag;
import io.micronaut.configuration.kafka.serde.CompositeSerdeRegistry;
import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Inject;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

import java.time.Duration;
import java.util.Properties;

@Factory
public class ExampleFactory {
    @Inject
    private CompositeSerdeRegistry serdeRegistry;

    @Singleton
    @Named("liked-hashtag")
    KStream<WindowedIdentifier, Long> hashtagStream(ConfiguredStreamBuilder builder) {


        Properties props = builder.getConfiguration();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "trending-hashtag-metrics");
        KTable<Windowed<Long>, Long> stream =
                builder.stream("liked-hashtag", Consumed.with(Serdes.Long(), serdeRegistry.getSerde(Hashtag.class)))
                        .groupByKey(Grouped.with(Serdes.Long(), serdeRegistry.getSerde(Hashtag.class)))
                        .windowedBy(SlidingWindows.ofTimeDifferenceAndGrace(Duration.ofMinutes(5), Duration.ofMinutes(1)))
                        .count(Materialized.as("trending-hashtag-store"));
        String store = stream.queryableStoreName();
        System.out.println(store);
        KStream<WindowedIdentifier, Long> streamWindowed = stream.toStream().selectKey((windowedHashtagId, count) -> new WindowedIdentifier(
                windowedHashtagId.key(),
                windowedHashtagId.window().start(),
                windowedHashtagId.window().end())).mapValues((key, value) -> value);
        streamWindowed.to("trending-hashtag", Produced.with(serdeRegistry.getSerde(WindowedIdentifier.class), Serdes.Long()));
        streamWindowed.print(Printed.toSysOut());
        return streamWindowed;
    }
}
