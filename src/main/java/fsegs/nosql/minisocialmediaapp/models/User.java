package fsegs.nosql.minisocialmediaapp.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {
    @Id
    private String id;
    private String nom;
    private String prenom;

    private String email;
    private String password;
    private String phone;
    private LocalDate date_naiss;

    // Embedded Profile class
    private Profile profile;

    private List<String> followers; // List of user IDs
    private List<String> following; // List of user IDs

    private LocalDate createdAt;
    private LocalDate updatedAt;


}
