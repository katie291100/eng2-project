package uk.ac.york.eng2.subscription.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;
import uk.ac.york.eng2.subscription.domain.Hashtag;
import uk.ac.york.eng2.subscription.domain.User;
import uk.ac.york.eng2.subscription.domain.Video;
import uk.ac.york.eng2.subscription.repositories.HashtagsRepositoryExtended;
import uk.ac.york.eng2.subscription.repositories.UserRepositoryExtended;
import uk.ac.york.eng2.subscription.repositories.UsersRepository;
import uk.ac.york.eng2.subscription.repositories.VideoRepository;

import java.util.Set;

@KafkaListener(groupId = "SubscriptionConsumer")
public class SubscriptionConsumer {

    @Inject
    HashtagsRepositoryExtended hashtagRepo;

    @Inject
    UserRepositoryExtended userRepo;

    @Inject
    VideoRepository videoRepo;

    @Topic("watch-video")
    void watchConsumer(@KafkaKey long id, Video video) {
        User user;
        System.out.println("video " + video.getId() + " by user: " + id);

        for (Hashtag hashtag : video.getHashtags()) {
            if (hashtagRepo.findById(hashtag.getId()).isEmpty()) {
                hashtagRepo.save(hashtag);
            }
        }
        if (videoRepo.findById(video.getId()).isEmpty()) {
            videoRepo.save(video);
        }

        if (userRepo.findById(id).isEmpty()){
            user = new User();
            user.setWatchedVideos(Set.of(video));
            user.setId(id);
            userRepo.save(user);

        }
        else {
            user = userRepo.findById(id).get();
            Set<Video> watchedVideos = user.getWatchedVideos();
            watchedVideos.add(video);
            user.setWatchedVideos(watchedVideos);
            userRepo.update(user);
        }
    }

    @Topic("new-hashtag")
    void hashtagConsumer(@KafkaKey long id, Hashtag hashtag) {
        if (hashtagRepo.findById(hashtag.getId()).isEmpty()) {
            hashtagRepo.save(hashtag);
        }
    }
}
