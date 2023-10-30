package uk.ac.york.eng2.videos;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import org.junit.jupiter.api.BeforeEach;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.VideoDTO;

import java.util.List;

@Client("/videos")
public interface VideosClient {
    @Get("/")
    public Iterable<Video> list();

    @Post("/")
    public HttpResponse<Void> add(@Body VideoDTO bookDetails);

    @Get("/{id}")
    public Video getVideo(Long id);

    @Put("/{id}")
    HttpResponse<Void> updateVideo(Long id, @Body VideoDTO video);

    @Get("/hashtag/{hashtag}")
    List<Video> listVideosByHashtag(String hashtag);
}