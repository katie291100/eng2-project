package com.york.eng2.trendingHashtags.domain.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;

import java.util.Set;

@Serdeable
@Entity
public class Hashtag {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "hashtags")
    private Set<Video> videos;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Video> getVideos() {
        return videos;
    }

    @Override
    public String toString() {
        return "Hashtag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Hashtag)) {
            return false;
        }
        Hashtag hashtag = (Hashtag) o;
        return hashtag.getId().equals(this.getId());
    }
}