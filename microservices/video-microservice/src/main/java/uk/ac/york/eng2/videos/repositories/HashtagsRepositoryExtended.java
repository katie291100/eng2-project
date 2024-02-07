package uk.ac.york.eng2.videos.repositories;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Repository;
import uk.ac.york.eng2.videos.domain.Hashtag;

import java.util.List;
import java.util.Optional;


@Repository
public interface HashtagsRepositoryExtended extends HashtagsRepository{
    @Join(value = "videos",  type = Join.Type.LEFT_FETCH)
    List<Hashtag> findByName(String name);

    @Join(value = "videos",  type = Join.Type.LEFT_FETCH)
    @NonNull
    Optional<Hashtag> findById(Long id);

}