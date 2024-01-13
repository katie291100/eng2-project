package uk.ac.york.eng2.subscription.dto;

import io.micronaut.serde.annotation.Serdeable;

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

