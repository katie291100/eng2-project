package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.videos.domain.*;

@KafkaClient
public interface SubscriptionProducer {

    @Topic("subscribe-hashtag")
    void subscribeHashtag(@KafkaKey Long key, Hashtag h);
    @Topic("unsubscribe-hashtag")
    void unsubscribeHashtag(@KafkaKey Long key, Hashtag h);
}

