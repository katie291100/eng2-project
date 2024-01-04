package uk.ac.york.eng2.videos.dto;

import io.micronaut.http.annotation.Body;
import io.micronaut.serde.annotation.Serdeable;
import uk.ac.york.eng2.videos.domain.*;
import java.util.Set;

@Serdeable
public class HashtagDTO {

		private String name;

		public void setName(String name) {
			this.name = name;
		}
		
		public String getName() {
        	return name;
    	}

}

