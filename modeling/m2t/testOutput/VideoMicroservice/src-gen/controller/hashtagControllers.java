package uk.ac.york.eng2.videos.controllers;

import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import uk.ac.york.eng2.videos.domain.*;
import uk.ac.york.eng2.videos.dto.*;
import java.util.*;

abstract class HashtagControllersAbstract {
	
	@Get("/")
	abstract public Set<Hashtag> listHashtags();
		
		
	@Get("/{hashtagID}")
	abstract public Hashtag getHashtag( Long hashtagID);
		
		
	@Post("/")
	abstract public HttpResponse<Void> addHashtag(@Body HashtagDTO hashtagDTO);
		
		
	
}
