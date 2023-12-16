package uk.ac.york.eng2.trendingHashtags.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;

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

    @Column(nullable = false)
    private Long likes = 0L;

    @Column(nullable = false)
    private Long dislikes = 0L;

    @ManyToMany
    private Set<Hashtag> hashtags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    public Set<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(Set<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

}