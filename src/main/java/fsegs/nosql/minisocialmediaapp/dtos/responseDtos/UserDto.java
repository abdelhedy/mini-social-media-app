package fsegs.nosql.minisocialmediaapp.dtos.responseDtos;

import fsegs.nosql.minisocialmediaapp.dtos.createInputs.RegisterRequest;
import fsegs.nosql.minisocialmediaapp.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;
    private String nom;
    private String prenom;

    public static UserDto toDto(User user) {
        // null check
        return UserDto.builder()
                .id(user.getId())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .build();
    }

    public static User toEntity(RegisterRequest request) {
        // null check
        return User.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(request.getPassword())
                .date_naiss(request.getDate_naiss())
                .build();
    }

}
