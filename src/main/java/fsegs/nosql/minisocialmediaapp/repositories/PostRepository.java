package fsegs.nosql.minisocialmediaapp.repositories;

import fsegs.nosql.minisocialmediaapp.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post,String> {
    // Get all posts by a specific user
    List<Post> findByAuthorId(String authorId);

}
