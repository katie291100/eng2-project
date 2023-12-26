package uk.ac.york.eng2.trendingHashtags.events;

import uk.ac.york.eng2.trendingHashtags.domain.Hashtag;
import uk.ac.york.eng2.trendingHashtags.repositories.HashtagsRepository;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;

/**
 * kafka-streams requires at least one listener
 */
@KafkaListener(groupId = "HashtagConsumer")
public class HashtagConsumer {

    @Inject
    HashtagsRepository hashtagsRepository;

    @Topic("new-hashtag")
    void newHashtag(@KafkaKey long id, Hashtag hashtag) {
        if (hashtagsRepository.findById(hashtag.getId()).orElse(null) == null){
            hashtagsRepository.save(hashtag);
        }
        System.out.println("new-hashtag");
    }

}
