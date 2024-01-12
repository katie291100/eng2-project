package uk.ac.york.eng2.subscription.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Serdeable
@Serdeable.Serializable
public class SubscriptionValue {

    private List<Long> videoIds = new java.util.ArrayList<>();

    public SubscriptionValue() {
        // empty constructor for reflection
    }

    @JsonCreator
    public SubscriptionValue(List<Long> videoIds) {
        this.videoIds = videoIds;
    }
    public void addVideoId(Long  videoIdNew) {
        this.videoIds.add(videoIdNew);
    }
    public List<Long> getVideoIds() {
        return videoIds;
    }
    public void setVideoIds(List<Long> videoIds) {
        this.videoIds = videoIds;
    }

    @Override
    public String toString() {
        return "SubscriptionValue[" + videoIds.toString() +"]";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SubscriptionValue) {
            SubscriptionValue other = (SubscriptionValue) o;
            return this.videoIds.equals(other.videoIds);
        }
        return false;
    }
}