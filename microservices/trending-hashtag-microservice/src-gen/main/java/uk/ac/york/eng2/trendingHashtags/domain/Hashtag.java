package uk.ac.york.eng2.trendingHashtags.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import java.util.*;

@Serdeable
@Entity
public class Hashtag {
    
	@Id
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Long id;
    @Column(unique=false, nullable=false, insertable=true, updatable=true, columnDefinition="")    
    private String name;
	@ManyToMany(mappedBy="hashtags", fetch = FetchType.LAZY)
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
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

    public void setVideos(Set<Video> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "Hashtag{" +
    
                "id=" + id + ","+
    
                "name=" + name + ","+
    
                "videos=" + videos + ","+
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Hashtag)) {
            return false;
        }
        Hashtag a = (Hashtag) o;
        return a.getId().equals(this.getId());
    }
}
