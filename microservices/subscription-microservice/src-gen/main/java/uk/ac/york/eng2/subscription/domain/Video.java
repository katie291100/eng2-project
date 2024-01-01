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
    private Long id;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private String title;
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Long postedBy;
    
    public Long getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(Long postedBy) {
        this.postedBy = postedBy;
    }
    
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Set<Users> watchers;
    
    public Set<Users> getWatchers() {
        return watchers;
    }

    public void setWatchers(Set<Users> watchers) {
        this.watchers = watchers;
    }

    @Override
    public String toString() {
        return "Video{" +
    
                "id=" + id + ","+
    
                "title=" + title + ","+
    
                "postedBy=" + postedBy + ","+
    
                "watchers=" + watchers + ","+
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
