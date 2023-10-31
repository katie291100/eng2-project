package uk.ac.york.eng2.videos.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.net.URI;
import java.util.Set;

import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.UserDTO;
import uk.ac.york.eng2.videos.events.UserProducer;
import uk.ac.york.eng2.videos.repositories.UsersRepository;
import uk.ac.york.eng2.videos.repositories.VideosRepository;

@Controller("/users")
public class UsersController {
    @Inject
    private UsersRepository repo;

    @Inject
    private VideosRepository videoRepo;

    @Inject
    private UserProducer kafkaClient;

    @Get("/")
    public Iterable<User> list() {
        return repo.findAll();
    }

    @Get("/{id}")
    @Transactional
    public User getUser(long id) {
        return repo.findById(id).orElse(null);
    }

    @Get("/{id}/watchedVideos")
    @Transactional
    public Set<Video> getWatchedByUser(long id) {
        User user = repo.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        return user.getWatchedVideos();
    }

    @Post("/")
    @Transactional
    public HttpResponse<Void> add(@Body UserDTO userDetails) {
        User newUser = new User();
        newUser.setName(userDetails.getName());

        repo.save(newUser);
        kafkaClient.newUser(newUser.getId(), newUser);
        return HttpResponse.created(URI.create("/users/" + newUser.getId()));
    }

    @Put("/{id}")
    @Transactional
    public HttpResponse<Void> updateUser(long id, @Body UserDTO userDetails) {
        User oldUser = repo.findById(id).orElse(null);
        if (oldUser == null) {
            return HttpResponse.notFound();
        }
        oldUser.setName(userDetails.getName());
        repo.save(oldUser);

        return HttpResponse.ok();
    }
    @Put("/{id}/watchedVideo/{videoId}")
    @Transactional
    public HttpResponse<Void> watchedVideo(long id, long videoId) {
        User user = repo.findById(id).orElse(null);
        Video video = videoRepo.findById(videoId).orElse(null);
        if (user == null || video == null) {
            return HttpResponse.notFound();
        }
        user.addWatchedVideo(video);
        repo.save(user);
        return HttpResponse.ok();
    }
    @Delete("/{id}")
    @Transactional
    public HttpResponse<Void> deleteUser(long id) {
        User oldUser = repo.findById(id).orElse(null);
        if (oldUser == null) {
            return HttpResponse.notFound();
        }
        repo.delete(oldUser);

        return HttpResponse.ok();
    }
}