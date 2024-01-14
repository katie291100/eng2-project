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
import uk.ac.york.eng2.videos.clients.VideosClient;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.VideoDTO;
import uk.ac.york.eng2.videos.repositories.UsersRepositoryExtended;
import uk.ac.york.eng2.videos.repositories.VideosRepositoryExtended;

import java.time.Duration;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest(transactional = false)
@Property(name = "spec.name", value = "KafkaProductionTest")
public class KafkaProductionTest {

    @Inject
    VideosClient client;

    @Inject
    VideosRepositoryExtended videosRepo;

    @Inject
    UsersRepositoryExtended userRepo;

    static Map<Long, Video> videoAdded = new java.util.HashMap<>();
    static Map<Long, Video> videoLiked = new java.util.HashMap<>();
    static Map<Long, Video> videoDisliked = new java.util.HashMap<>();

    User poster = new User();
    @BeforeEach()
    void setup() {
        videosRepo.deleteAll();
        userRepo.deleteAll();
        poster.setName("Test User");
        userRepo.save(poster);
        videoAdded.clear();
        videoLiked.clear();
        videoDisliked.clear();
    }


    @Test
    public void testAddVideoProduced() {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setTitle("Test Video");

        videoDTO.setPostedBy(poster.getId());

        HttpResponse<Void> response = client.addVideo(videoDTO);
        Iterable<Video> iterVideos = client.list();

        assertEquals(201, response.getStatus().getCode());
        assertEquals("Test Video", iterVideos.iterator().next().getTitle());
        Awaitility.await()
                .atMost(Duration.ofSeconds(30))
                .until(() -> videoAdded.containsKey(poster.getId()));
    }

    @Test
    public void testLikeVideoProduced() {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setTitle("Test Video");

        videoDTO.setPostedBy(poster.getId());

        HttpResponse<Void> response = client.addVideo(videoDTO);
        Video video = client.list().iterator().next();
        client.likeVideo(video.getId(), poster.getId());
        assertEquals(201, response.getStatus().getCode());
        Awaitility.await()
                .atMost(Duration.ofSeconds(30))
                .until(() -> videoLiked.containsKey(poster.getId()));
    }

    @Test
    public void testDislikeVideoProduced() {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setTitle("Test Video");

        videoDTO.setPostedBy(poster.getId());

        HttpResponse<Void> response = client.addVideo(videoDTO);
        Video video = client.list().iterator().next();
        client.dislikeVideo(video.getId(), poster.getId());
        assertEquals(201, response.getStatus().getCode());
        Awaitility.await()
                .atMost(Duration.ofSeconds(30))
                .until(() -> videoDisliked.containsKey(poster.getId()));
    }
    @Requires(property = "spec.name", value = "KafkaProductionTest")
    @KafkaListener(groupId = "kafka-production-test")
    static class TestConsumer {

        @Topic("new-video")
        void newVideo(@KafkaKey Long key, Video b) {
            videoAdded.put(key, b);
        }

        @Topic("like-video")
        void likeVideo(@KafkaKey Long key, Video b){
            videoLiked.put(key, b);
        }

        @Topic("dislike-video")
        void dislikeVideo(@KafkaKey Long key, Video b){
            videoDisliked.put(key, b);
        }
    }

}
