package uk.ac.york.eng2.videos;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.VideoDTO;

import uk.ac.york.eng2.videos.events.VideoProducer;
import uk.ac.york.eng2.videos.repositories.UsersRepository;
import uk.ac.york.eng2.videos.repositories.VideosRepository;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(transactional = false, environments = "no_streams")
public class VideosControllerTest {

    @Inject
    VideosClient client;

    @Inject
    VideosRepository videosRepo;

    @Inject
    UsersRepository userRepo;

    User poster = new User();

    Map<Long, Video> postsAdded = new java.util.HashMap<>();
    @MockBean(VideoProducer.class)
    VideoProducer videoProducer() {
        return (key, value) -> { postsAdded.put(key, value); };
    }

    @BeforeEach()
    void setup() {
        videosRepo.deleteAll();
        userRepo.deleteAll();
        postsAdded.clear();
        poster.setName("Test User");
        userRepo.save(poster);}


    @Test
    public void noVideos() {
        Iterable<Video> iterBooks = client.list();
        assertFalse(iterBooks.iterator().hasNext());
    }

    @Test
    public void addVideoUser() {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setTitle("Test Video");

        videoDTO.setPostedBy(poster.getId());

        HttpResponse<Void> response = client.add(videoDTO);
        Iterable<Video> iterVideos = client.list();

        assertEquals(201, response.getStatus().getCode());
        assertEquals("Test Video", iterVideos.iterator().next().getTitle());
        assertTrue(postsAdded.containsKey(iterVideos.iterator().next().getPostedBy().getId()));
    }

    @Test
    public void addVideoNoUser() {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setTitle("Test Video");

        videoDTO.setPostedBy(poster.getId()+1);

        HttpResponse<Void> response = client.add(videoDTO);
        Iterable<Video> iterVideos = client.list();

        assertEquals(404, response.getStatus().getCode());
        assertFalse(iterVideos.iterator().hasNext());
    }

    @Test
    public void getVideo() {

        Video video1 = new Video();
        video1.setTitle("Test Video1");
        video1.setPostedBy(poster);

        Video video2 = new Video();
        video2.setTitle("Test Video2");
        video2.setPostedBy(poster);

        videosRepo.save(video1);
        videosRepo.save(video2);
        System.out.println(client.list().toString());

        assertEquals(video1.getId(), client.getVideo(video1.getId()).getId());
        assertEquals(video1.getTitle(), client.getVideo(video1.getId()).getTitle());
    }

    @Test
    public void updateVideo() {
        Video video = new Video();
        video.setTitle("Test Video");
        video.setPostedBy(poster);
        videosRepo.save(video);

        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setTitle("Test Video2");

        HttpResponse<Void> response = client.updateVideo(video.getId(), videoDTO);

        Video updatedVideo = videosRepo.findById(video.getId()).orElse(null);
        assertEquals(HttpStatus.OK, response.getStatus());
        if (updatedVideo != null) {
            assertEquals("Test Video2", updatedVideo.getTitle());
        }
        else{
            assert false;
        }

    }

}