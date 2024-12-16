package fsegs.nosql.minisocialmediaapp.repositories;

import fsegs.nosql.minisocialmediaapp.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    List<User> findByNomAndPrenom(String username);

    Optional<User> findByEmail(String email);
}
