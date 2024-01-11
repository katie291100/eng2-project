package uk.ac.york.eng2.subscription.events;

import io.micronaut.configuration.kafka.serde.SerdeRegistry;
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
    public KStream<SubscriptionIdentifier, SubscriptionValue>  hashtagVideoStream(ConfiguredStreamBuilder builder) {

        Properties props = builder.getConfiguration();
        props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());


        try (AdminClient client = AdminClient.create(builder.getConfiguration())) {
            if (!client.listTopics().names().get().contains("new-video")) {
                client.createTopics(List.of(
                        new NewTopic("new-video", 3, (short) 1)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Materialized<SubscriptionIdentifier, SubscriptionValue, KeyValueStore<Bytes, byte[]>> materialized = Materialized.as("subscription-store");
        materialized.withKeySerde(serdeRegistry.getSerde(SubscriptionIdentifier.class));
        materialized.withValueSerde(serdeRegistry.getSerde(SubscriptionValue.class));


        KStream<SubscriptionIdentifier, SubscriptionValue> streamVideo = builder.stream("new-video", Consumed.with(Serdes.Long(), serdeRegistry.getSerde(Video.class)))
                .flatMap((key, value) -> {
                    List<KeyValue<Long, Long>> hashtagMap = new ArrayList<>();
                    System.out.println("map: " + value);

                    for (Hashtag hashtag : value.getHashtags()) {
                        hashtagMap.add(new KeyValue<>(hashtag.getId(), value.getId()));
                    }

                    return hashtagMap;
                }).flatMap((key, value) -> {
                    List<User> users = userRepository.findAll();
                    List<KeyValue<SubscriptionIdentifier, SubscriptionValue>> subscriptionMap = new ArrayList<>();
                    System.out.println("map2: " + value);

                    for (User user : users) {
                        if (user.getSubscriptions().stream().anyMatch(subscription -> subscription.getId().equals(key))) {
                            SubscriptionIdentifier subscriptionIdentifier = new SubscriptionIdentifier(user.getId(), key);
                            SubscriptionValue subscriptionValue = new SubscriptionValue(List.of(value));
                            subscriptionMap.add(new KeyValue<>(subscriptionIdentifier, subscriptionValue));
                        }
                    }
                    System.out.println("map2: " + subscriptionMap);

                    return subscriptionMap;} )
                .groupByKey(Grouped.with(serdeRegistry.getSerde(SubscriptionIdentifier.class), serdeRegistry.getSerde(SubscriptionValue.class)))
                .reduce((value1, value2) -> {
                    System.out.println("value1: " + value1 + " value2: " + value2);
                    value1.addVideoId(value2.getVideoIds());
                    return value1;
                }, materialized).toStream().peek((key, value) -> System.out.println("key: " + key + " value: " + value));

        streamVideo.to("outputt", Produced.with(serdeRegistry.getSerde(SubscriptionIdentifier.class), serdeRegistry.getSerde(SubscriptionValue.class))); // custom serde for List<Long>
        return streamVideo;
    }

}
