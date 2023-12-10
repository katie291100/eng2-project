package uk.ac.york.eng2.trendingHashtags.events;

import uk.ac.york.eng2.trendingHashtags.domain.Hashtag;
import uk.ac.york.eng2.trendingHashtags.domain.Video;
import uk.ac.york.eng2.trendingHashtags.repositories.HashtagsRepository;
import io.micronaut.configuration.kafka.serde.CompositeSerdeRegistry;
import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.configuration.kafka.streams.InteractiveQueryService;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Inject;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;

import java.time.Duration;
import java.util.*;

@Factory
public class TrendingStreams {
    @Inject
    InteractiveQueryService interactiveQueryService;
    @Inject
    HashtagsRepository hashtagsRepository;
    @Inject
    private CompositeSerdeRegistry serdeRegistry;

    @Singleton
    @Named("liked-hashtag")
    public KStream<WindowedIdentifier, Long> hashtagStream(ConfiguredStreamBuilder builder) {

        Properties props = builder.getConfiguration();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "trending-hashtag-metrics");
        props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());
        Materialized<Long, Long, KeyValueStore<Bytes, byte[]>> materialized = Materialized.as("trending-hashtag-store");
        KStream<WindowedIdentifier, Long> stream = builder.stream("like-video", Consumed.with(Serdes.Long(), serdeRegistry.getSerde(Video.class)))
                .flatMapValues(Video::getHashtags)
                .flatMap((key, value) -> {
                    if (hashtagsRepository.findById(value.getId()).orElse(null) == null){
                        hashtagsRepository.save(value);
                    }
                    System.out.println("liked-hashtag");
                    @NonNull List<Hashtag> hashtags = hashtagsRepository.findAll();
                    System.out.println(value);
                    List<KeyValue<Long, Long>> hashtagMap = new ArrayList<>();

                    for (Hashtag hashtag : hashtags) {
                        if (hashtag.getId().equals(value.getId())) {
                            hashtagMap.add(new KeyValue<>(hashtag.getId(), 1L));
                            } else {
                            hashtagMap.add(new KeyValue<>(hashtag.getId(), 0L));
                        }
                    }
                    return hashtagMap;
                })
                .groupByKey(Grouped.with(Serdes.Long(), Serdes.Long()))
                .windowedBy(SlidingWindows.ofTimeDifferenceAndGrace(Duration.ofMinutes(5), Duration.ofMinutes(1)))
                .aggregate(() -> 0L, (k, v, count) -> count + v).toStream().selectKey((windowedHashtagId, count) -> new WindowedIdentifier(
                        windowedHashtagId.key(),
                        windowedHashtagId.window().start(),
                        windowedHashtagId.window().end()));

        stream.selectKey((key, value) -> key.getId()).toTable(materialized);

        stream.to("trending-hashtag", Produced.with(serdeRegistry.getSerde(WindowedIdentifier.class), Serdes.Long()));

        return stream;
    }
}
