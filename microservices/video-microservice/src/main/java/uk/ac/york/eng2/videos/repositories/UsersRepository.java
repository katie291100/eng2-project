package uk.ac.york.eng2.videos.repositories;

import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import jakarta.annotation.Nonnull;
import uk.ac.york.eng2.videos.domain.User;
import uk.ac.york.eng2.videos.domain.Video;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
    @Join(value = "watchedVideos",  type = Join.Type.LEFT_FETCH)
    @Join(value = "watchedVideos.hashtags",  type = Join.Type.LEFT_FETCH)
    @Override
    Optional<User> findById(@Nonnull Long id);

}