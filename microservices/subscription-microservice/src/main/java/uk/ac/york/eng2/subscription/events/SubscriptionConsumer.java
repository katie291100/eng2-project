package uk.ac.york.eng2.subscription.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;
import uk.ac.york.eng2.subscription.domain.Hashtag;
import uk.ac.york.eng2.subscription.domain.User;
import uk.ac.york.eng2.subscription.domain.Video;
import uk.ac.york.eng2.subscription.repositories.HashtagsRepositoryExtended;
import uk.ac.york.eng2.subscription.repositories.UserRepositoryExtended;
import uk.ac.york.eng2.subscription.repositories.UsersRepository;
import uk.ac.york.eng2.subscription.repositories.VideosRepository;

import java.util.Set;

@KafkaListener(groupId = "SubscriptionConsumer", offsetReset = OffsetReset.EARLIEST)
public class SubscriptionConsumer {

    @Inject
    HashtagsRepositoryExtended hashtagRepo;

    @Inject
    UserRepositoryExtended userRepo;

    @Inject
    VideosRepository videoRepo;

    @Topic("new-video")
    void watchConsumer(@KafkaKey long id, Video video) {
        User user;
        System.out.println("video " + video.getId() + " by user: " + id);

        for (Hashtag hashtag : video.getHashtags()) {
            if (hashtagRepo.findById(hashtag.getId()).isEmpty()) {
                hashtagRepo.save(hashtag);
            }
        }

        if (userRepo.findById(video.getPostedBy().getId()).isEmpty()) {
            user = new User();
            user.setId(video.getPostedBy().getId());
            userRepo.save(user);
        } else {
            user = userRepo.findById(id).get();
        }

        if (videoRepo.findById(video.getId()).isEmpty()) {
            videoRepo.save(video);
        }
    }


    @Topic("new-user")
    void userConsumer(@KafkaKey long id, User user) {

        if (userRepo.findById(id).isEmpty()) {
            userRepo.save(user);
        }
    }
}
