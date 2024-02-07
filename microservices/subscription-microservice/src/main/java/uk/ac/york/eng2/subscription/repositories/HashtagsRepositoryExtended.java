package uk.ac.york.eng2.subscription.repositories;

import io.micronaut.data.annotation.Repository;
import uk.ac.york.eng2.subscription.domain.Hashtag;

@Repository
public interface HashtagsRepositoryExtended extends HashtagsRepository {

        Hashtag findByName(String name);

}