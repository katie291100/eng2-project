package uk.ac.york.eng2.videos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import java.util.*;

@Serdeable
@Entity
public class User {
    
	@Id
	@GeneratedValue
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Long id ;
    @Column(unique=false, nullable=false, insertable=true, updatable=true, columnDefinition="")    
    private String name ;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Set<Video> watchedVideos ;
	@JsonIgnore
	@OneToMany(mappedBy="postedBy", fetch = FetchType.EAGER)
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Set<Video> postedVideos ;

    
    
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
                "id=" + id + ","+
                "name=" + name + ","+
        '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof User)) {
            return false;
        }
        User a = (User) o;
        return a.getId().equals(this.getId());
    }
}
