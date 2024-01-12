package uk.ac.york.eng2.subscription.controllers;

import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import org.apache.kafka.streams.KeyValue;
import uk.ac.york.eng2.subscription.domain.*;
import uk.ac.york.eng2.subscription.dto.*;
import uk.ac.york.eng2.subscription.events.SubscriptionIdentifier;
import uk.ac.york.eng2.subscription.events.SubscriptionValue;

import java.util.*;

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
