package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.videos.domain.Hashtag;
import uk.ac.york.eng2.videos.domain.Video;

@KafkaClient
public interface VideoProducer {

    String TOPIC_ADD_VIDEO = "new-video";
    String TOPIC_LIKE_VIDEO = "like-video";
    String TOPIC_DISLIKE_VIDEO = "dislike-video";

    @Topic(TOPIC_ADD_VIDEO)
    void postVideo(@KafkaKey Long key, Video b);

    @Topic(TOPIC_LIKE_VIDEO)
    void likeVideo(@KafkaKey Long key, Video b);
    @Topic(TOPIC_DISLIKE_VIDEO)
    void dislikeVideo(@KafkaKey Long key, Video b);

}


