package uk.ac.york.eng2.videos.dto;

import io.micronaut.serde.annotation.Serdeable;
import uk.ac.york.eng2.videos.domain.Video;

import java.util.Set;

@Serdeable
public class UserDTO {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}