package com.york.eng2.trendingHashtags.dto;

import io.micronaut.http.annotation.Body;
import io.micronaut.serde.annotation.Serdeable;

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