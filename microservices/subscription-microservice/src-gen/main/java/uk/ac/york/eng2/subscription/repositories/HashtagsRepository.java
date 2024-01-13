package uk.ac.york.eng2.subscription.repositories;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import uk.ac.york.eng2.subscription.domain.Hashtag;

@Repository
public interface HashtagsRepository extends CrudRepository<Hashtag, Long> {


}