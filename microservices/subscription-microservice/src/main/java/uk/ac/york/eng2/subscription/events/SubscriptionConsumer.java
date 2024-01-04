package uk.ac.york.eng2.subscription.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;
import uk.ac.york.eng2.subscription.domain.Hashtag;
import uk.ac.york.eng2.subscription.domain.User;
import uk.ac.york.eng2.subscription.repositories.HashtagsRepositoryExtended;

@KafkaListener(groupId = "SubscriptionConsumer")

public class SubscriptionConsumer {

    @Inject
    HashtagsRepositoryExtended hashtagRepo;

    @Topic("watch-video")
    void watchConsumer(@KafkaKey long id, User user) {
        System.out.println("video-watched by user: " + user.getId());
        System.out.println(user.getWatchedVideos());

    }

    @Topic("new-hashtag")
    void hashtagConsumer(@KafkaKey long id, Hashtag hashtag) {
        if (hashtagRepo.findById(hashtag.getId()).isEmpty()) {
            hashtagRepo.save(hashtag);
        }
    }
}
