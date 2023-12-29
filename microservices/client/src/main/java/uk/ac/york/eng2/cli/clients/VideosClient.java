package uk.ac.york.eng2.cli.clients;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.eng2.cli.domain.Video;
import uk.ac.york.eng2.cli.dto.VideoDTO;

import java.util.Set;

@Client("${videos.url:`http://localhost:8080/videos`}")
public interface VideosClient {
    @Get("/")
    public Iterable<Video> list();

    @Post("/")
    public HttpResponse<Void> add(@Body VideoDTO bookDetails);

    @Get("/{id}")
    public Video getVideo(Long id);

//    @Put("/{id}")
//    HttpResponse<Void> updateVideo(Long id, @Body VideoDTO video);

    @Get("/hashtag/{hashtag}")
    Set<Video> listVideosByHashtag(String hashtag);

    @Put("/{id}/like/{userId}")
    HttpResponse<Void> likeVideo(Long id, Long userId);

    @Put("/{id}/dislike/{userId}")
    HttpResponse<Void> dislikeVideo(Long id, Long userId);

}