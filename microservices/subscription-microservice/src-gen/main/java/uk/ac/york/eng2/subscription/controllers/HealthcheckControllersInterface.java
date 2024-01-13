package uk.ac.york.eng2.subscription.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;

interface HealthcheckControllersInterface {
	
	 /**
     * This is a GET request at "/"
     */
	@Get("/")
	HttpResponse<Void> health();
		
		
	
}
