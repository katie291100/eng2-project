package uk.ac.york.eng2.videos.dto;

import io.micronaut.http.annotation.Body;
import io.micronaut.serde.annotation.Serdeable;
import uk.ac.york.eng2.videos.domain.*;
import java.util.Set;

@Serdeable
public class VideoDTO {

		private String title;
		private Long postedBy;
		private Set<HashtagDTO> hashtags;

		public void setTitle(String title) {
			this.title = title;
		}
		
		public String getTitle() {
        	return title;
    	}
		public void setPostedBy(Long postedBy) {
			this.postedBy = postedBy;
		}
		
		public Long getPostedBy() {
        	return postedBy;
    	}
		public void setHashtags(Set<HashtagDTO> hashtags) {
			this.hashtags = hashtags;
		}
		
		public Set<HashtagDTO> getHashtags() {
        	return hashtags;
    	}

}

