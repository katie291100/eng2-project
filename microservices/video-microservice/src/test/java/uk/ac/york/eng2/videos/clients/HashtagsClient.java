package uk.ac.york.eng2.videos.clients;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.eng2.videos.domain.Hashtag;
import uk.ac.york.eng2.videos.domain.Video;
import uk.ac.york.eng2.videos.dto.HashtagDTO;
import uk.ac.york.eng2.videos.dto.VideoDTO;

import java.util.List;

@Client("/hashtags")
public interface HashtagsClient {
    @Get("/")
    public Iterable<Hashtag> list();

    @Get("/{id}")
    public Hashtag getHashtag(Long id);

    @Post("/")
    public HttpResponse<Void> add(@Body HashtagDTO hashtagDTO);

}