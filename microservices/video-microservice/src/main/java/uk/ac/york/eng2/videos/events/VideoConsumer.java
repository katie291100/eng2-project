package uk.ac.york.eng2.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.videos.domain.Video;

@KafkaListener(groupId = "video-debug")
public class VideoConsumer {

    @Topic("new-video")
    void notifyNewVdeo(@KafkaKey long id, Video video) {
        System.out.printf("video posted: %s %s", id, video.getTitle());
    }
}
