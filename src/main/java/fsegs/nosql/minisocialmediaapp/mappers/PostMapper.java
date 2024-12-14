package fsegs.nosql.minisocialmediaapp.mappers;

import fsegs.nosql.minisocialmediaapp.dtos.createInputs.PostCreateInput;
import fsegs.nosql.minisocialmediaapp.dtos.responseDtos.PostResponseDto;
import fsegs.nosql.minisocialmediaapp.models.Post;
import fsegs.nosql.minisocialmediaapp.models.User;
import fsegs.nosql.minisocialmediaapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PostMapper {
    @Value("${file.upload.dir}")
    private static final String UPLOAD_DIR = "E:/postify_files";
    private final UserRepository userRepository;

    public PostMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Post toEntity(PostCreateInput input) {
        Post post = new Post();
        post.setAuthorId(input.getAuthorId());
        post.setContent(input.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        // Save images and videos
        if (input.getImages() != null) {
            List<String> imagePaths = input.getImages().stream()
                    .map(this::saveFileToLocalDirectory)
                    .collect(Collectors.toList());
            post.setImagePaths(imagePaths);
        }

        if (input.getVideos() != null) {
            List<String> videoPaths = input.getVideos().stream()
                    .map(this::saveFileToLocalDirectory)
                    .collect(Collectors.toList());
            post.setVideoPaths(videoPaths);
        }

        return post;
    }
    public List<Post> toEntityList(List<PostCreateInput> inputs) {
        if (inputs == null) {
            return null;
        }
        return inputs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public PostResponseDto toDto(Post post) {
        if (post == null) {
            return null;
        }

        PostResponseDto dto = new PostResponseDto();
        dto.setId(post.getId());
//        dto.setAuthorId(post.getAuthorId());
        dto.setContent(post.getContent());
//        dto.setImagePaths(post.getImagePaths());
//        dto.setVideoPaths(post.getVideoPaths());
        String fileBaseUrl = "http://localhost:8080/files/";

        if (post.getImagePaths() != null) {
            dto.setImageURLs(
                    post.getImagePaths().stream()
                            .map(path -> fileBaseUrl + Paths.get(path).getFileName().toString()) // Extract only the file name
                            .collect(Collectors.toList())
            );
        }

        if (post.getVideoPaths() != null) {
            dto.setVideoURLs(
                    post.getVideoPaths().stream()
                            .map(path -> fileBaseUrl + Paths.get(path).getFileName().toString())
                            .collect(Collectors.toList())
            );
        }
        String authorId = post.getAuthorId().replace("\"", "");
        User user = userRepository.findById(authorId)
                .orElseThrow(() -> {
                    System.err.println("User not found with ID: " + post.getAuthorId());
                    return new RuntimeException("User not found with ID: " + post.getAuthorId());
                });

        // Debug prints
//        System.out.println("Found User: " + user);
//        System.out.println("User Nom: " + user.getNom());
//        System.out.println("User Prenom: " + user.getPrenom());
        dto.setAuthorNom(user.getNom());
        dto.setAuthorPrenom(user.getPrenom());
        dto.setLikes(post.getLikes());
        return dto;
    }

    public List<PostResponseDto> toDtoList(List<Post> posts) {
        if (posts == null) {
            return null;
        }
        return posts.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private String saveFileToLocalDirectory(MultipartFile file) {
        try {
            // Ensure upload directory exists
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate a unique file name to avoid conflicts
            String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(uniqueFileName);

            // Save the file to the directory
            file.transferTo(filePath.toFile());

            // Return the file path
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + file.getOriginalFilename(), e);
        }
    }
//    private String readFileAsBase64(String filePath) {
//        try {
//            Path path = Paths.get(UPLOAD_DIR).resolve(filePath).normalize();
//            byte[] fileBytes = Files.readAllBytes(path);
//            return Base64.getEncoder().encodeToString(fileBytes);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to read file: " + filePath, e);
//        }
//    }
//    private String processVideoFile(String filePath) {
//        Path path = Paths.get(UPLOAD_DIR).resolve(filePath).normalize();
//        try {
//            long fileSize = Files.size(path);
//            if (fileSize < 5 * 1024 * 1024) { // If the video is less than 5MB, return base64
//                byte[] fileBytes = Files.readAllBytes(path);
//                return Base64.getEncoder().encodeToString(fileBytes);
//            } else { // For large videos, return URL
//                return "/videos/" + filePath;
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to read video file: " + filePath, e);
//        }
//    }


}
