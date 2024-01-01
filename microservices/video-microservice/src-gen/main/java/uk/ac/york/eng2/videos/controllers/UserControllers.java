package uk.ac.york.eng2.videos.controllers;

import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import uk.ac.york.eng2.videos.domain.*;
import uk.ac.york.eng2.videos.dto.*;
import java.util.*;

interface UserControllersInterface {
	
	 /**
     * This is a GET request at "/"
     */
	@Get("/")
	Set<User> listUsers();
		
		
	 /**
     * This is a POST request at "/"
     */
	@Post("/")
	UserDTO addUser(@Body HttpResponse<Void> HTTPResponse);
		
		
	 /**
     * This is a PUT request at "/watchVideo/{videoID}"
     */
	@Put("/watchVideo/{videoID}")
	Long watchVideo(@Body HttpResponse<Void> HTTPResponse);
		
		
	 /**
     * This is a GET request at "/{userId}"
     */
	@Get("/{userId}")
	Long getUser(@Body User user);
		
		
	 /**
     * This is a GET request at "/{userId}/watchedVideos"
     */
	@Get("/{userId}/watchedVideos")
	Long getWatchedByUser(@Body Set<Video> videos);
		
		
	 /**
     * This is a GET request at "/{userId}/postedVideos"
     */
	@Get("/{userId}/postedVideos")
	Long getPostedByUser(@Body Set<Video> videos);
		
		
	
}
