package uk.ac.york.eng2.videos.repositories;

import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import jakarta.annotation.Nonnull;
import uk.ac.york.eng2.videos.domain.Video;

import java.util.Optional;

@Repository
public interface VideosRepository extends CrudRepository<Video, Long> {
    @Join(value = "postedBy", type = Join.Type.LEFT_FETCH)
    @Override
    Optional<Video> findById(@Nonnull Long id);

}