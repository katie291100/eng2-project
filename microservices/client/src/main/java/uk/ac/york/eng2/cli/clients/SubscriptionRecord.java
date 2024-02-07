package uk.ac.york.eng2.cli.clients;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Serdeable
@Serdeable.Serializable
public class SubscriptionRecord {

    private Long userId;
    private Long hashtagId;

    @NonNull
    private List<Long> videoIds = new java.util.ArrayList<>();

    public SubscriptionRecord() {
        // empty constructor for reflection
    }

    @JsonCreator
    public SubscriptionRecord(Long userId, Long hashtagId, List<Long> videoIds ) {
        this.userId = userId;
        this.hashtagId = hashtagId;
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
        return "SubscriptionRecord{" +
                "userId=" + userId +
                ", hashtagId=" + hashtagId +
                ", videoIds=" + videoIds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SubscriptionRecord) {
            SubscriptionRecord other = (SubscriptionRecord) o;

            return this.videoIds.equals(other.videoIds) && this.userId.equals(other.userId) && this.hashtagId.equals(other.hashtagId);
        }
        return false;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getHashtagId() {
        return hashtagId;
    }

    public void setHashtagId(Long hashtagId) {
        this.hashtagId = hashtagId;
    }
}