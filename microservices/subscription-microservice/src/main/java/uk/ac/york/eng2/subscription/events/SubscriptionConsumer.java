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

@KafkaListener(groupId = "SubscriptionConsumer", offsetReset = OffsetReset.EARLIEST)
public class SubscriptionConsumer {

    @Inject
    HashtagsRepositoryExtended hashtagRepo;

    @Inject
    UserRepositoryExtended userRepo;


    @Topic("new-video")
    void watchConsumer(@KafkaKey Long id, Video video) {
        System.out.println("video " + video.getId() + " by user: " + id);

        for (Hashtag hashtag : video.getHashtags()) {
            if (hashtagRepo.findById(hashtag.getId()).isEmpty()) {
                hashtagRepo.save(hashtag);
            }
        }
    }

    @Topic("new-hashtag")
    void hashtagConsumer(@KafkaKey Long id, Hashtag hashtag) {
        System.out.println("hashtag " + hashtag.getId());
         if (hashtagRepo.findById(hashtag.getId()).isEmpty()) {
                hashtagRepo.save(hashtag);
         }
    }



    @Topic("new-user")
    void userConsumer(@KafkaKey Long id, User user) {
        System.out.println("user created " + user.getId());
        if (userRepo.findById(id).isEmpty()) {
            userRepo.save(user);
        }
    }
}
