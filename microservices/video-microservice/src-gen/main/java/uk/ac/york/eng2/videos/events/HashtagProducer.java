package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.videos.domain.*;

@KafkaClient
public interface HashtagProducer {

    @Topic("new-hashtag")
    void newHashtag(@KafkaKey Long hashtagId, Hashtag hashtag);
}

