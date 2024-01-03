package uk.ac.york.eng2.subscription.events;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
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
import uk.ac.york.eng2.subscription.domain.Hashtag;
import uk.ac.york.eng2.subscription.domain.Video;
import uk.ac.york.eng2.subscription.repositories.HashtagsRepository;
import uk.ac.york.eng2.subscription.repositories.UsersRepository;

import java.time.Duration;
import java.util.*;

@Factory
public class SubscriptionStream {
    @Inject
    InteractiveQueryService interactiveQueryService;
    @Inject
    HashtagsRepository hashtagsRepository;

    @Inject
    private UsersRepository userRepository;
    @Inject
    private CompositeSerdeRegistry serdeRegistry;



    @Singleton
    @Named("liked-hashtag")
    public KTable<Long, Set<Long>> hashtagVideoStream(ConfiguredStreamBuilder builder) {

        Properties props = builder.getConfiguration();
        props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());

        try (AdminClient client = AdminClient.create(builder.getConfiguration())) {
            if (!client.listTopics().names().get().contains("watch-video")) {
                client.createTopics(List.of(
                        new NewTopic("watch-video", 3, (short) 1)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        KStream<Long, Video> streamWatched = builder.stream("watch-video", Consumed.with(Serdes.Long(), serdeRegistry.getSerde(Video.class)));
        KStream<Long, Video> streamVideo = builder.stream("new-video", Consumed.with(Serdes.Long(), serdeRegistry.getSerde(Video.class)));

        Materialized<Long, Long, KeyValueStore<Bytes, byte[]>> materialized = Materialized.as("trending-hashtag-store");
        KTable<Long, Set<Long>> stream = streamVideo.flatMap((key, value) -> {
            List<KeyValue<Long, Long>> hashtagMap = new ArrayList<>();

            for (Hashtag hashtag : value.getHashtags()) {
                hashtagMap.add(new KeyValue<>(hashtag.getId(), value.getId()));
            }

            return hashtagMap;

        }).groupBy((key, value) -> key, Grouped.with(Serdes.Long(), Serdes.Long())).aggregate(
                HashSet::new,
                (key, value, aggregate) -> {
                    aggregate.add(value);
                    return aggregate;
                })
        ;
        stream.toStream().toTable(Materialized.as("hashtag-video-store"));
        //stream goes here  ;

        return stream;
    }
}
