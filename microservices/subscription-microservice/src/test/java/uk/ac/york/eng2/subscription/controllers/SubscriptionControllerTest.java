package uk.ac.york.eng2.subscription.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import uk.ac.york.eng2.subscription.domain.Hashtag;
import uk.ac.york.eng2.subscription.domain.User;
import uk.ac.york.eng2.subscription.domain.Video;
import uk.ac.york.eng2.subscription.events.TestProducerUtil;
import uk.ac.york.eng2.subscription.repositories.HashtagsRepositoryExtended;
import uk.ac.york.eng2.subscription.repositories.UserRepositoryExtended;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest(transactional = false)
class SubscriptionControllerTest {

    @Inject
    SubscriptionClient  subscriptionClient;

    @Inject
    HashtagsRepositoryExtended hashtagsRepository;

    @Inject
    UserRepositoryExtended userRepository;

    @Inject
    TestProducerUtil testProducerUtil;

    @Inject
    KafkaStreams kStreams;

    @BeforeEach
    void setUp() {
        Awaitility.await().atMost(60, TimeUnit.SECONDS).until(() -> kStreams.state().equals(KafkaStreams.State.RUNNING));
        hashtagsRepository.deleteAll();
        userRepository.deleteAll();

    }


    @AfterAll
    public void cleanUp() { kStreams.close(); }


    @Test
    void testListAllSubscriptionsValid() throws InterruptedException {

        Video video = new Video();
        video.setId(1L);
        Hashtag hashtag = new Hashtag();
        hashtag.setId(2L);
        hashtag.setName("test");
        hashtagsRepository.save(hashtag);
        video.setHashtags(Set.of(hashtag));

        User user = new User();
        user.setId(3L);
        user.setName("test");
        user.setSubscriptions(Set.of(hashtag));
        userRepository.save(user);

        User user2 = new User();
        user2.setId(4L);
        user2.setName("trigger");
        user2.setSubscriptions(Set.of(hashtag));
        userRepository.save(user2);

        testProducerUtil.postVideo(1L, video);
        Video video2 = new Video();
        video2.setId(5L);
        video2.setHashtags(Set.of(hashtag));
        testProducerUtil.postVideo(3L, video2);
        sleep(5000);
        testProducerUtil.watchVideo(4L, video);
        testProducerUtil.watchVideo(4L, video2);

        sleep(20000);
        List<SubscriptionRecord> result = subscriptionClient.listAllSubscriptions();
        SubscriptionRecord subscriptionRecord = new SubscriptionRecord(user.getId(), hashtag.getId(), List.of(1L, 5L));
        SubscriptionRecord subscriptionRecord2 = new SubscriptionRecord(user2.getId(), hashtag.getId(), new ArrayList<>());

        assertTrue(result.contains(subscriptionRecord));
        assertTrue(result.contains(subscriptionRecord2));
    }


    @Test
    void testSortedNextVideosThreeVideosValid() throws InterruptedException {
        ReadOnlyKeyValueStore<Long, Long> store = new ReadOnlyKeyValueStore<Long, Long>() {
            @Override
            public Long get(Long key) {
                if (key == 1L) {
                    return 4L;
                }
                if (key == 2L) {
                    return 1L;
                }
                if (key == 3L) {
                    return 2L;
                }
                return null;
            }

            @Override
            public KeyValueIterator<Long, Long> range(Long from, Long to) {
                return null;
            }

            @Override
            public KeyValueIterator<Long, Long> all() {
                return null;
            }

            @Override
            public long approximateNumEntries() {
                return 0;
            }

        };
        ArrayList<Long> list = new ArrayList<>(List.of(1L, 2L, 3L));

        List<Long> sortedList= SubscriptionController.sortedNextVideos(list, store);

        assertEquals(List.of(1L, 3L, 2L), sortedList);
    }


    @Test
    void testSortedNextVideosNoVideosValid() throws InterruptedException {
        ReadOnlyKeyValueStore<Long, Long> store = new ReadOnlyKeyValueStore<Long, Long>() {
            @Override
            public Long get(Long key) {

                return null;
            }

            @Override
            public KeyValueIterator<Long, Long> range(Long from, Long to) {
                return null;
            }

            @Override
            public KeyValueIterator<Long, Long> all() {
                return null;
            }

            @Override
            public long approximateNumEntries() {
                return 0;
            }

        };
        ArrayList<Long> list = new ArrayList<>();

        List<Long> sortedList= SubscriptionController.sortedNextVideos(list, store);

        assertTrue(sortedList.isEmpty());
    }

    @Test
    void testSortedNextVideosTooManyVideosValid() {
        ReadOnlyKeyValueStore<Long, Long> store = new ReadOnlyKeyValueStore<Long, Long>() {
            @Override
            public Long get(Long key) {
                switch (key.intValue()) {
                    case 1:
                        return 4L;
                    case 2:
                        return 1L;
                    case 3:
                        return 2L;
                    case 4:
                        return 3L;
                    case 5:
                        return 5L;
                    case 6:
                        return 1L;
                    case 7:
                        return 16L;
                    case 8:
                        return 17L;
                    case 9:
                        return 1L;
                    case 10:
                        return 19L;
                    case 11:
                        return 5L;
                    case 12:
                        return 21L;
                    case 13:
                        return 3L;

                }
                return null;

            }

            @Override
            public KeyValueIterator<Long, Long> range(Long from, Long to) {
                return null;
            }

            @Override
            public KeyValueIterator<Long, Long> all() {
                return null;
            }

            @Override
            public long approximateNumEntries() {
                return 0;
            }

        };
        ArrayList<Long> list = new ArrayList<>(List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L ,9L, 10L, 11L, 12L, 13L));

        List<Long> sortedList= SubscriptionController.sortedNextVideos(list, store);

        assertEquals(List.of(12L, 10L, 8L, 7L, 5L, 11L, 1L, 4L, 13L, 3L), sortedList);
    }

    @Test
    void testListVideosSubscriptionValid() throws InterruptedException {
        Video video = new Video();
        video.setId(50L);
        Hashtag hashtag = new Hashtag();
        hashtag.setId(6L);
        hashtag.setName("test");
        hashtagsRepository.save(hashtag);
        video.setHashtags(Set.of(hashtag));

        User user = new User();
        user.setId(7L);
        user.setName("test");
        user.setSubscriptions(Set.of(hashtag));
        userRepository.save(user);

        User user2 = new User();
        user2.setId(8L);
        user2.setName("trigger");
        userRepository.save(user2);

        testProducerUtil.postVideo(8L, video);
        Video video2 = new Video();
        video2.setId(9L);
        video2.setHashtags(Set.of(hashtag));
        testProducerUtil.postVideo(8L, video2);
        sleep(5000);
        testProducerUtil.watchVideo(8L, video);

        sleep(20000);
        List<Long> result = subscriptionClient.listVideosSubscription(user.getId(), hashtag.getId());

        assertEquals(List.of(50L, 9L), result);
    }


    @Test
    void getSubscriptionNone() throws InterruptedException {

        Hashtag hashtag = new Hashtag();
        hashtag.setId(10L);
        hashtag.setName("test");
        hashtagsRepository.save(hashtag);

        User user = new User();
        user.setId(11L);
        user.setName("test");
        user.setSubscriptions(Set.of(hashtag));
        userRepository.save(user);

        sleep(20000);
        List<Long> result = subscriptionClient.listVideosSubscription(user.getId(), hashtag.getId());

        assertEquals(result, new ArrayList<>());
    }

    @Test
    void testListVideosSubscriptionNoneValid() throws InterruptedException {

        Hashtag hashtag = new Hashtag();
        hashtag.setId(12L);
        hashtag.setName("test");
        hashtagsRepository.save(hashtag);

        User user = new User();
        user.setId(13L);
        user.setName("test");
        user.setSubscriptions(Set.of(hashtag));
        userRepository.save(user);
        sleep(30000);
        List<Long> result = subscriptionClient.listVideosSubscription(user.getId(), hashtag.getId());

        assertEquals(new ArrayList<>(), result);
    }
    @Test
    void testListVideosSubscriptionUserInvalidError() throws InterruptedException {

        Hashtag hashtag = new Hashtag();
        hashtag.setId(12L);
        hashtag.setName("test");
        hashtagsRepository.save(hashtag);

        sleep(30000);
        List<Long> result = subscriptionClient.listVideosSubscription(14L, hashtag.getId());

        assertEquals(null, result);
    }
    @Test
    void testListVideosSubscriptionHashtagInvalidError() throws InterruptedException {
        User user = new User();
        user.setId(14L);
        user.setName("test");
        userRepository.save(user);
        sleep(30000);
        List<Long> result = subscriptionClient.listVideosSubscription(user.getId(), 14L);

        assertEquals(null, result);
    }
    @Test
    void testSubscribeUserHashtagValid() {
        Hashtag hashtag = new Hashtag();
        hashtag.setId(15L);
        hashtag.setName("test");
        hashtagsRepository.save(hashtag);

        User user = new User();
        user.setId(16L);
        user.setName("test");
        userRepository.save(user);
        HttpResponse<Void> result = subscriptionClient.subscribe(15L, 16L);

        assertEquals(HttpStatus.CREATED, result.getStatus());
        assertEquals("/subscription/15/16", result.getHeaders().get("location"));

        user = userRepository.findById(16L).orElse(null);
        assert user != null;
        assertEquals(Set.of(hashtag), user.getSubscriptions());
    }

    @Test
    void testUnsubscribeUserHashtagValid() {
        Hashtag hashtag = new Hashtag();
        hashtag.setId(15L);
        hashtag.setName("test");
        hashtagsRepository.save(hashtag);

        User user = new User();
        user.setId(16L);
        user.setName("test");
        userRepository.save(user);
        HttpResponse<Void> result = subscriptionClient.subscribe(15L, 16L);

        assertEquals( result.getStatus(), HttpStatus.CREATED);
        assertEquals("/subscription/15/16", result.getHeaders().get("location"));

        user = userRepository.findById(16L).orElse(null);
        assert user != null;
        assertEquals(Set.of(hashtag), user.getSubscriptions());

        result = subscriptionClient.unsubscribe(15L, 16L);

        user = userRepository.findById(16L).orElse(null);
        assert user != null;
        assertEquals(HttpStatus.OK, result.getStatus());
        assertFalse(user.getSubscriptions().contains(hashtag));
    }



    @Test
    void testListUserSubscriptionsValid() throws InterruptedException {
        Video video = new Video();
        video.setId(17L);
        Hashtag hashtag = new Hashtag();
        hashtag.setId(18L);
        hashtag.setName("test");
        hashtagsRepository.save(hashtag);

        Hashtag hashtag2 = new Hashtag();
        hashtag2.setId(19L);
        hashtag2.setName("test");
        hashtagsRepository.save(hashtag2);

        Hashtag hashtag3 = new Hashtag();
        hashtag3.setId(20L);
        hashtag3.setName("test");
        hashtagsRepository.save(hashtag3);

        video.setHashtags(Set.of(hashtag, hashtag3));

        User user = new User();
        user.setId(21L);
        user.setName("test");
        user.setSubscriptions(Set.of(hashtag, hashtag2, hashtag3));
        userRepository.save(user);

        User user2 = new User();
        user2.setId(22L);
        user2.setName("trigger");
        userRepository.save(user2);

        testProducerUtil.postVideo(user.getId(), video);
        Video video2 = new Video();
        video2.setId(9L);
        video2.setHashtags(Set.of(hashtag));
        testProducerUtil.postVideo(user.getId(), video2);
        sleep(5000);
        testProducerUtil.watchVideo(user2.getId(), video);

        sleep(20000);
        List<SubscriptionRecord> result = subscriptionClient.listUserSubscriptions(user.getId());
        SubscriptionRecord subscriptionRecord = new SubscriptionRecord(user.getId(), hashtag.getId(), List.of(17L, 9L));
        SubscriptionRecord subscriptionRecord2 = new SubscriptionRecord(user.getId(), hashtag2.getId(), new ArrayList<>());
        SubscriptionRecord subscriptionRecord3 = new SubscriptionRecord(user.getId(), hashtag3.getId(), List.of(17L));
        assertTrue(result.containsAll(List.of(subscriptionRecord, subscriptionRecord2, subscriptionRecord3)));
    }
}