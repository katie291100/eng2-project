package uk.ac.york.eng2.clients;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.eng2.domain.User;
import uk.ac.york.eng2.domain.Video;
import uk.ac.york.eng2.dto.UserDTO;

import java.util.Set;

@Client("${users.url:`http://localhost:8080/users`}")
public interface UsersClient {
    @Get("/")
    Iterable<User> list();

    @Get("/{id}")
    User getUser(long id);

    @Get("/{id}/watchedVideos")
    Set<Video> getWatchedByUser(long id);

    @Get("/{id}/postedVideos")
    Set<Video> getPostedByUser(long id);

    @Post("/")
    HttpResponse<Void> add(@Body UserDTO userDetails);

    @Put("/{id}")
    HttpResponse<Void> updateUser(long id, @Body UserDTO userDetails);

    @Put("/{id}/watchedVideo/{videoId}")
    HttpResponse<Void> watchedVideo(long id, long videoId);

    @Delete("/{id}")
    HttpResponse<Void> deleteUser(long id);


}
