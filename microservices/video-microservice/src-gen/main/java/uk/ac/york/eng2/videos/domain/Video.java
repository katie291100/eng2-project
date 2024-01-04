package uk.ac.york.eng2.videos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import java.util.*;

@Serdeable
@Entity
public class Video {
    
	@Id
	@GeneratedValue
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Long id;
    @Column(unique=false, nullable=false, insertable=true, updatable=true, columnDefinition="")    
    private String title;

	@ManyToOne(fetch = FetchType.EAGER, optional=false)
    private User postedBy;
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Long likes;
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Long dislikes;
	@ManyToMany(fetch = FetchType.EAGER)
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Set<Hashtag> hashtags;
	@JsonIgnore
	@ManyToMany(mappedBy="watchedVideos", fetch = FetchType.LAZY)
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Set<User> watchers;

    
    
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
    
    
    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
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
    
    
    public Set<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(Set<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }
    
    
    public Set<User> getWatchers() {
        return watchers;
    }

    public void setWatchers(Set<User> watchers) {
        this.watchers = watchers;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id + ","+
                "title=" + title + ","+
                "likes=" + likes + ","+
                "dislikes=" + dislikes + ","+
                "hashtags=" + hashtags + ","+
        '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Video)) {
            return false;
        }
        Video a = (Video) o;
        return a.getId().equals(this.getId());
    }
}
