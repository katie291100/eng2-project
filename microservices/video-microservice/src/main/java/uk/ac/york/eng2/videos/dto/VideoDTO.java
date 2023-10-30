package uk.ac.york.eng2.videos.dto;

import io.micronaut.http.annotation.Body;
import io.micronaut.serde.annotation.Serdeable;
import org.graalvm.nativebridge.In;
import uk.ac.york.eng2.videos.domain.Hashtag;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.repositories.HashtagsRepository;

import java.util.Set;

@Serdeable
public class VideoDTO {

    private String title;

    private Long postedBy;

    private Set<HashtagDTO> hashtags;

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

    public Set<HashtagDTO> getHashtags() {
        return hashtags;
    }

    public void setHashtags(@Body Set<HashtagDTO> hashtags) {
        this.hashtags = hashtags;
    }


}