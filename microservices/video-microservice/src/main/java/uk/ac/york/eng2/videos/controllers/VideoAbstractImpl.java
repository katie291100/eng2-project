package uk.ac.york.eng2.videos.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.VideoDTO;

import java.util.List;
import java.util.Set;

interface VideoAbstractImpl {
    /**
     * This is a GET request at "/"
     */
    @Get("/")
    List<Video> listVideos();


    /**
     * This is a GET request at "/{hashtag}"
     */
    @Get("/{hashtag}")
    Set<Video> listVideosByHashtag( String hashtag);


    /**
     * This is a POST request at "/"
     */
    @Post("/")
    HttpResponse<Void> addVideo(@Body VideoDTO videoDTO);


    /**
     * This is a GET request at "/{videoId}"
     */
    @Get("/{videoId}")
    Video getVideo( Long videoId);


    /**
     * This is a PUT request at "/{videoId}/likeVideo/{userId}"
     */
    @Put("/{videoId}/likeVideo/{userId}")
    HttpResponse<Void> likeVideo( Long videoId, Long userId);


    /**
     * This is a PUT request at "/{videoId}/dislikeVideo/{userId}"
     */
    @Put("/{videoId}/dislikeVideo/{userId}")
    HttpResponse<Void> dislikeVideo( Long videoId, Long userId);


}
