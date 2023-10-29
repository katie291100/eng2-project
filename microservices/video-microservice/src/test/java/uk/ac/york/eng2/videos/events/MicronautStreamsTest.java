package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.kstream.KStream;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;

import java.util.HashMap;
import java.util.Map;

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Property(name = "spec.name", value = "MicronautStreamsTest")
public class MicronautStreamsTest {
    @Inject
    VideoProducer producer;

    @Inject
    KafkaStreams kStreams;

    static Map<Long, Video> events = new HashMap<>();

    @BeforeEach
    void setup() {
        events.clear();
        Awaitility.await()
                .until(() -> kStreams.state().isRunningOrRebalancing());
    }

    @AfterAll
    public void cleanup() {
        kStreams.close();
    }

    @Test
    public void testStreams() {
        Video video = new Video();
        video.setTitle("Test Video");
        User user = new User();
        user.setName("Test User");
        video.setPostedBy(user);

        producer.postVideo(1L, video);
        Awaitility.await()
                .until(() -> events.containsKey(1L));
    }


    @KafkaListener
    @Requires(property = "spec.name", value = "MicronautStreamsTest")
    static class StreamsListener{

        @Topic("new-video")
        void receive(@KafkaKey Long key, Video video) {
            System.out.println("Received " + video);
            events.put(key, video);
        }

    }

}
