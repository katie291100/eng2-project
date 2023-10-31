package uk.ac.york.eng2.videos.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.eng2.videos.clients.UsersClient;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.UserDTO;
import uk.ac.york.eng2.videos.repositories.HashtagsRepository;
import uk.ac.york.eng2.videos.repositories.UsersRepository;
import uk.ac.york.eng2.videos.repositories.VideosRepository;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(transactional = false)
public class UsersControllerTest {

    @Inject
    UsersClient client;

    @Inject
    VideosRepository videosRepo;

    @Inject
    UsersRepository userRepo;

    @Inject
    HashtagsRepository hashtagsRepo;

    @BeforeEach
    void setup() {
        videosRepo.deleteAll();
        userRepo.deleteAll();
        hashtagsRepo.deleteAll();
    }
//    static Map<Long, Video>
//            postsAdded,
//            watchVideo,
//            likeVideo,
//            dislikeVideo = new java.util.HashMap<>();

    @Test
    public void testListUsers() {
        User user = new User();
        user.setName("test name");
        userRepo.save(user);
        Iterable<User> users = client.list();
        assertTrue(users.iterator().hasNext());
    }

    @Test
    public void testGetUser() {
        User user = new User();
        user.setName("test name");
        userRepo.save(user);
        User user2 = client.getUser(user.getId());
        assertEquals(user.getId(), user2.getId());
    }

    @Test
    public void testGetUserNotFound() {
        User user = client.getUser(1L);
        assertNull(user);
    }

    @Test
    public void testGetWatchedByUser() {
        User user = new User();
        user.setName("test name");
        user = userRepo.save(user);

        Video video = new Video();
        video.setTitle("test title");
        video.setPostedBy(user);
        videosRepo.save(video);

        user.addWatchedVideo(video);
        userRepo.update(user);
        Set<Video> videos = client.getWatchedByUser(user.getId());
        assertTrue(videos.iterator().hasNext());
        assertEquals(video.getId(), videos.iterator().next().getId());
    }

    @Test
    public void testGetWatchedByUserNotFound() {
        Set<Video> videos = client.getWatchedByUser(1L);
        assertNull(videos);
    }

    @Test
    public void testAddUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("test name");
        client.add(userDTO);
        Iterable<User> users = client.list();
        assertTrue(users.iterator().hasNext());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setName("Test Name");
        user = userRepo.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test Name2");
        client.updateUser(user.getId(), userDTO);

        User user2 = client.getUser(user.getId());
        assertEquals(userDTO.getName(), user2.getName());
    }

    @Test
    public void testUpdateUserNotFound() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test Name2");
        client.updateUser(1L, userDTO);

        User user2 = client.getUser(1L);
        assertNull(user2);
    }

    @Test
    public void testWatchedVideo() {
        User user = new User();
        user.setName("test name");
        user = userRepo.save(user);

        Video video = new Video();
        video.setTitle("test title");
        video.setPostedBy(user);
        videosRepo.save(video);

        client.watchedVideo(user.getId(), video.getId());
        Set<Video> videos = client.getWatchedByUser(user.getId());
        assertTrue(videos.iterator().hasNext());
        assertEquals(video.getId(), videos.iterator().next().getId());
    }

    @Test
    public void testWatchedVideoNotFound() {
        Set<Video> videos = client.getWatchedByUser(1L);
        assertNull(videos);
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setName("test name");
        user = userRepo.save(user);
        HttpResponse<Void> response = client.deleteUser(user.getId());
        Iterable<User> users = client.list();
        assertFalse(users.iterator().hasNext());
        assertEquals(HttpStatus.OK, response.getStatus());

    }

    @Test
    public void testDeleteUserNotFound() {
        HttpResponse<Void> response = client.deleteUser(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
    }

//    @Test
//    private VideoProducer getVideoProducer() {
//        return new VideoProducer() {
//            @Override
//            public void postVideo(Long key, Video b) {
//                postsAdded.put(key, b);
//            }
//
//            @Override
//            public void watchVideo(Long key, Video b) {
//                watchVideo.put(key, b);
//            }
//
//            @Override
//            public void likeVideo(Long key, Video b) {
//                likeVideo.put(key, b);
//
//            }
//
//            @Override
//            public void dislikeVideo(Long key, Video b) {
//                dislikeVideo.put(key, b);
//
//            }
//        };
//    }



}