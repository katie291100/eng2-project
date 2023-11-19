package uk.ac.york.eng2.videos.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

import uk.ac.york.eng2.videos.domain.Hashtag;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.dto.HashtagDTO;
import uk.ac.york.eng2.videos.dto.UserDTO;
import uk.ac.york.eng2.videos.events.HashtagProducer;
import uk.ac.york.eng2.videos.repositories.HashtagsRepository;
import uk.ac.york.eng2.videos.repositories.UsersRepository;

import java.net.URI;

@Controller("/hashtags")
public class HashtagsController {

    @Inject
    HashtagsRepository repo;

    @Inject
    HashtagProducer kafkaClient;

    @Get("/")
    public Iterable<Hashtag> list() {
        return repo.findAll();
    }

    @Get("/{id}")
    public Hashtag getHashtag(long id) {
        return repo.findById(id).orElse(null);
    }

    @Post("/")
    public HttpResponse<Void> add(@Body HashtagDTO hashtagDetails) {
        Hashtag newHashtag = new Hashtag();
        newHashtag.setName(hashtagDetails.getName());

        repo.save(newHashtag);

        kafkaClient.newHashtag(newHashtag.getId(), newHashtag);
        return HttpResponse.created(URI.create("/hashtags/" + newHashtag.getId()));
    }
}