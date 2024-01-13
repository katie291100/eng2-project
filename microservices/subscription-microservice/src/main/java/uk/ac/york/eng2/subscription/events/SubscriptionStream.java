package uk.ac.york.eng2.subscription.events;

import io.micronaut.configuration.kafka.serde.CompositeSerdeRegistry;
import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.configuration.kafka.streams.InteractiveQueryService;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import uk.ac.york.eng2.subscription.domain.Hashtag;
import uk.ac.york.eng2.subscription.domain.User;
import uk.ac.york.eng2.subscription.domain.Video;
import uk.ac.york.eng2.subscription.repositories.HashtagsRepositoryExtended;
import uk.ac.york.eng2.subscription.repositories.UserRepositoryExtended;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
    @SuppressWarnings("unchecked")
    @Named("subscription-stream")
    public KStream<SubscriptionIdentifier, List<Long>> newVideoStream(ConfiguredStreamBuilder builder) {

        Properties props = builder.getConfiguration();
        props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());


        try (AdminClient client = AdminClient.create(builder.getConfiguration())) {
            if (!client.listTopics().names().get().contains("new-video")) {
                client.createTopics(List.of(
                        new NewTopic("new-video", 6, (short) 1)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Materialized<SubscriptionIdentifier, List<Long>, KeyValueStore<Bytes, byte[]>> materialized = Materialized.as("subscription-store");
        materialized.withKeySerde(serdeRegistry.getSerde(SubscriptionIdentifier.class));
        materialized.withValueSerde(Serdes.ListSerde(ArrayList.class, serdeRegistry.getSerde(Long.class)));

        KStream<SubscriptionIdentifier, List<Long>> streamVideo = builder.stream("new-video", Consumed.with(Serdes.Long(), serdeRegistry.getSerde(Video.class)))
                .flatMap((key, value) -> {
                    List<KeyValue<Long, Long>> hashtagMap = new ArrayList<>();
                    System.out.println("map: " + value);

                    for (Hashtag hashtag : value.getHashtags()) {
                        hashtagMap.add(new KeyValue<>(hashtag.getId(), value.getId()));
                    }

                    return hashtagMap;
                }).flatMap((key, value) -> {
                    List<User> users = userRepository.findAll();
                    List<KeyValue<SubscriptionIdentifier, Long>> subscriptionMap = new ArrayList<>();
                    System.out.println("map2: " + value);

                    for (User user : users) {
                        if (user.getSubscriptions().stream().anyMatch(subscription -> subscription.getId().equals(key))) {
                            SubscriptionIdentifier subscriptionIdentifier = new SubscriptionIdentifier(user.getId(), key);
                            subscriptionMap.add(new KeyValue<>(subscriptionIdentifier, value));
                        }
                    }
                    System.out.println("map2: " + subscriptionMap);

                    return subscriptionMap;
                })
                .groupByKey(Grouped.with(serdeRegistry.getSerde(SubscriptionIdentifier.class), serdeRegistry.getSerde(Long.class)))
                .aggregate(ArrayList::new,
                        (key, value, aggregate) -> {
                            aggregate.add(value);
                            return aggregate;
                        }, materialized)
                .toStream().peek((key, value) -> System.out.println("key: " + key + " value: " + value));

        streamVideo.to("video-output", Produced.with(serdeRegistry.getSerde(SubscriptionIdentifier.class), Serdes.ListSerde(ArrayList.class, serdeRegistry.getSerde(Long.class)))); // custom serde for List<Long>
        return streamVideo;
    }

    @Singleton
    @SuppressWarnings("unchecked")
    @Named("hashtag-stream")
    public KStream<SubscriptionIdentifier, List<Long>> hashtagVideoStream(ConfiguredStreamBuilder builder) {

        Properties props = builder.getConfiguration();
        props.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE_V2);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());


        try (AdminClient client = AdminClient.create(builder.getConfiguration())) {
            if (!client.listTopics().names().get().contains("watch-video")) {
                client.createTopics(List.of(
                        new NewTopic("watch-video", 6, (short) 1)
                ));
            }
            if (!client.listTopics().names().get().contains("video-output")) {
                client.createTopics(List.of(
                        new NewTopic("video-output", 6, (short) 1)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Materialized<SubscriptionIdentifier, List<Long>, KeyValueStore<Bytes, byte[]>> materialized = Materialized.as("hashtag-video-store");
        materialized.withKeySerde(serdeRegistry.getSerde(SubscriptionIdentifier.class));
        materialized.withValueSerde(Serdes.ListSerde(ArrayList.class, serdeRegistry.getSerde(Long.class)));

        Materialized<SubscriptionIdentifier, List<Long>, KeyValueStore<Bytes, byte[]>> materialized2 = Materialized.as("hashtag-video-store-final");
        materialized2.withKeySerde(serdeRegistry.getSerde(SubscriptionIdentifier.class));
        materialized2.withValueSerde(Serdes.ListSerde(ArrayList.class, serdeRegistry.getSerde(Long.class)));

        Materialized<Long, Long, KeyValueStore<Bytes, byte[]>> materializedWatchCount = Materialized.as("watch-video-store");
        materializedWatchCount.withKeySerde(serdeRegistry.getSerde(Long.class));
        materializedWatchCount.withValueSerde(serdeRegistry.getSerde(Long.class));

        KStream<Long, Video> streamVideo = builder.stream("watch-video", Consumed.with(Serdes.Long(), serdeRegistry.getSerde(Video.class)));

        KStream<Long, Long> streamWatch = streamVideo.groupBy((key, value) -> value.getId(), Grouped.with(Serdes.Long(), serdeRegistry.getSerde(Video.class)))
                .count(materializedWatchCount).toStream().peek((key, value) -> System.out.println("key: " + key + " value: " + value));


        KStream<SubscriptionIdentifier, List<Long>> subscriptionStream = streamVideo.flatMap((key, value) -> {
                    List<KeyValue<SubscriptionIdentifier, Long>> hashtagMap = new ArrayList<>();
                    System.out.println("map: " + value);
                    User user = userRepository.findById(key).orElse(null);
                    for (Hashtag hashtag : value.getHashtags()) {
                        System.out.println("hash: " + hashtag);
                        System.out.println("user: " + user.getSubscriptions());
                        if (user.getSubscriptions().stream().anyMatch(subscription -> subscription.getId().equals(hashtag.getId()))) {
                            System.out.println("userhashash: " + user);
                            SubscriptionIdentifier subscriptionIdentifier = new SubscriptionIdentifier(key, hashtag.getId());
                            hashtagMap.add(new KeyValue<>(subscriptionIdentifier, value.getId()));
                        }
                    }
                    List<User> users = userRepository.findAll();
                    for (User userNext : users) {
                        for (Hashtag hashtag : userNext.getSubscriptions()) {
                            SubscriptionIdentifier subscriptionIdentifier = new SubscriptionIdentifier(userNext.getId(), hashtag.getId());

                            hashtagMap.add(new KeyValue<>(subscriptionIdentifier,0L));
                        }
                    }


                    return hashtagMap;
                }).groupByKey(Grouped.with(serdeRegistry.getSerde(SubscriptionIdentifier.class), serdeRegistry.getSerde(Long.class)))
                .aggregate(ArrayList::new,
                        (key, value, aggregate) -> {
                            aggregate.add(value);

                            return aggregate;
                        }, materialized)
                .toStream().peek((key, value) -> System.out.println("key: " + key + " value-to-remove: " + value))
                .join(builder.stream("video-output", Consumed.with(serdeRegistry.getSerde(SubscriptionIdentifier.class), Serdes.ListSerde(ArrayList.class, serdeRegistry.getSerde(Long.class)))).toTable(),
                        (key, value1, value2) -> {
                            System.out.println("key10: " + key);
                            System.out.println("value10: " + value1 + " value20: " + value2);
                            List<Long> video2 = (List<Long>) value2;
                            video2.removeAll((List<Long>)value1);
                            video2.remove(0L);
                            System.out.println("value34: " + video2);

                            return video2;
                        })

                .peek((key, value) -> System.out.println("key: " + key + " value3: " + value));

        subscriptionStream.toTable(materialized2);

        return subscriptionStream;
    }
}
