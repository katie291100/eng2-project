package uk.ac.york.eng2.subscription.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@Serdeable.Serializable
public class SubscriptionIdentifier {

    private Long userId, hashtagId;

    public SubscriptionIdentifier() {
        // empty constructor for reflection
    }

    @JsonCreator
    public SubscriptionIdentifier(Long userId, Long hashtagId) {
        this.userId = userId;
        this.hashtagId = hashtagId;
    }

    public Long getId() {
        return userId;
    }

    public void setId(Long userId) {
        this.userId = userId;
    }

    public void setHashtagId(Long hashtagId) {
        this.hashtagId = hashtagId;
    }
    public Long getHashtagId() {
        return hashtagId;
    }

    @Override
    public String toString() {
        return "SubscriptionIdentifier [userId=" + userId + ", hashtagId=" + hashtagId + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SubscriptionIdentifier) {
            SubscriptionIdentifier other = (SubscriptionIdentifier) o;
            return this.userId.equals(other.userId) && this.hashtagId.equals(other.hashtagId);
        }
        return false;
    }
}