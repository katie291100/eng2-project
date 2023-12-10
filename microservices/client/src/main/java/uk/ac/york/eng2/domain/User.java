package uk.ac.york.eng2.domain;

import io.micronaut.serde.annotation.Serdeable;

import java.util.HashSet;
import java.util.Set;

@Serdeable
public class User {

    private Long id;

    private String name;

    private Set<Video> watchedVideos = new HashSet<>();

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