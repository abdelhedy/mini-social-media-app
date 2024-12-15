package fsegs.nosql.minisocialmediaapp.services;

import fsegs.nosql.minisocialmediaapp.models.User;
import fsegs.nosql.minisocialmediaapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // Create a new user
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    // Get user by ID
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    // Update a user
//    public User updateUser(String id, User userDetails) {
//        User user = userRepository.findById(id).orElse(null);
//        if (user != null) {
//            user.setUsername(userDetails.getUsername());
//            user.setEmail(userDetails.getEmail());
//            user.setProfile(userDetails.getProfile());
//            user.setFollowers(userDetails.getFollowers());
//            user.setFollowing(userDetails.getFollowing());
//            user.setUpdatedAt(userDetails.getUpdatedAt());
//            return userRepository.save(user);
//        }
//        return null;
//    }

    // Delete a user
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

}
