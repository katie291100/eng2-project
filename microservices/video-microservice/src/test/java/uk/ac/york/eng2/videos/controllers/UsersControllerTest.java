package uk.ac.york.eng2.videos.controllers;

import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.eng2.videos.clients.UsersClient;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.UserDTO;
import uk.ac.york.eng2.videos.events.UserProducer;
import uk.ac.york.eng2.videos.repositories.HashtagsRepositoryExtended;
import uk.ac.york.eng2.videos.repositories.UsersRepositoryExtended;
import uk.ac.york.eng2.videos.repositories.VideosRepositoryExtended;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(transactional = false, environments = "no_streams")
public class UsersControllerTest {

    @Inject
    UsersClient client;

    @Inject
    VideosRepositoryExtended videosRepo;

    @Inject
    UsersRepositoryExtended userRepo;

    @Inject
    HashtagsRepositoryExtended hashtagsRepo;

    Map<Long, Video> watchedVideo = new HashMap<>();
    Map<Long, Video> newUser = new HashMap<>();

    @BeforeEach
    void setup() {
        videosRepo.deleteAll();
        userRepo.deleteAll();
        hashtagsRepo.deleteAll();
        watchedVideo.clear();
        newUser.clear();
    }

    @Test
    public void testListUsers() {
        User user = new User();
        user.setName("test name");
        userRepo.save(user);
        Iterable<User> users = client.listUser();
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
        Set<Video> userWatchedVideo = new HashSet<>();
        userWatchedVideo.add(video);
        user.setWatchedVideos(userWatchedVideo);
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
        client.addUser(userDTO);
        Iterable<User> users = client.listUser();
        assertTrue(users.iterator().hasNext());
    }
//
//    @Test
//    public void testUpdateUser() {
//        User user = new User();
//        user.setName("Test Name");
//        user = userRepo.save(user);
//
//        UserDTO userDTO = new UserDTO();
//        userDTO.setName("Test Name2");
//        client.updateUser(user.getId(), userDTO);
//
//        User user2 = client.getUser(user.getId());
//        assertEquals(userDTO.getName(), user2.getName());
//    }
//
//    @Test
//    public void testUpdateUserNotFound() {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setName("Test Name2");
//        client.updateUser(1L, userDTO);
//
//        User user2 = client.getUser(1L);
//        assertNull(user2);
//        assertEquals(watchedVideo.size(), 0);
//    }

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
        assertEquals(1, watchedVideo.size());
        assertEquals(video.getId(), watchedVideo.get(user.getId()).getId());
    }

    @Test
    public void testWatchedVideoNotFound() {
        Set<Video> videos = client.getWatchedByUser(1L);
        assertNull(videos);
    }
//
//    @Test
//    public void testDeleteUser() {
//        User user = new User();
//        user.setName("test name");
//        user = userRepo.save(user);
//        HttpResponse<Void> response = client.deleteUser(user.getId());
//        Iterable<User> users = client.list();
//        assertFalse(users.iterator().hasNext());
//        assertEquals(HttpStatus.OK, response.getStatus());
//
//    }
//
//    @Test
//    public void testDeleteUserNotFound() {
//        HttpResponse<Void> response = client.deleteUser(1L);
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
//    }

    @MockBean(UserProducer.class)
    UserProducer userProducer() {
        return new UserProducer() {
            @Override
            public void newUser(Long key, User b) {

            }

            @Override
            public void watchVideo(Long key, Video b) {
                watchedVideo.put(key, b);
            }

        };
    }



    }