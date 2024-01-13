package uk.ac.york.eng2.subscription;

import io.micronaut.configuration.kafka.serde.SerdeRegistry;
import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.configuration.kafka.streams.InteractiveQueryService;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.extensions.junit5.annotation.TestResourcesScope;
import jakarta.inject.Inject;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.eng2.subscription.domain.Hashtag;
import uk.ac.york.eng2.subscription.domain.User;
import uk.ac.york.eng2.subscription.domain.Video;
import uk.ac.york.eng2.subscription.events.SubscriptionIdentifier;
import uk.ac.york.eng2.subscription.events.SubscriptionStream;
import uk.ac.york.eng2.subscription.repositories.HashtagsRepositoryExtended;
import uk.ac.york.eng2.subscription.repositories.UserRepositoryExtended;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.apache.kafka.common.utils.Utils.sleep;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Example of a Test Double approach for testing our Kafka Streams logic, without actually using a
 * Kafka cluster. We use a simulated Kafka Streams driver included with KS: the {@code
 * TopologyTestDriver}.
 */
@TestResourcesScope("myscope")
@MicronautTest(transactional = false, environments = "no_streams")
public class SubscriptionStreamsDoubleTest {

    @Inject
    private SerdeRegistry serdeRegistry;
    @Inject
    private SubscriptionStream streams;
    @Inject
    InteractiveQueryService interactiveQueryService;

    @Inject
    HashtagsRepositoryExtended hashtagRepo;

    @Inject
    UserRepositoryExtended usersRepository;;



    private Properties props;
    private ConfiguredStreamBuilder builder;
    @BeforeEach
    public void setup() {
        props = new Properties();
        builder = new ConfiguredStreamBuilder(props);
        streams.newVideoStream(builder);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());
//        hashtagsRepository.deleteAll();
        hashtagRepo.deleteAll();
        usersRepository.deleteAll();
    }

    /**
     * Test that the topology is correct at startup.
     */
    @Test
    public void topologyCheckEmpty() {
        try (TopologyTestDriver testDriver = new TopologyTestDriver(builder.build(), props)) {
            TestInputTopic<Long, Video> inputTopic =
                    testDriver.createInputTopic(
                            "post-video", new LongSerializer(), serdeRegistry.getSerializer(Video.class));

            ReadOnlyKeyValueStore<Object, Object> store = testDriver.getKeyValueStore("subscription-store");
            assertNotNull(store);
            assertFalse(store.all().hasNext());
        }
    }

    /**
     * Test that the topology keeps track of videos correctly when a video is created and a subscription exists.
     */
    @Test
    public void topologyCheck() {

        try (TopologyTestDriver testDriver = new TopologyTestDriver(builder.build(), props)) {
            TestInputTopic<Long, Video> inputTopic =
                    testDriver.createInputTopic(
                            "new-video", new LongSerializer(), serdeRegistry.getSerializer(Video.class));

            Video video = new Video();
            video.setId(1L);

            Hashtag hashtag = new Hashtag();
            hashtag.setName("test");
            hashtag.setId(2L);
            hashtagRepo.save(hashtag);

            User user = new User();
            user.setId(3L);
            user.setName("test");
            user.setSubscriptions(Set.of(hashtag));
            video.setHashtags(Set.of(hashtag));
            usersRepository.save(user);

            usersRepository.findById(3L).ifPresentOrElse( u ->
                    System.out.println(u.getSubscriptions()),
                    () -> {
                        throw new RuntimeException("User not found");
                    }
            );


            inputTopic.pipeInput(1L, video);
            sleep(30000);


            ReadOnlyKeyValueStore<Object, Object> store = testDriver.getKeyValueStore("subscription-store");
            assertNotNull(store);
            assertEquals(List.of(video.getId()), store.get(new SubscriptionIdentifier(user.getId(), hashtag.getId())));

        }
    }

    /**
     * Test that the topology keeps track of multipole videos correctly when videos created and a subscription exists.
     */
    @Test
    public void topologyCheckMultiple() {

        try (TopologyTestDriver testDriver = new TopologyTestDriver(builder.build(), props)) {
            TestInputTopic<Long, Video> inputTopic =
                    testDriver.createInputTopic(
                            "new-video", new LongSerializer(), serdeRegistry.getSerializer(Video.class));

            Video video = new Video();
            video.setId(1L);

            Hashtag hashtag = new Hashtag();
            hashtag.setName("test");
            hashtag.setId(2L);
            hashtagRepo.save(hashtag);

            User user = new User();
            user.setId(3L);
            user.setName("test");
            user.setSubscriptions(Set.of(hashtag));
            video.setHashtags(Set.of(hashtag));
            usersRepository.save(user);

            Video video2 = new Video();
            video2.setId(4L);
            video2.setHashtags(Set.of(hashtag));
            inputTopic.pipeInput(4L, video2);

            inputTopic.pipeInput(1L, video);
            sleep(30000);

            List<Long> videos = new ArrayList<>();
            videos.add(video.getId());
            videos.add(video2.getId());

            ReadOnlyKeyValueStore<SubscriptionIdentifier, List<Long>> store = testDriver.getKeyValueStore("subscription-store");
            assertNotNull(store);
            List<Long> result = store.get(new SubscriptionIdentifier(user.getId(), hashtag.getId()));

            assertTrue(result.containsAll(videos));
        }
    }

    /**
     * Test that the topology keeps track of multipole videos correctly when videos created and a subscription exists.
     */
    @Test
    public void topologyCheckMultipleBeforeSub() {

        try (TopologyTestDriver testDriver = new TopologyTestDriver(builder.build(), props)) {
            TestInputTopic<Long, Video> inputTopic =
                    testDriver.createInputTopic(
                            "new-video", new LongSerializer(), serdeRegistry.getSerializer(Video.class));

            Video video = new Video();
            video.setId(1L);

            Hashtag hashtag = new Hashtag();
            hashtag.setName("test");
            hashtag.setId(2L);
            hashtagRepo.save(hashtag);
            video.setHashtags(Set.of(hashtag));

            User user = new User();
            user.setId(3L);
            user.setName("test");
            user.setSubscriptions(Set.of(hashtag));

            Video video2 = new Video();
            video2.setId(4L);
            video2.setHashtags(Set.of(hashtag));
            inputTopic.pipeInput(4L, video2);
            sleep(10000);

            usersRepository.save(user);
            sleep(10000);

            inputTopic.pipeInput(1L, video);
            sleep(10000);

            List<Long> videos = new ArrayList<>();
            videos.add(video.getId());
            videos.add(video2.getId());

            ReadOnlyKeyValueStore<SubscriptionIdentifier, List<Long>> store = testDriver.getKeyValueStore("subscription-store");
            assertNotNull(store);
            List<Long> result = store.get(new SubscriptionIdentifier(user.getId(), hashtag.getId()));

            assertTrue(result.contains(1L));
            assertFalse(result.contains(4L));
        }
    }
}
