package fsegs.nosql.minisocialmediaapp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Post {
    @Id
    private String id;
    private String authorId; // Reference to the User ID
    private String content;
    private List<String> imagePaths;
    private List<String> videoPaths;
    private List<String> likes= new ArrayList<>(); // List of user IDs who liked the post
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
