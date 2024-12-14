package fsegs.nosql.minisocialmediaapp.controllers;

import fsegs.nosql.minisocialmediaapp.dtos.createInputs.PostCreateInput;
import fsegs.nosql.minisocialmediaapp.dtos.responseDtos.PostResponseDto;
import fsegs.nosql.minisocialmediaapp.dtos.updateInputs.PostUpdateInput;
import fsegs.nosql.minisocialmediaapp.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService ) {
        this.postService = postService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<PostResponseDto>> getAllPosts(){
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<List<PostResponseDto>> getPostsByAuthorId(@PathVariable String authorId){
        return new ResponseEntity<>(postService.getPostsByAuthor(authorId),HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable String postId){
        return new ResponseEntity<>(postService.getPostById(postId),HttpStatus.OK);
    }

    @PostMapping(value = "/create",consumes = {"multipart/form-data"})
    private ResponseEntity<PostResponseDto> addPost(@ModelAttribute PostCreateInput input){
        return new ResponseEntity<>(postService.createPost(input),HttpStatus.CREATED);
    }
    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable String postId,
            @RequestBody PostUpdateInput updateInput) { // Use the new DTO
        PostResponseDto updatedPost = postService.updatePost(postId, updateInput);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable String id)  {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
