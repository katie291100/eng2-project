package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.videos.domain.*;

@KafkaClient
public interface VideoProducer {

    @Topic("new-video")
    void newVideo(@KafkaKey Long videoId, Video video);
    @Topic("like-video")
    void likeVideo(@KafkaKey Long userId, Video videoId);
    @Topic("dislike-video")
    void dislikeVideo(@KafkaKey Long userId, Video videoId);
}

