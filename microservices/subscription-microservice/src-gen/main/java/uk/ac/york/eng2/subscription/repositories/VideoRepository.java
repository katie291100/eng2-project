package uk.ac.york.eng2.subscription.repositories;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import uk.ac.york.eng2.subscription.domain.User;
import uk.ac.york.eng2.subscription.domain.Video;

@Repository
public interface VideoRepository extends CrudRepository<Video, Long> {


}