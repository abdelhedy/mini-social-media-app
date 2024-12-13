package fsegs.nosql.minisocialmediaapp.services;

import fsegs.nosql.minisocialmediaapp.dtos.createInputs.PostCreateInput;
import fsegs.nosql.minisocialmediaapp.dtos.responseDtos.PostResponseDto;
import fsegs.nosql.minisocialmediaapp.mappers.PostMapper;
import fsegs.nosql.minisocialmediaapp.models.Post;
import fsegs.nosql.minisocialmediaapp.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;
    public PostService(PostRepository postRepository, GridFsTemplate gridFsTemplate) {
        this.postRepository = postRepository;
    }
    public List<PostResponseDto> getAllPosts() {
        List<Post> posts=postRepository.findAll();
        return postMapper.toDtoList(posts);
    }
//    public List<Post> getAllPosts() {
//        List<Post> posts=postRepository.findAll();
//        return posts;
//    }
//    public List<PostResponseDto> getPostsByAuthor(String authorId) {
//        List<Post> postByAuthor= postRepository.findByAuthorId(authorId);
//        return postMapper.toDtoList(postByAuthor);
//    }
//    public Post getPostById(String id) {
//        Post post= postRepository.findById(id).orElse(null);
//        return post;
//    }
    public PostResponseDto getPostById(String id) {
        Post post= postRepository.findById(id).orElse(null);
        return postMapper.toDto(post);
    }
    public PostResponseDto createPost(PostCreateInput input) {
        Post savedPost=postRepository.save(postMapper.toEntity(input));
        return postMapper.toDto(savedPost);
    }
//    public Post createPost(PostCreateInput input) {
//        Post savedPost=postRepository.save(postMapper.toEntity(input));
//        return savedPost;
//    }


    // Update a post
//    public Post updatePost(String id, Post postDetails) {
//        Post post = postRepository.findById(id).orElse(null);
//        if (post != null) {
//            post.setContent(postDetails.getContent());
//            post.setMedia(postDetails.getMedia());
//            post.setLikes(postDetails.getLikes());
//            post.setComments(postDetails.getComments());
//            post.setUpdatedAt(postDetails.getUpdatedAt());
//            return postRepository.save(post);
//        }
//        return null;
//    }

    // Delete a post
    public void deletePost(String id) {
        postRepository.deleteById(id);
    }

}
