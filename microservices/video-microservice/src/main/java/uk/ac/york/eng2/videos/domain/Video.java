package uk.ac.york.eng2.videos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Serdeable
@Entity
public class Video {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;


    @ManyToMany
    private Set<Hashtag> hashtags;

    @ManyToOne
    private User postedBy;

    @JsonIgnore
    @ManyToMany(mappedBy = "watchedVideos")
    private Set<User> watchers = new HashSet<>();


    public Set<User> getWatchers() {
        return watchers;
    }

    public void setWatchers(Set<User> watchers) {
        this.watchers = watchers;
    }

    public void addWatcher(User watcher) {
        this.watchers.add(watcher);
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", postedBy=" + postedBy.getName() +
                '}';
    }


    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User poster) {
        this.postedBy = poster;
    }

    public Set<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(Set<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }
}