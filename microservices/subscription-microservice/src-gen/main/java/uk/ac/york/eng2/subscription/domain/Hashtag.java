package uk.ac.york.eng2.videos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.*;
import java.util.*;

@Serdeable
@Entity
public class Hashtag {
    
    
    @Id
    @GeneratedValue
    private Long id;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Hashtag{" +
    
                "id=" + id + ","+
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
