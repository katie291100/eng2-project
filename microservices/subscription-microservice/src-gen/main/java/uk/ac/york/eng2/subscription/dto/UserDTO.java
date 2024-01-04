package uk.ac.york.eng2.subscription.dto;

import io.micronaut.http.annotation.Body;
import io.micronaut.serde.annotation.Serdeable;
import uk.ac.york.eng2.subscription.domain.*;
import java.util.Set;

@Serdeable
public class UserDTO {

		private String name;

		public void setName(String name) {
			this.name = name;
		}
		
		public String getName() {
        	return name;
    	}

}

