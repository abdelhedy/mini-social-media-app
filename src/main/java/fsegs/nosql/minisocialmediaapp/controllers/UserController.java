package fsegs.nosql.minisocialmediaapp.controllers;

import fsegs.nosql.minisocialmediaapp.dtos.responseDtos.PostResponseDto;
import fsegs.nosql.minisocialmediaapp.models.User;
import fsegs.nosql.minisocialmediaapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllPosts(){
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<User> getPostById(@PathVariable String userId){
        return new ResponseEntity<>(service.getUserById(userId), HttpStatus.OK);
    }


}
