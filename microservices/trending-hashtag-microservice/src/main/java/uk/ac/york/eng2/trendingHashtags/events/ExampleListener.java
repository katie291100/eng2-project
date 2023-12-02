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
@KafkaListener(groupId = "ExampleListener")
public class ExampleListener {

    @Inject
    HashtagsRepository hashtagsRepository;

    @Topic("liked-hashtag")
    void example(@KafkaKey long id, Hashtag hashtag) {
        if (hashtagsRepository.findById(hashtag.getId()).orElse(null) == null){
            hashtagsRepository.save(hashtag);
        }
        System.out.println("liked-hashtag");
    }

}
