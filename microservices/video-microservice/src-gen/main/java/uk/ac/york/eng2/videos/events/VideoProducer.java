package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.videos.domain.*;

@KafkaClient
public interface VideoProducer {

    @Topic("new-video")
    void newVideo(@KafkaKey Long key, Hashtag h);
    @Topic("like-video")
    void likeVideo(@KafkaKey Long key, Hashtag h);
    @Topic("dislike-video")
    void dislikeVideo(@KafkaKey Long key, Hashtag h);
}

