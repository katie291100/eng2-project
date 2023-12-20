package uk.ac.york.eng2.domain;

import io.micronaut.serde.annotation.Serdeable;

import java.util.HashSet;
import java.util.Set;

@Serdeable
public class Video {

    private Long id;

    private String title;

    private Long likes;

    private Long dislikes;

    private Set<Hashtag> hashtags = new HashSet<>();

    private User postedBy;

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

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Video)) {
            return false;
        }
        Video video = (Video) o;
        return video.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
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

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getDislikes() {
        return dislikes;
    }

    public void setDislikes(Long dislikes) {
        this.dislikes = dislikes;
    }

}