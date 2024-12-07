package fsegs.nosql.minisocialmediaapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    private Long id;
    private String authorId; // Reference to the User ID
    private String content;
    private List<String> media; // List of media URLs
    private List<String> likes; // List of user IDs who liked the post
}
