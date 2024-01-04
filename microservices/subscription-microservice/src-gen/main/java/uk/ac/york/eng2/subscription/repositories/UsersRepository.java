package uk.ac.york.eng2.subscription.repositories;

import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import uk.ac.york.eng2.subscription.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {

    @Join(value = "watchedVideos", type = Join.Type.LEFT_FETCH)
    Optional<User> findById(Long id);


}