package uk.ac.york.eng2.subscription.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.subscription.domain.Video;


@KafkaClient
public interface TestProducerUtil {

    String TOPIC_ADD_VIDEO = "new-video";
    String TOPIC_WATCH_VIDEO = "watch-video";

    @Topic(TOPIC_ADD_VIDEO)
    void postVideo(@KafkaKey Long key, Video b);

    @Topic(TOPIC_WATCH_VIDEO)
    void watchVideo(@KafkaKey Long key, Video b);
}