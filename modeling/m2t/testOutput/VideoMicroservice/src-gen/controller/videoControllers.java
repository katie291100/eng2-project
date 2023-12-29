package uk.ac.york.eng2.videos.controllers;

import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import uk.ac.york.eng2.videos.domain.*;
import uk.ac.york.eng2.videos.dto.*;
import java.util.*;

abstract class VideoControllersAbstract {
	
	@Get("/")
	abstract public List<Video> listVideos();
		
		
	@Get("/{hashtag}")
	abstract public Set<Video> listVideosByHashtag( String hashtag);
		
		
	@Post("/")
	abstract public HttpResponse<Void> addVideo(@Body VideoDTO videoDTO);
		
		
	@Get("/{videoId}")
	abstract public Video getVideo( Long videoId);
		
		
	@Put("/{videoId}/likeVideo/{userId}")
	abstract public HttpResponse<Void> likeVideo( Long videoId, Long userId);
		
		
	@Put("/{videoId}/dislikeVideo/{userId}")
	abstract public HttpResponse<Void> dislikeVideo( Long videoId, Long userId);
		
		
	
}
