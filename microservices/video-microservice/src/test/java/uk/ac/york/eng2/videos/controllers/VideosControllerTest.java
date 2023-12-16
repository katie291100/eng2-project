package uk.ac.york.eng2.videos.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.eng2.videos.clients.VideosClient;
import uk.ac.york.eng2.videos.domain.Hashtag;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.HashtagDTO;
import uk.ac.york.eng2.videos.dto.VideoDTO;

import uk.ac.york.eng2.videos.events.VideoProducer;
import uk.ac.york.eng2.videos.repositories.HashtagsRepository;
import uk.ac.york.eng2.videos.repositories.UsersRepository;
import uk.ac.york.eng2.videos.repositories.VideosRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

    @Inject
    HashtagsRepository hashtagsRepo;

    User poster = new User();
    Hashtag hashtag = new Hashtag();

    Map<Long, Video> postsAdded = new HashMap<>();
    Map<Long, Video> watchVideo = new HashMap<>();
    Map<Long, Video> likeVideo = new HashMap<>();
    Map<Long, Video> dislikeVideo = new HashMap<>();

    @MockBean(VideoProducer.class)
    VideoProducer videoProducer() {
        return new VideoProducer() {
            @Override
            public void postVideo(Long key, Video b) {
                postsAdded.put(key, b);
            }

            @Override
            public void likeVideo(Long key, Video b) {
                likeVideo.put(key, b);

            }

            @Override
            public void dislikeVideo(Long key, Video b) {
                dislikeVideo.put(key, b);

            }
        };
    }


    @BeforeEach
    void setup() {
        videosRepo.deleteAll();
        userRepo.deleteAll();
        hashtagsRepo.deleteAll();

        poster.setName("Test User");
        userRepo.save(poster);

        hashtag.setName("hashtag1");
        hashtagsRepo.save(hashtag);

        postsAdded.clear();
        watchVideo.clear();
        likeVideo.clear();
        dislikeVideo.clear();

    }


    @Test
    public void testGetNoVideos() {
        Iterable<Video> iterBooks = client.list();
        assertFalse(iterBooks.iterator().hasNext());
    }

    @Test
    public void testAddVideo() {
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
    public void testAddVideoWithHashtags(){
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setTitle("Test Video");
        HashSet<HashtagDTO> hashtags = new HashSet<>();
        HashtagDTO hashtagDTO = new HashtagDTO("hashtag1");
        hashtags.add(hashtagDTO);
        videoDTO.setHashtags(hashtags);

        videoDTO.setPostedBy(poster.getId());

        HttpResponse<Void> response = client.add(videoDTO);
        Iterable<Video> iterVideos = client.list();

        assert !hashtagsRepo.findByName("hashtag1").isEmpty();
        assertEquals(201, response.getStatus().getCode());
        assertEquals("Test Video", iterVideos.iterator().next().getTitle());
        assertEquals("hashtag1", iterVideos.iterator().next().getHashtags().iterator().next().getName());
        assertTrue(postsAdded.containsKey(iterVideos.iterator().next().getPostedBy().getId()));
    }

    @Test
    public void testAddVideoNoUser() {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setTitle("Test Video");

        videoDTO.setPostedBy(poster.getId()+1);

        HttpResponse<Void> response = client.add(videoDTO);
        Iterable<Video> iterVideos = client.list();

        assertEquals(404, response.getStatus().getCode());
        assertFalse(iterVideos.iterator().hasNext());
        assertTrue(postsAdded.isEmpty());
    }

    @Test
    public void testGetVideo() {

        Video video1 = new Video();
        video1.setTitle("Test Video1");
        video1.setPostedBy(poster);

        HashSet<Hashtag> hashtags = new HashSet<>();
        hashtags.add(hashtag);
        video1.setHashtags(hashtags);

        Video video2 = new Video();
        video2.setTitle("Test Video2");
        video2.setPostedBy(poster);

        video1 = videosRepo.save(video1);
        videosRepo.save(video2);

        assertEquals(video1.getId(), client.getVideo(video1.getId()).getId());
        assertEquals(video1.getTitle(), client.getVideo(video1.getId()).getTitle());
    }

    @Test
    public void testUpdateVideoExists() {
        Video video = new Video();
        video.setTitle("Test Video");
        video.setPostedBy(poster);
        video = videosRepo.save(video);

        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setTitle("Test Video2");

        User user = new User();
        user.setName("Test User2");
        user = userRepo.save(user);
        videoDTO.setPostedBy(user.getId());

        HttpResponse<Void> response = client.updateVideo(video.getId(), videoDTO);

        Video updatedVideo = videosRepo.findById(video.getId()).orElse(null);
        assertEquals(HttpStatus.OK, response.getStatus());
        if (updatedVideo != null) {
            assertEquals("Test Video2", updatedVideo.getTitle());
            assertEquals(user.getId(), updatedVideo.getPostedBy().getId());
        }
        else{
            assert false;
        }
    }
    @Test
    public void testDeleteVideo() {
        Video video = new Video();
        video.setTitle("Test Video");
        video.setPostedBy(poster);
        video = videosRepo.save(video);

        client.deleteVideo(video.getId());
        Iterable<Video> videos = client.list();
        assertFalse(videos.iterator().hasNext());
    }

    @Test
    public void testDeleteVideoNoVideo() {
        HttpResponse<Void> response = client.deleteVideo(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    public void testUpdateVideoNoVideo() {
        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setTitle("Test Video2");

        HttpResponse<Void> response = client.updateVideo(1L, videoDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    public void testListVideosByHashtag(){
        Video video = new Video();
        video.setTitle("Test Video");
        video.setPostedBy(poster);

        Video video2 = new Video();
        video2.setTitle("Test Video2");
        video2.setPostedBy(poster);

        HashSet<Hashtag> hashtags = new HashSet<>();
        hashtags.add(hashtag);
        video.setHashtags(hashtags);

        videosRepo.save(video);
        videosRepo.save(video2);

        List<Video> iterVideos = client.listVideosByHashtag("hashtag1");

        assertEquals(1, iterVideos.size());
        assertEquals("Test Video", iterVideos.get(0).getTitle());

    }

    @Test
    public void testListVideosByHashtagNoHashtag(){
        Video video = new Video();
        video.setTitle("Test Video");
        video.setPostedBy(poster);
        HashSet<Hashtag> hashtags = new HashSet<>();
        hashtags.add(hashtag);
        video.setHashtags(hashtags);

        videosRepo.save(video);

        List<Video> iterVideos = client.listVideosByHashtag("hashtag2");

        assertNull(iterVideos);

    }

    @Test
    public void testLikeVideo() {
        Video video = new Video();
        video.setTitle("Test Video");
        video.setPostedBy(poster);
        video = videosRepo.save(video);

        HttpResponse<Void> response = client.likeVideo(video.getId(), poster.getId());

        Video updatedVideo = videosRepo.findById(video.getId()).orElse(null);
        assertEquals(HttpStatus.OK, response.getStatus());
        if (updatedVideo != null) {
            assertEquals(1, updatedVideo.getLikes());
        }
        else{
            assert false;
        }
    }

    @Test
    public void testLikeVideoNoVideo() {
        HttpResponse<Void> response = client.likeVideo(1L, poster.getId());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    public void testLikeVideoNoUser() {
        Video video = new Video();
        video.setTitle("Test Video");
        video.setPostedBy(poster);
        video = videosRepo.save(video);

        HttpResponse<Void> response = client.likeVideo(video.getId(), poster.getId()+1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    public void testDislikeVideo() {
        Video video = new Video();
        video.setTitle("Test Video");
        video.setPostedBy(poster);
        video = videosRepo.save(video);

        HttpResponse<Void> response = client.dislikeVideo(video.getId(), poster.getId());

        Video updatedVideo = videosRepo.findById(video.getId()).orElse(null);
        assertEquals(HttpStatus.OK, response.getStatus());
        if (updatedVideo != null) {
            assertEquals(1, updatedVideo.getDislikes());
        }
        else{
            assert false;
        }
        assertTrue(dislikeVideo.containsKey(poster.getId()));
        assertEquals(video.getId(), dislikeVideo.get(poster.getId()).getId());

    }

    @Test
    public void testDislikeVideoNoVideo() {
        HttpResponse<Void> response = client.dislikeVideo(1L, poster.getId());
        assertTrue(dislikeVideo.isEmpty());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
    }

    @Test
    public void testDislikeVideoNoUser() {
        Video video = new Video();
        video.setTitle("Test Video");
        video.setPostedBy(poster);
        video = videosRepo.save(video);
        assertTrue(dislikeVideo.isEmpty());
        HttpResponse<Void> response = client.dislikeVideo(video.getId(), poster.getId()+1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
    }


}