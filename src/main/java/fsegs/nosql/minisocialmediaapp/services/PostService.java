package fsegs.nosql.minisocialmediaapp.services;

import fsegs.nosql.minisocialmediaapp.dtos.createInputs.PostCreateInput;
import fsegs.nosql.minisocialmediaapp.dtos.responseDtos.PostResponseDto;
import fsegs.nosql.minisocialmediaapp.dtos.updateInputs.PostUpdateInput;
import fsegs.nosql.minisocialmediaapp.mappers.PostMapper;
import fsegs.nosql.minisocialmediaapp.models.Post;
import fsegs.nosql.minisocialmediaapp.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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
    public List<PostResponseDto> getPostsByAuthor(String authorId) {
        List<Post> postByAuthor= postRepository.findByAuthorId(authorId);
        return postMapper.toDtoList(postByAuthor);
    }
    public PostResponseDto getPostById(String id) {
        Post post= postRepository.findById(id).orElse(null);
        return postMapper.toDto(post);
    }
    public PostResponseDto createPost(PostCreateInput input) {
        Post savedPost=postRepository.save(postMapper.toEntity(input));
        return postMapper.toDto(savedPost);
    }

    public PostResponseDto updatePost(String postId, PostUpdateInput updateInput) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Get existing file paths
        List<String> existingImagePaths = post.getImagePaths();
        List<String> existingVideoPaths = post.getVideoPaths();

        // Update content
        post.setContent(updateInput.getContent());
        post.setUpdatedAt(LocalDateTime.now()); // Update the timestamp

        // Handle image paths
        if (updateInput.getImagePaths() != null) {
            // Delete old images that are not in the new list
            for (String existingPath : existingImagePaths) {
                if (!updateInput.getImagePaths().contains(existingPath)) {
                    deleteFile(existingPath); // Implement this method to delete the file
                }
            }
            // Update the image paths
            post.setImagePaths(updateInput.getImagePaths());
        }

        // Handle video paths
        if (updateInput.getVideoPaths() != null) {
            // Delete old videos that are not in the new list
            for (String existingPath : existingVideoPaths) {
                if (!updateInput.getVideoPaths().contains(existingPath)) {
                    deleteFile(existingPath); // Implement this method to delete the file
                }
            }
            // Update the video paths
            post.setVideoPaths(updateInput.getVideoPaths());
        }
        // Save the updated post
        Post savedPost = postRepository.save(post);
        return postMapper.toDto(savedPost); // Convert to DTO
    }
    // Add a like to a post
    public void addLike(String userId, String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Add the userId to the likes list if not already present
        if (!post.getLikes().contains(userId)) {
            post.getLikes().add(userId);
            postRepository.save(post); // Save the updated post
        }
    }
    // Remove a like from a post
    public void deleteLike(String userId, String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Remove the userId from the likes list if present
        if (post.getLikes().contains(userId)) {
            post.getLikes().remove(userId);
            postRepository.save(post); // Save the updated post
        }
    }

    // Delete a post
    public void deletePost(String id) {
        // Retrieve the post from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        // Delete associated files
        deleteFiles(post.getImagePaths());
        deleteFiles(post.getVideoPaths());

        // Delete the post from the database
        postRepository.delete(post);
        postRepository.deleteById(id);
    }
    private void deleteFiles(List<String> filePaths) {
        if (filePaths != null) {
            for (String filePath : filePaths) {
                try {
                    Path path = Paths.get(filePath);
                    Files.deleteIfExists(path); // Delete the file if it exists
                } catch (Exception e) {
                    e.printStackTrace(); // Log the exception for debugging
                }
            }
        }
    }
    private void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path); // Delete the file if it exists
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception for debugging
            // Handle the exception as needed (e.g., throw a custom exception)
        }
    }

}
