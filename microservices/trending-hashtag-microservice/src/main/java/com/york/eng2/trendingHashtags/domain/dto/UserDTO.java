package com.york.eng2.trendingHashtags.domain.dto;

import io.micronaut.serde.annotation.Serdeable;

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