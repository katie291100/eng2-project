package uk.ac.york.eng2.subscription.events;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class SubscriptionIdentifier {

    private Long userId, hashtagId;

    public SubscriptionIdentifier() {
        // empty constructor for reflection
    }

    public SubscriptionIdentifier(Long userId, Long hashtagId) {
        this.userId = userId;
        this.hashtagId = hashtagId;
    }

    public Long getId() {
        return userId;
    }
    public Long getHashtagId() {
        return userId;
    }

    @Override
    public String toString() {
        return "WindowedIdentifier [userId=" + userId + ", hashtagId=" + hashtagId + "]";
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