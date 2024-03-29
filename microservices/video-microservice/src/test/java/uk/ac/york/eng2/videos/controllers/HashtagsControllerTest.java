package uk.ac.york.eng2.videos.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.york.eng2.videos.clients.HashtagsClient;
import uk.ac.york.eng2.videos.domain.Hashtag;
import uk.ac.york.eng2.videos.dto.HashtagDTO;
import uk.ac.york.eng2.videos.events.HashtagProducer;
import uk.ac.york.eng2.videos.repositories.HashtagsRepositoryExtended;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(transactional = false)
public class HashtagsControllerTest {

    @Inject
    HashtagsClient client;

    @Inject
    HashtagsRepositoryExtended hashtagsRepo;

    Map<Long, Hashtag> newHashtag = new HashMap<>();


    @MockBean(HashtagProducer.class)
    HashtagProducer hashtagProducer() {
        return new HashtagProducer() {
            @Override
            public void newHashtag(Long id, Hashtag hashtag) {
                newHashtag.put(id, hashtag);
            }
        };
    }

    @BeforeEach
    void setup() {
        hashtagsRepo.deleteAll();
    }

    @Test
    public void testListHashtagsNoneHashtags() {

        Iterable<Hashtag> hashtags = client.list();
        assertFalse(hashtags.iterator().hasNext());
    }

    @Test
    public void testListHashtagsHasHashtagValid() {
        HashtagDTO hashtagDTO = new HashtagDTO();
        hashtagDTO.setName("hashtag1");
        client.add(hashtagDTO);
        Iterable<Hashtag> hashtags = client.list();
        assert (hashtags.iterator().hasNext());
        assertEquals("hashtag1", hashtags.iterator().next().getName());
    }

    @Test
    public void testGetHashtagHashtagValid() {
        Hashtag hashtag = new Hashtag();
        hashtag.setName("hashtag2");
        hashtagsRepo.save(hashtag);
        Hashtag retrievedHashtag = client.getHashtag(hashtag.getId());
        assertEquals("hashtag2", retrievedHashtag.getName());
    }

    @Test
    public void testGetHashtagHashtagInvalid() {
        Hashtag retrievedHashtag = client.getHashtag(0L);
        assertNull(retrievedHashtag);
    }

    @Test
    public void testAddHashtagValid() {
        HashtagDTO hashtagDTO = new HashtagDTO();
        hashtagDTO.setName("hashtag3");
        HttpResponse<Void> response = client.add(hashtagDTO);

        assertEquals(HttpStatus.CREATED, response.getStatus());
        Iterable<Hashtag> hashtags = client.list();
        assertTrue(newHashtag.containsKey(hashtags.iterator().next().getId()));
        assert (hashtags.iterator().hasNext());
        assertEquals("hashtag3", hashtags.iterator().next().getName());
    }





}