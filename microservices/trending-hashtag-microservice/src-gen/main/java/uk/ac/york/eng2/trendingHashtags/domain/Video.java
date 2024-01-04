package uk.ac.york.eng2.trendingHashtags.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import java.util.*;

@Serdeable
@Entity
public class Video {
    
	@Id
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Long id;
    @Column(unique=false, nullable=false, insertable=true, updatable=true, columnDefinition="")    
    private String title;
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Long likes;
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Long dislikes;
	@ManyToMany(fetch = FetchType.EAGER)
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Set<Hashtag> hashtags;

    
    
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
