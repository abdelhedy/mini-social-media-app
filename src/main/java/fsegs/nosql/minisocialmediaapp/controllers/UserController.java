package fsegs.nosql.minisocialmediaapp.controllers;

import fsegs.nosql.minisocialmediaapp.dtos.createInputs.AuthenticationRequest;
import fsegs.nosql.minisocialmediaapp.dtos.createInputs.RegisterRequest;
import fsegs.nosql.minisocialmediaapp.dtos.responseDtos.UserDto;
import fsegs.nosql.minisocialmediaapp.models.User;
import fsegs.nosql.minisocialmediaapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }
//    @GetMapping("/{userId}")
//    public ResponseEntity<User> getUserById(@PathVariable String userId){
//        return new ResponseEntity<>(service.getUserById(userId), HttpStatus.OK);
//    }
    @PostMapping("/subscribe")
    public ResponseEntity<UserDto> addUser(@RequestBody RegisterRequest request) {
        UserDto user = service.createUser(request);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<UserDto> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id)  {
        service.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
