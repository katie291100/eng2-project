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
	HttpResponse<Void> addUser(@Body UserDTO userDTO);
		
		
	 /**
     * This is a PUT request at "/{userId}/watchedVideo/{videoId}"
     */
	@Put("/{userId}/watchedVideo/{videoId}")
	HttpResponse<Void> watchVideo( Long userId, Long videoId);
		
		
	 /**
     * This is a GET request at "/{userId}"
     */
	@Get("/{userId}")
	User getUser( Long userId);
		
		
	 /**
     * This is a GET request at "/{userId}/watchedVideos"
     */
	@Get("/{userId}/watchedVideos")
	Set<Video> getWatchedByUser( Long userId);
		
		
	 /**
     * This is a GET request at "/{userId}/postedVideos"
     */
	@Get("/{userId}/postedVideos")
	Set<Video> getPostedByUser( Long userId);
		
		
	
}
