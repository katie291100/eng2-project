package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.serde.CompositeSerdeRegistry;
import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import uk.ac.york.eng2.videos.domain.Video;

@Factory
public class BooksStream {
    @Inject
    private CompositeSerdeRegistry serdeRegistry;

    @Singleton
    KStream<Long, Long> readByDay(ConfiguredStreamBuilder builder) {
        Properties props = builder.getConfiguration();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "videos-metrics");
        props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);

        KStream<Long, Long> stream =
                builder.stream(
                                "new-video", Consumed.with(Serdes.Long(), serdeRegistry.getSerde(Video.class)))
                        .selectKey((userId, video) -> video.getId())
                        .groupByKey(Grouped.with(Serdes.Long(), serdeRegistry.getSerde(Video.class)))
                        .count()
                        .toStream();


        stream.to("new-video2");
        stream.print(Printed.toSysOut());

        return stream;
    }
}