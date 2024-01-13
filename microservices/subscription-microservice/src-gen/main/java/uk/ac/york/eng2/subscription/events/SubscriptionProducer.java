package uk.ac.york.eng2.subscription.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.subscription.domain.Hashtag;

@KafkaClient
public interface SubscriptionProducer {

    @Topic("subscribe-hashtag")
    void subscribeHashtag(@KafkaKey Long userId, Hashtag hashtag);
    @Topic("unsubscribe-hashtag")
    void unsubscribeHashtag(@KafkaKey Long userId, Hashtag hashtag);
}

