package uk.ac.york.eng2.subscription.repositories;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import uk.ac.york.eng2.subscription.domain.Video;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideosRepository extends CrudRepository<Video, Long> {


}