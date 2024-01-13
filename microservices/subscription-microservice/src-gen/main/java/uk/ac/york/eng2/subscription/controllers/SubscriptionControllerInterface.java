package uk.ac.york.eng2.subscription.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;

import java.util.List;

interface SubscriptionControllerInterface {
	
	 /**
     * This is a GET request at "/"
     */
	@Get("/")
	List<SubscriptionRecord> listAllSubscriptions();
		
		
	 /**
     * This is a PUT request at "/{hashtagId}/{userId}"
     */
	@Put("/{hashtagId}/{userId}")
	HttpResponse<Void> subscribe( Long hashtagId, Long userId);
		
		
	 /**
     * This is a DELETE request at "/{hashtagId}/{userId}"
     */
	@Delete("/{hashtagId}/{userId}")
	HttpResponse<Void> unsubscribe( Long hashtagId, Long userId);
		
		
	
}
