package uk.ac.york.eng2.trendingHashtags;

import static org.junit.jupiter.api.Assertions.*;


import io.micronaut.configuration.kafka.serde.SerdeRegistry;
import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.configuration.kafka.streams.InteractiveQueryService;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.extensions.junit5.annotation.TestResourcesScope;
import jakarta.inject.Inject;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import java.util.Set;

import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.eng2.trendingHashtags.domain.Hashtag;
import uk.ac.york.eng2.trendingHashtags.domain.Video;
import uk.ac.york.eng2.trendingHashtags.events.TrendingStreams;
import uk.ac.york.eng2.trendingHashtags.events.WindowedIdentifier;
import uk.ac.york.eng2.trendingHashtags.repositories.HashtagsRepository;

/**
 * Example of a Test Double approach for testing our Kafka Streams logic, without actually using a
 * Kafka cluster. We use a simulated Kafka Streams driver included with KS: the {@code
 * TopologyTestDriver}.
 */
@TestResourcesScope("myscope")
@MicronautTest(transactional = false, environments = "no_streams")
public class TrendingStreamsDoubleTest {

    @Inject
    private SerdeRegistry serdeRegistry;
    @Inject
    private TrendingStreams streams;
    @Inject
    private HashtagsRepository hashtagsRepository;
    @Inject
    InteractiveQueryService interactiveQueryService;

    private Properties props;
    private ConfiguredStreamBuilder builder;
    @BeforeEach
    public void setup() {
        props = new Properties();
        builder = new ConfiguredStreamBuilder(props);
        streams.hashtagStream(builder);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Long().getClass().getName());
        hashtagsRepository.deleteAll();
    }

    /**
     * Test that the topology is correct at startup.
     */
    @Test
    public void topologyCheckEmpty() {
        try (TopologyTestDriver testDriver = new TopologyTestDriver(builder.build(), props)) {
            TestInputTopic<Long, Video> inputTopic =
                    testDriver.createInputTopic(
                            "like-video", new LongSerializer(), serdeRegistry.getSerializer(Video.class));

            ReadOnlyKeyValueStore<Object, Object> store = testDriver.getKeyValueStore("trending-hashtag-store");
            assertNotNull(store);
            assertFalse(store.all().hasNext());
        }
    }

    /**
     * Test that the topology keeps track of likes correctly when a video with one hashtag is liked 3 times.
     */
    @Test
    public void topologyCheck() {

        try (TopologyTestDriver testDriver = new TopologyTestDriver(builder.build(), props)) {
            TestInputTopic<Long, Video> inputTopic =
                    testDriver.createInputTopic(
                            "like-video", new LongSerializer(), serdeRegistry.getSerializer(Video.class));

            Video video = new Video();
            Hashtag hashtag = new Hashtag();
            hashtag.setName("test");
            hashtag.setId(1L);
            hashtagsRepository.save(hashtag);

            video.setHashtags(Set.of(hashtag));
            final long videoID = 1L;
            final int eventCount = 3;
            for (int i = 0; i < eventCount; i++) {
                inputTopic.pipeInput(videoID, video);
            }

            ReadOnlyKeyValueStore<Object, Object> store = testDriver.getKeyValueStore("trending-hashtag-store");
            assertNotNull(store);
            assertEquals(3L, store.get(hashtag.getId()));

        }
    }

    /**
     * Test that the topology keeps track of likes correctly when a video with one hashtag is liked 3 times.
     */

    @Test
    public void multiHashtagTopologyCheck() {
        try (TopologyTestDriver testDriver = new TopologyTestDriver(builder.build(), props)) {
            TestInputTopic<Long, Video> inputTopic =
                    testDriver.createInputTopic(
                            "like-video", new LongSerializer(), serdeRegistry.getSerializer(Video.class));

            Video video1 = new Video();
            Hashtag hashtag1 = new Hashtag();
            hashtag1.setName("test");
            hashtag1.setId(1L);

            video1.setHashtags(Set.of(hashtag1));
            video1.setId(1L);
            inputTopic.pipeInput(video1.getId(), video1);
            inputTopic.pipeInput(video1.getId(), video1);

            Video video2 = new Video();
            Hashtag hashtag2 = new Hashtag();
            hashtag2.setName("test2");
            hashtag2.setId(2L);
            video2.setHashtags(Set.of(hashtag2));
            video2.setId(2L);
            inputTopic.pipeInput(video2.getId(), video2);

            TestOutputTopic<WindowedIdentifier, Long> outputTopic =
                    testDriver.createOutputTopic(
                            "trending-hashtag",
                            serdeRegistry.getDeserializer(WindowedIdentifier.class),
                            new LongDeserializer());


            ReadOnlyKeyValueStore<Object, Object> store = testDriver.getKeyValueStore("trending-hashtag-store");

            assertNotNull(store);
            assertEquals(2L, store.get(hashtag1.getId()));
            assertEquals(1L, store.get(hashtag2.getId()));

        }
    }
    @Test
    public void multiWindowTopologyCheck() {
        try (TopologyTestDriver testDriver = new TopologyTestDriver(builder.build(), props)) {
            TestInputTopic<Long, Video> inputTopic =
                    testDriver.createInputTopic(
                            "like-video", new LongSerializer(), serdeRegistry.getSerializer(Video.class));

            TestOutputTopic<WindowedIdentifier, Long> outputTopic =
                    testDriver.createOutputTopic(
                            "trending-hashtag",
                            serdeRegistry.getDeserializer(WindowedIdentifier.class),
                            new LongDeserializer());

            Video video1 = new Video();
            Hashtag hashtag1 = new Hashtag();
            hashtag1.setName("test");
            hashtag1.setId(1L);

            video1.setHashtags(Set.of(hashtag1));
            video1.setId(1L);
            inputTopic.pipeInput(1L, video1);
            inputTopic.pipeInput(1L, video1);
            inputTopic.pipeInput(1L, video1);

            Video video2 = new Video();
            Hashtag hashtag2 = new Hashtag();
            hashtag2.setName("test");
            hashtag2.setId(2L);

            video2.setHashtags(Set.of(hashtag2));
            video2.setId(2L);
            inputTopic.pipeInput(1L, video2);

            ReadOnlyKeyValueStore<Object, Object> store = testDriver.getKeyValueStore("trending-hashtag-store");

            assertNotNull(store);
            assertEquals(3L, store.get(hashtag1.getId()));
            assertEquals(1L, store.get(hashtag2.getId()));

            inputTopic.advanceTime(Duration.of(64, ChronoUnit.MINUTES));
            inputTopic.pipeInput(1L, video1);

            assertEquals(1L, store.get(hashtag1.getId()));
            assertEquals(0L, store.get(hashtag2.getId()));

        }
    }
}
