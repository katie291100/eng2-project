package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpResponse;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.eng2.videos.VideosClient;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.VideoDTO;
import uk.ac.york.eng2.videos.repositories.UsersRepository;
import uk.ac.york.eng2.videos.repositories.VideosRepository;

import java.time.Duration;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest(transactional = false, environments = "no_streams")
@Property(name = "spec.name", value = "KafkaProductionTest")
public class KafkaProductionTest {

    @Inject
    VideosClient client;

    @Inject
    VideosRepository videosRepo;

    @Inject
    UsersRepository userRepo;

    static Map<Long, Video> postsAdded = new java.util.HashMap<>();

    User poster = new User();
    @BeforeEach()
    void setup() {
        videosRepo.deleteAll();
        userRepo.deleteAll();
        poster.setName("Test User");
        userRepo.save(poster);}
    @Test
    public void addVideoUser() {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setTitle("Test Video");

        videoDTO.setPostedBy(poster.getId());

        HttpResponse<Void> response = client.add(videoDTO);
        Iterable<Video> iterVideos = client.list();
        Awaitility.await()
                .atMost(Duration.ofSeconds(30))
                .until(() -> postsAdded.containsKey(poster.getId()));
        assertEquals(201, response.getStatus().getCode());
        assertEquals("Test Video", iterVideos.iterator().next().getTitle());


    }
    @Requires(property = "spec.name", value = "KafkaProductionTest")
    @KafkaListener(groupId = "kafka-production-test")
    static class TestConsumer {

        @Topic(VideoProducer.TOPIC_ADD_VIDEO)
        void readBook(@KafkaKey Long id, Video video)
        {    postsAdded.put(id, video);  }}

}
