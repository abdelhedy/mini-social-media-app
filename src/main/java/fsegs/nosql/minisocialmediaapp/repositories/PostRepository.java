package fsegs.nosql.minisocialmediaapp.repositories;

import fsegs.nosql.minisocialmediaapp.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post,Long> {
}
