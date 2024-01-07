package uk.ac.york.eng2.subscription.repositories;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Repository;
import uk.ac.york.eng2.subscription.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepositoryExtended extends UsersRepository {

        @Join(value = "watchedVideos", type = Join.Type.LEFT_FETCH)
        @Join(value = "subscriptions", type = Join.Type.LEFT_FETCH)
        Optional<User> findById(Long id);
        @Join(value = "subscriptions", type = Join.Type.LEFT_FETCH)
        @NonNull
        List<User> findAll();

}