package uk.ac.york.eng2.videos.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.eng2.videos.clients.HashtagsClient;
import uk.ac.york.eng2.videos.clients.UsersClient;
import uk.ac.york.eng2.videos.domain.Hashtag;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.HashtagDTO;
import uk.ac.york.eng2.videos.dto.UserDTO;
import uk.ac.york.eng2.videos.repositories.HashtagsRepository;
import uk.ac.york.eng2.videos.repositories.UsersRepository;
import uk.ac.york.eng2.videos.repositories.VideosRepository;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(transactional = false)
public class HashtagsControllerTest {

    @Inject
    HashtagsClient client;

    @Inject
    HashtagsRepository hashtagsRepo;


    @BeforeEach
    void setup() {
        hashtagsRepo.deleteAll();
    }
    @Test
    public void testListHashtags() {
        HashtagDTO hashtagDTO = new HashtagDTO("hashtag1");
        client.add(hashtagDTO);
        Iterable<Hashtag> hashtags = client.list();
        assert (hashtags.iterator().hasNext());
        assertTrue(hashtags.iterator().next().getName().equals("hashtag1"));
    }

    @Test
    public void testGetHashtag() {
        Hashtag hashtag = new Hashtag();
        hashtag.setName("hashtag1");
        hashtagsRepo.save(hashtag);
        Hashtag retrievedHashtag = client.getHashtag(hashtag.getId());
        assertEquals("hashtag1", retrievedHashtag.getName());
    }

    @Test
    public void testAddHashtag() {
        HashtagDTO hashtagDTO = new HashtagDTO("hashtag1");
        HttpResponse<Void> response = client.add(hashtagDTO);

        assertEquals(HttpStatus.CREATED, response.getStatus());
        Iterable<Hashtag> hashtags = client.list();

        assert (hashtags.iterator().hasNext());
        assertTrue(hashtags.iterator().next().getName().equals("hashtag1"));
    }





}