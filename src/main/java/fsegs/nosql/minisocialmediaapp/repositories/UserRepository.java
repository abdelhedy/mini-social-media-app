package fsegs.nosql.minisocialmediaapp.repositories;

import fsegs.nosql.minisocialmediaapp.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,Long> {

}
