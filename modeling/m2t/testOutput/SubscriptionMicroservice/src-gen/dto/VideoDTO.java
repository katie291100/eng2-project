package uk.ac.york.eng2.videos.dto;

import io.micronaut.http.annotation.Body;
import io.micronaut.serde.annotation.Serdeable;

import java.util.Set;

@Serdeable
public class VideoDTO {

		private String String
		private Long Long

		public void setTitle(String title) {
			this.title = title
		}
		
		public Long gettitle() {
        	return title;
    	}
		public void setPostedBy(Long postedBy) {
			this.postedBy = postedBy
		}
		
		public Long getpostedBy() {
        	return postedBy;
    	}

}