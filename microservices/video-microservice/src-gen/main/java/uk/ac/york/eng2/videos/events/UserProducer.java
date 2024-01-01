package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.videos.domain.*;

@KafkaClient
public interface UserProducer {

    @Topic("new-user")
    void newUser(@KafkaKey Long key, Hashtag h);
    @Topic("watch-video")
    void watchVideo(@KafkaKey Long key, Hashtag h);
}

