package uk.ac.york.eng2.clients;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.eng2.domain.Hashtag;
import uk.ac.york.eng2.dto.HashtagDTO;


@Client("${hashtags.url:`http://localhost:8080/hashtags`}")
public interface HashtagsClient {
    @Get("/")
    Iterable<Hashtag> list();

    @Get("/{id}")
    Hashtag getHashtag(Long id);

    @Post("/")
    HttpResponse<Void> add(@Body HashtagDTO hashtagDTO);

}