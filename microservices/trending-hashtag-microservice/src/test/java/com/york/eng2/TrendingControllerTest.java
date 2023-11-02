package com.york.eng2;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest(transactional = false)
public class TrendingControllerTest {

    @Inject
    TrendingClient client;

//    @BeforeEach
//    void setup() {
//        hashtagsRepo.deleteAll();
//    }
//    static Map<Long, Video>
//            postsAdded,
//            watchVideo,
//            likeVideo,
//            dislikeVideo = new java.util.HashMap<>();
    @Test
    public void testListHashtags() {
        client.list();
        assert true;
    }
//
//    @Test
//    public void testGetHashtag() {
//        Hashtag hashtag = new Hashtag();
//        hashtag.setName("hashtag1");
//        hashtagsRepo.save(hashtag);
//        Hashtag retrievedHashtag = client.getHashtag(hashtag.getId());
//        assertEquals("hashtag1", retrievedHashtag.getName());
//    }
//
//    @Test
//    public void testAddHashtag() {
//        HashtagDTO hashtagDTO = new HashtagDTO("hashtag1");
//        HttpResponse<Void> response = client.add(hashtagDTO);
//
//        assertEquals(HttpStatus.CREATED, response.getStatus());
//        Iterable<Hashtag> hashtags = client.list();
//
//        assert (hashtags.iterator().hasNext());
//        assertTrue(hashtags.iterator().next().getName().equals("hashtag1"));
//    }
//
////    @Test
////    private VideoProducer getVideoProducer() {
////        return new VideoProducer() {
////            @Override
////            public void postVideo(Long key, Video b) {
////                postsAdded.put(key, b);
////            }
////
////            @Override
////            public void watchVideo(Long key, Video b) {
////                watchVideo.put(key, b);
////            }
////
////            @Override
////            public void likeVideo(Long key, Video b) {
////                likeVideo.put(key, b);
////
////            }
////
////            @Override
////            public void dislikeVideo(Long key, Video b) {
////                dislikeVideo.put(key, b);
////
////            }
////        };
////    }
//


}