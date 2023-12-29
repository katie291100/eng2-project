package uk.ac.york.eng2.videos.controllers;

import io.micronaut.http.*;
import io.micronaut.http.annotation.*;
import uk.ac.york.eng2.videos.domain.*;
import uk.ac.york.eng2.videos.dto.*;
import java.util.*;
@Controller("/videos")
interface VideoAbstract {

    @Get("/")
    List<Video> listVideos();


    @Get("/{hashtag}")
    Set<Video> listVideosByHashtag( String hashtag);


    @Post("/")
    HttpResponse<Void> addVideo(@Body VideoDTO videoDTO);


    @Get("/{videoId}")
    Video getVideo( Long videoId);


    @Put("/{videoId}/likeVideo/{userId}")
    HttpResponse<Void> likeVideo( Long videoId, Long userId);


    @Put("/{videoId}/dislikeVideo/{userId}")
    HttpResponse<Void> dislikeVideo( Long videoId, Long userId);

}
