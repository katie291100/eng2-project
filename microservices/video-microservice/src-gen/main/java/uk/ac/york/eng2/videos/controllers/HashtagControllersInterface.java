package uk.ac.york.eng2.videos.controllers;

import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import uk.ac.york.eng2.videos.domain.*;
import uk.ac.york.eng2.videos.dto.*;
import java.util.*;

interface HashtagControllersInterface {
	
	 /**
     * This is a GET request at "/"
     */
	@Get("/")
	Set<Hashtag> listHashtags();
		
		
	 /**
     * This is a GET request at "/{hashtagId}"
     */
	@Get("/{hashtagId}")
	Hashtag getHashtag( Long hashtagId);
		
		
	 /**
     * This is a POST request at "/"
     */
	@Post("/")
	HttpResponse<Void> addHashtag(@Body HashtagDTO hashtagDTO);
		
		
	
}
