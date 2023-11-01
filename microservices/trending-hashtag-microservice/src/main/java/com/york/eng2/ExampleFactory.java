package com.york.eng2;

import com.york.eng2.trendingHashtags.domain.domain.Hashtag;
import com.york.eng2.trendingHashtags.domain.domain.Video;
import io.micronaut.configuration.kafka.serde.CompositeSerdeRegistry;
import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Inject;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.WindowStore;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Factory
public class ExampleFactory {
    @Inject
    private CompositeSerdeRegistry serdeRegistry;

    @Singleton
    @Named("like-video")
    KStream<WindowedIdentifier, Long> hashtagStream(ConfiguredStreamBuilder builder) {
        Properties props = builder.getConfiguration();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "trending-hashtag-metrics");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KStream<WindowedIdentifier, Long> stream =
                builder.stream("liked-hashtag", Consumed.with(Serdes.Long(), serdeRegistry.getSerde(Hashtag.class)))
                        .groupByKey(Grouped.with(Serdes.Long(), serdeRegistry.getSerde(Hashtag.class)))
                        .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(5)).advanceBy(Duration.ofSeconds(30)))
                        .count()
                        .toStream().selectKey((windowedHashtagId, count) -> new WindowedIdentifier(
                                windowedHashtagId.key(),
                                windowedHashtagId.window().start(),
                                windowedHashtagId.window().end()));

        stream.to("trending-hashtag", Produced.with(serdeRegistry.getSerde(WindowedIdentifier.class), Serdes.Long()));
        stream.print(Printed.toSysOut());
        return stream;
    }
}
