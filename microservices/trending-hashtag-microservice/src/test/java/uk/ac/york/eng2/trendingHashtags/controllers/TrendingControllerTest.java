package uk.ac.york.eng2.trendingHashtags.controllers;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.apache.kafka.streams.KafkaStreams;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.*;
import uk.ac.york.eng2.trendingHashtags.domain.Hashtag;
import uk.ac.york.eng2.trendingHashtags.domain.Video;
import uk.ac.york.eng2.trendingHashtags.events.TestProducerUtil;
import uk.ac.york.eng2.trendingHashtags.repositories.HashtagsRepository;


import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest(transactional = false)
public class TrendingControllerTest {

    @Inject
    TrendingClient trendingClient;

    @Inject
    HashtagsRepository hashtagsRepository;

    @Inject
    TestProducerUtil testProducerUtil;

    @Inject
    KafkaStreams kStreams;

    @BeforeEach
    public void setUp() {
        Awaitility.await().atMost(30, TimeUnit.SECONDS).until(() -> kStreams.state().equals(KafkaStreams.State.RUNNING));
        hashtagsRepository.deleteAll();
    }

    @AfterAll public void cleanUp() { kStreams.close(); }

    @Test
    public void testOneVideoLiked() throws InterruptedException {
        Video video = new Video();
        video.setId(1L);
        Hashtag hashtag = new Hashtag();
        hashtag.setId(2L);
        hashtag.setName("test");
        video.setHashtags(Set.of(hashtag));

        testProducerUtil.likeVideo(1L, video);
        sleep(30000);
        List<Long> result = trendingClient.list();
        assertTrue(result.contains(2L));
    }


    @Test
    public void testTwoVideoLiked() throws InterruptedException {
        Video video = new Video();
        video.setId(1L);
        Hashtag hashtag = new Hashtag();
        hashtag.setId(2L);
        hashtag.setName("test");
        video.setHashtags(Set.of(hashtag));

        Video video2 = new Video();
        video2.setId(2L);
        video2.setTitle("test2");
        Hashtag hashtag2 = new Hashtag();
        hashtag2.setId(3L);
        hashtag2.setName("test1");
        video2.setHashtags(Set.of(hashtag2));

        testProducerUtil.likeVideo(1L, video);
        testProducerUtil.likeVideo(1L, video2);

        sleep(30000);
        List<Long> result = trendingClient.list();
        assertEquals(result.contains(2L), true);
        assertEquals(result.contains(3L), true);
    }

    @Test
    public void testMoreThanTenHashtagss() throws InterruptedException {
        Hashtag hashtag1 = new Hashtag();
        hashtag1.setName("test");
        hashtag1.setId(13L);
        Video video2 = new Video();
        video2.setId(2L);
        video2.setTitle("test2");
        video2.setHashtags(Set.of(hashtag1));

        testProducerUtil.likeVideo(1L, video2);

        Video video = new Video();
        video.setId(1L);

        Set<Hashtag> hashtags = new HashSet<>();
        for (int i = 50; i < 60; i++) {
            Hashtag hashtag = new Hashtag();
            hashtag.setId((long) i);
            hashtag.setName("test" + i);
            hashtags.add(hashtag);
        }
        video.setHashtags(hashtags);

        testProducerUtil.likeVideo(1L, video);
        testProducerUtil.likeVideo(2L, video);
        testProducerUtil.likeVideo(2L, video);
        testProducerUtil.likeVideo(2L, video);

        sleep(30000);
        List<Long> result = trendingClient.list();
        assertFalse(result.contains(hashtag1.getId()));
        for (int i = 50; i < 60; i++) {
            assertTrue(result.contains((long) i));

        }
    }
}