package uk.ac.york.eng2.videos.controllers;

import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import uk.ac.york.eng2.videos.domain.*;
import uk.ac.york.eng2.videos.dto.*;
import java.util.*;

abstract class UserControllersAbstract {
	
	@Get("/")
	abstract public Set<User> listUsers();
		
		
	@Post("/")
	abstract public userDTO addUser(@Body HttpResponse<Void> HTTPResponse);
		
		
	@Put("/watchVideo/{videoID}")
	abstract public Long watchVideo(@Body HttpResponse<Void> HTTPResponse);
		
		
	@Get("/{userId}")
	abstract public Long getUser(@Body User user);
		
		
	@Get("/{userId}/watchedVideos")
	abstract public Long getWatchedByUser(@Body Set(Videos) videos);
		
		
	@Get("/{userId}/postedVideos")
	abstract public Long getPostedByUser(@Body Set(Videos) videos);
		
		
	
}
