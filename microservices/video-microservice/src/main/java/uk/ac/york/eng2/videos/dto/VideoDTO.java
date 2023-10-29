package uk.ac.york.eng2.videos.dto;

import io.micronaut.serde.annotation.Serdeable;
import org.graalvm.nativebridge.In;
import uk.ac.york.eng2.videos.domain.User;
import java.util.Set;

@Serdeable
public class VideoDTO {

    private String title;

    private Long postedBy;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(Long postedBy) {
        this.postedBy = postedBy;
    }
}