package uk.ac.york.eng2.videos.clients;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import jakarta.transaction.Transactional;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.UserDTO;

import java.util.Set;

@Client("/users")
public interface UsersClient {
    @Get("/")
    Iterable<User> listUser();

    @Get("/{id}")
    @Transactional
    User getUser(long id);

    @Get("/{id}/watchedVideos")
    @Transactional
    Set<Video> getWatchedByUser(long id);

    @Post("/")
    HttpResponse<Void> addUser(@Body UserDTO userDetails);

//    @Put("/{id}")
//    @Transactional
//    HttpResponse<Void> updateUser(long id, @Body UserDTO userDetails);

    @Put("/{id}/watchedVideo/{videoId}")
    @Transactional
    HttpResponse<Void> watchedVideo(long id, long videoId);
//
//    @Delete("/{id}")
//    @Transactional
//    HttpResponse<Void> deleteUser(long id);
}
