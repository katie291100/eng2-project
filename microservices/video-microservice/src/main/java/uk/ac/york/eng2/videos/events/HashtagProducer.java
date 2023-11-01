package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.videos.domain.Hashtag;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;

@KafkaClient
public interface HashtagProducer {

    String TOPIC_NEW_HASH = "new-hashtag";
    String TOPIC_LIKED_HASH = "liked-hashtag";

    @Topic(TOPIC_NEW_HASH)
    void newHashtag(@KafkaKey Long key, Hashtag h);

    @Topic(TOPIC_LIKED_HASH)
    void likeHashtag(@KafkaKey Long key, Hashtag h);

}

