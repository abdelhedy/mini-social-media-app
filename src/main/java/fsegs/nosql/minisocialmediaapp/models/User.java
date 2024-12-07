package fsegs.nosql.minisocialmediaapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    private long id;
    private String nom;
    private String prenom;

    private String email;
    private String password;

    // Embedded Profile class
    private Profile profile;

    private List<String> followers; // List of user IDs
    private List<String> following; // List of user IDs

    private LocalDate createdAt;
    private LocalDate updatedAt;


}
