package uk.ac.york.eng2.subscription.events;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import io.micronaut.configuration.kafka.serde.CompositeSerdeRegistry;
import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.configuration.kafka.streams.InteractiveQueryService;
import io.micronaut.context.annotation.Factory;
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
import uk.ac.york.eng2.subscription.domain.User;
import uk.ac.york.eng2.subscription.domain.Video;
import uk.ac.york.eng2.subscription.repositories.HashtagsRepositoryExtended;
import uk.ac.york.eng2.subscription.repositories.UserRepositoryExtended;
import uk.ac.york.eng2.subscription.repositories.UsersRepository;

import java.util.*;

@Factory
public class SubscriptionStream {
    @Inject
    InteractiveQueryService interactiveQueryService;
    @Inject
    HashtagsRepositoryExtended hashtagsRepositoryExtended;

    @Inject
    private UserRepositoryExtended userRepository;
    @Inject
    private CompositeSerdeRegistry serdeRegistry;



    @Singleton
    @Named("subscription-stream")
    public KStream<SubscriptionIdentifier, Set<Long>> hashtagVideoStream(ConfiguredStreamBuilder builder) {

        Properties props = builder.getConfiguration();
        props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());

        try (AdminClient client = AdminClient.create(builder.getConfiguration())) {
            if (!client.listTopics().names().get().contains("watch-video")) {
                client.createTopics(List.of(
                        new NewTopic("watch-video", 3, (short) 1)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        KStream<Long, Video> streamVideo = builder.stream("new-video", Consumed.with(Serdes.Long(), serdeRegistry.getSerde(Video.class)));

        KStream<Long, Long> stream = streamVideo.flatMap((key, value) -> {
            List<KeyValue<Long, Long>> hashtagMap = new ArrayList<>();

            for (Hashtag hashtag : value.getHashtags()) {
                hashtagMap.add(new KeyValue<>(hashtag.getId(), value.getId()));
            }

            return hashtagMap;});

        KTable<SubscriptionIdentifier, Set<Long>> streamTable = stream.flatMap((key, value) -> {
                    List<User> users = userRepository.findAll();
                    List<KeyValue<SubscriptionIdentifier, Long>> subscriptionMap = new ArrayList<>();

                    for (User user : users) {
                        if (user.getSubscriptions().stream().anyMatch(subscription -> subscription.getId().equals(key))) {
                            SubscriptionIdentifier subscriptionIdentifier = new SubscriptionIdentifier(user.getId(), key);

                            subscriptionMap.add(new KeyValue<>(subscriptionIdentifier, value));
                        }
                    }
                    return subscriptionMap;
        }).groupBy((key, value) -> key, Grouped.with(serdeRegistry.getSerde(SubscriptionIdentifier.class), Serdes.Long())).aggregate(
                HashSet::new,
                (key, value, aggregate) -> {
                    aggregate.add(value);
                    return aggregate;
                });
        Materialized<SubscriptionIdentifier, Set<Long>, KeyValueStore<Bytes, byte[]>> materialized = Materialized.as("subscription-store");
        materialized.withKeySerde(serdeRegistry.getSerde(SubscriptionIdentifier.class));
        KStream<SubscriptionIdentifier, Set<Long>> streamReturn = streamTable.toStream();
        streamReturn.toTable(materialized);
        return streamReturn;
    }
}
