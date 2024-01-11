package uk.ac.york.eng2.subscription.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import java.util.*;

@Serdeable
@Entity
public class User {
    
	@Id
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Long id ;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Set<Video> watchedVideos ;
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "subscribedToBy")
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Set<Hashtag> subscriptions ;

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public Set<Video> getWatchedVideos() {
        return watchedVideos;
    }

    public void setWatchedVideos(Set<Video> watchedVideos) {
        this.watchedVideos = watchedVideos;
    }
    
    
    public Set<Hashtag> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<Hashtag> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id + ","+
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
