package uk.ac.york.eng2.videos.repositories;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import uk.ac.york.eng2.videos.domain.Hashtag;
import uk.ac.york.eng2.videos.domain.User;

import java.util.Optional;

@Repository
public interface HashtagsRepository extends CrudRepository<Hashtag, Long> {

    Optional<Hashtag> findByName(String name);
}