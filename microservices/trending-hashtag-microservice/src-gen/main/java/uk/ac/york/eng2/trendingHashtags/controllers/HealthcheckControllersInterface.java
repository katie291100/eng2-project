package uk.ac.york.eng2.trendingHashtags.controllers;

import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import uk.ac.york.eng2.trendingHashtags.domain.*;
import java.util.*;

interface HealthcheckControllersInterface {
	
	 /**
     * This is a GET request at "/"
     */
	@Get("/")
	HttpResponse<Void> health();
		
		
	
}
