package uk.ac.york.eng2.videos.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

import uk.ac.york.eng2.videos.domain.Hashtag;
import uk.ac.york.eng2.videos.dto.HashtagDTO;
import uk.ac.york.eng2.videos.events.HashtagProducer;
import uk.ac.york.eng2.videos.repositories.HashtagsRepositoryExtended;

import java.net.URI;

@Controller("/hashtags")
public class HashtagsController {

    @Inject
    HashtagsRepositoryExtended repo;

    @Inject
    HashtagProducer kafkaClient;

    @Get("/")
    public Iterable<Hashtag> listHashtags() {
        return repo.findAll();
    }

    @Get("/{id}")
    public Hashtag getHashtag(long id) {
        return repo.findById(id).orElse(null);
    }

    @Post("/")
    public HttpResponse<Void> add(@Body HashtagDTO hashtagDetails) {
        if (hashtagDetails.getName() == null) {
            return HttpResponse.badRequest();
        }

        if (!repo.findByName(hashtagDetails.getName()).isEmpty()) {
            return HttpResponse.badRequest();
        }
        Hashtag newHashtag = new Hashtag();
        newHashtag.setName(hashtagDetails.getName());

        repo.save(newHashtag);

        kafkaClient.newHashtag(newHashtag.getId(), newHashtag);
        return HttpResponse.created(URI.create("/hashtags/" + newHashtag.getId()));
    }
}