package uk.ac.york.eng2.videos.dto;

import io.micronaut.serde.annotation.Serdeable;
import org.apache.kafka.common.protocol.types.Field;
import uk.ac.york.eng2.videos.domain.Video;

import java.util.Set;

@Serdeable
public record HashtagDTO(String name) {

    public String getName() {
        return name;
    }
}