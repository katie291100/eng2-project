package uk.ac.york.eng2.trendingHashtags.repositories;

import uk.ac.york.eng2.trendingHashtags.domain.Hashtag;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;


import java.util.List;

@Repository
public interface HashtagsRepository extends CrudRepository<Hashtag, Long> {

    List<Hashtag> findByName(String name);

}