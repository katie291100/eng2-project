package uk.ac.york.eng2.subscription.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;

import java.util.Set;

@Serdeable
@Entity
public class Hashtag {
    
	@Id
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Long id ;
    @Column(unique=false, nullable=false, insertable=true, updatable=true, columnDefinition="")    
    private String name ;
    @JsonIgnore
	@ManyToMany(mappedBy="hashtags", fetch = FetchType.LAZY)
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Set<Video> videos ;
	@JsonIgnore
	@ManyToMany(mappedBy="subscriptions", fetch = FetchType.LAZY)
    @Column(unique=false, nullable=true, insertable=true, updatable=true, columnDefinition="")    
    private Set<User> subscribedToBy ;

    
    
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
    
    
    public Set<User> getSubscribedToBy() {
        return subscribedToBy;
    }

    public void setSubscribedToBy(Set<User> subscribedToBy) {
        this.subscribedToBy = subscribedToBy;
    }

    @Override
    public String toString() {
        return "Hashtag{" +
                "id=" + id + ","+
                "name=" + name + ","+
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
