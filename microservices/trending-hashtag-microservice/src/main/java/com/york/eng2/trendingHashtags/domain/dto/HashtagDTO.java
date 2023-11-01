package com.york.eng2.trendingHashtags.domain.dto;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record HashtagDTO(String name) {

    public String getName() {
        return name;
    }
}