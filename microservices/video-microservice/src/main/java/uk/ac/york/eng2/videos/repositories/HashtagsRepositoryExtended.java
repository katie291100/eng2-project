package uk.ac.york.eng2.videos.repositories;

import io.micronaut.data.annotation.Repository;
import uk.ac.york.eng2.videos.domain.Hashtag;

import java.util.List;


@Repository
public interface HashtagsRepositoryExtended extends HashtagsRepository{

    List<Hashtag> findByName(String name);

}