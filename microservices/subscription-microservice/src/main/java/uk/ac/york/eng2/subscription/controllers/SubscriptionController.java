package uk.ac.york.eng2.subscription.controllers;

import io.micronaut.configuration.kafka.streams.InteractiveQueryService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import uk.ac.york.eng2.subscription.domain.Hashtag;
import uk.ac.york.eng2.subscription.domain.User;
import uk.ac.york.eng2.subscription.events.SubscriptionIdentifier;
import uk.ac.york.eng2.subscription.repositories.HashtagsRepositoryExtended;
import uk.ac.york.eng2.subscription.repositories.UserRepositoryExtended;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Controller("/subscription")
public class SubscriptionController implements SubscriptionControllerInterface{

    @Inject
    InteractiveQueryService interactiveQueryService;

    @Inject
    UserRepositoryExtended usersRepository;

    @Inject
    HashtagsRepositoryExtended hashtagsRepository;

    @Get("/{userId}/{hashtagId}")
    public List<Long> listVideosSubscription(Long userId, Long hashtagId) {
        User user = usersRepository.findById(userId).orElse(null);
        Hashtag hashtag = hashtagsRepository.findById(hashtagId).orElse(null);
        if (user == null || hashtag == null) {
            return null;
        }
        ReadOnlyKeyValueStore<SubscriptionIdentifier, List<Long>> queryableStore = getStore("hashtag-video-store-final");
        ReadOnlyKeyValueStore<Long, Long> queryableStoreWatchers = interactiveQueryService.getQueryableStore("watch-video-store", QueryableStoreTypes.<Long, Long>keyValueStore()).orElse(null);

        List<Long> videos = queryableStore.get(new SubscriptionIdentifier(userId, hashtagId));
        if (videos == null) {
            return new ArrayList<>();
        }
        return sortedNextVideos(videos, queryableStoreWatchers);
    }

    @Get("/")
    public  List<SubscriptionRecord> listAllSubscriptions() {
        ReadOnlyKeyValueStore<SubscriptionIdentifier, List<Long>> queryableStore = getStore("hashtag-video-store-final");
        ReadOnlyKeyValueStore<Long, Long> queryableStoreWatchers = interactiveQueryService.getQueryableStore("watch-video-store", QueryableStoreTypes.<Long, Long>keyValueStore()).orElse(null);
        List<SubscriptionRecord> subscriptionRecords = new ArrayList<>();
        queryableStore.all().forEachRemaining((value) -> {
            subscriptionRecords.add(new SubscriptionRecord(value.key.getId(), value.key.getHashtagId(), sortedNextVideos(value.value, queryableStoreWatchers)));
        });

        return subscriptionRecords;
    }

    @Put("/{hashtagId}/{userId}")
    public HttpResponse<Void> subscribe(Long hashtagId, Long userId) {
        System.out.println("OOPS1");

        if (hashtagId == null || userId == null) {
            return HttpResponse.badRequest();
        }
        System.out.println("OOPS2");

        usersRepository.findAll().forEach((user) -> System.out.println(user));
        hashtagsRepository.findAll().forEach((user) -> System.out.println(user));
        User user = usersRepository.findById(userId).orElse(null);
        Hashtag hashtag = hashtagsRepository.findById(hashtagId).orElse(null);
        if (user == null || hashtag == null) {
            System.out.println("OOPS3");

            return HttpResponse.notFound();
        }
        Set<Hashtag> subscriptions = user.getSubscriptions();
        subscriptions.add(hashtag);
        System.out.println("h: " + hashtag);
        user.setSubscriptions(subscriptions);
        System.out.println("user: " + user);
        System.out.println("user: " + user.getSubscriptions());

        usersRepository.update(user);
        System.out.println("user: " + usersRepository.findById(userId).orElse(null).getSubscriptions());
        return HttpResponse.created(URI.create("/subscription/" + hashtagId + "/" + userId));
    }

    @Delete("/{hashtagId}/{userId}")
    public HttpResponse<Void> unsubscribe(Long hashtagId, Long userId) {
        if (hashtagId == null || userId == null) {
            return HttpResponse.badRequest();
        }
        usersRepository.findAll().forEach((user) -> System.out.println(user));
        hashtagsRepository.findAll().forEach((user) -> System.out.println(user));

        User user = usersRepository.findById(userId).orElse(null);
        Hashtag hashtag = hashtagsRepository.findById(hashtagId).orElse(null);
        if (user == null || hashtag == null) {
            return HttpResponse.notFound();
        }
        Set<Hashtag> subscriptions = user.getSubscriptions();
        subscriptions.remove(hashtag);
        user.setSubscriptions(subscriptions);

        usersRepository.update(user);
        return HttpResponse.ok();
    }

    @Transactional
    @Get("/user/{userId}")
    public List<SubscriptionRecord> listUserSubscriptions(Long userId) {
        User user = usersRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        ReadOnlyKeyValueStore<SubscriptionIdentifier, List<Long>> queryableStore = getStore("hashtag-video-store-final");
        ReadOnlyKeyValueStore<Long, Long> queryableStoreWatchers = interactiveQueryService.getQueryableStore("watch-video-store", QueryableStoreTypes.<Long, Long>keyValueStore()).orElse(null);
        List<SubscriptionRecord> subscriptions = new ArrayList<>();
        user.getSubscriptions().forEach(
                (hashtag) -> {
                    List<Long> videos = queryableStore.get(new SubscriptionIdentifier(userId, hashtag.getId()));
                    if (videos == null) {
                        videos = new ArrayList<>();
                    }
                    subscriptions.add(new SubscriptionRecord(userId, hashtag.getId(), sortedNextVideos(videos, queryableStoreWatchers)));
                }
        );


        return subscriptions;
    }
    private ReadOnlyKeyValueStore<SubscriptionIdentifier, List<Long>> getStore(String name) {

        return interactiveQueryService.getQueryableStore(name, QueryableStoreTypes.<SubscriptionIdentifier, List<Long>>keyValueStore()).orElse(null);
    }

    public static List<Long> sortedNextVideos(List<Long> videos, ReadOnlyKeyValueStore<Long, Long> queryableStoreWatchers) {
        videos.sort(
                (o1, o2) -> {
                    Long o1Watchers = queryableStoreWatchers.get(o1);
                    Long o2Watchers = queryableStoreWatchers.get(o2);
                    if (o1Watchers == null) {
                        o1Watchers = 0L;
                    }
                    if (o2Watchers == null) {
                        o2Watchers = 0L;
                    }
                    return o2Watchers.compareTo(o1Watchers);
                }
        );

        ArrayList<Long> subscriptionVideos = new ArrayList<>();
        if (videos.size() < 10) {
            subscriptionVideos.addAll(videos);
        }
        else {
            subscriptionVideos.addAll(videos.subList(0, 10));
        }


        return subscriptionVideos;
    }
}

