package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.videos.domain.Hashtag;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;

@KafkaClient
public interface HashtagProducer {

    String TOPIC_ADD_VIDEO = "new-hashtag";

    @Topic(TOPIC_ADD_VIDEO)
    void newHashtag(@KafkaKey Long key, Hashtag h);

}

