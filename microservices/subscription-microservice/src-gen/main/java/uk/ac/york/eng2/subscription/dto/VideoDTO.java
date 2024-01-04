package uk.ac.york.eng2.subscription.dto;

import io.micronaut.http.annotation.Body;
import io.micronaut.serde.annotation.Serdeable;
import uk.ac.york.eng2.subscription.domain.*;
import java.util.Set;

@Serdeable
public class VideoDTO {

		private String title;
		private Long postedBy;

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

}

