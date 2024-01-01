package uk.ac.york.eng2.videos.clients;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.VideoDTO;

import java.util.List;

@Client("/videos")
public interface VideosClient {
    @Get("/")
    public Iterable<Video> list();

    @Post("/")
    public HttpResponse<Void> addVideo(@Body VideoDTO bookDetails);

    @Get("/{id}")
    public Video getVideo(Long id);

//    @Put("/{id}")
//    HttpResponse<Void> updateVideo(Long id, @Body VideoDTO video);

    @Get("/hashtag/{hashtag}")
    List<Video> listVideosByHashtag(String hashtag);

    @Put("/{id}/like/{userId}")
    HttpResponse<Void> likeVideo(Long id, Long userId);

    @Put("/{id}/dislike/{userId}")
    HttpResponse<Void> dislikeVideo(Long id, Long userId);

//    @Delete("/{id}")
//    HttpResponse<Void> deleteVideo(Long id);
}