package uk.ac.york.eng2.videos.dto;

import io.micronaut.http.annotation.Body;
import io.micronaut.serde.annotation.Serdeable;

import java.util.Set;

@Serdeable
public class VideoDTO {

		private String String
		private String String

		public void setTitle(String title) {
			this.title = title
		}
		
		public Long gettitle() {
        	return title;
    	}
		public void setPostedBy(String postedBy) {
			this.postedBy = postedBy
		}
		
		public Long getpostedBy() {
        	return postedBy;
    	}

}