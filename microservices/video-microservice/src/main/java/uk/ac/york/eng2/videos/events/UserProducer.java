package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;

@KafkaClient
public interface UserProducer {

    String TOPIC_ADD_VIDEO = "new-user";
    String TOPIC_WATCH_VIDEO = "watch-video";

    @Topic(TOPIC_ADD_VIDEO)
    void newUser(@KafkaKey Long key, User b);

    @Topic(TOPIC_WATCH_VIDEO)
    void watchVideo(@KafkaKey Long key, Video b);
}


