package com.york.eng2.trendingHashtags.domain.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Serdeable
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany
    private Set<Video> watchedVideos = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "postedBy")
    private Set<Video> postedVideos;


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

    public Set<Video> getWatchedVideos() {
        return watchedVideos;
    }

    public void addWatchedVideo(Video watchedVideo) {
        watchedVideos.add(watchedVideo);
    }

    public void setWatchedVideos(Set<Video> watchedVideos) {
        this.watchedVideos = watchedVideos;
    }

    public Set<Video> getPostedVideos() {
        return postedVideos;
    }

    public void setPostedVideos(Set<Video> postedVideos) {
        this.postedVideos = postedVideos;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}