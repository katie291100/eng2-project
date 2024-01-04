package uk.ac.york.eng2.trendingHashtags.repositories;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import uk.ac.york.eng2.trendingHashtags.domain.Hashtag;

import java.util.List;
import java.util.Optional;

@Repository
public interface HashtagsRepository extends CrudRepository<Hashtag, Long> {


}