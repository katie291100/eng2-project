package uk.ac.york.eng2.subscription;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;

/**
 * kafka-streams requires at least one listener
 */
@KafkaListener(groupId = "ExampleListener")
public class ExampleListener {

    @Topic("example")
    void example() {
        System.out.println("example");
    }
}
