package fsegs.nosql.minisocialmediaapp.models;

import lombok.Data;

@Data
public class Profile {
    private String bio;
    private String profilePicture;
    private String coverPhoto;
    private String location;
    private String website;
}
