package fsegs.nosql.minisocialmediaapp.dtos.responseDtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostResponseDto {
    private String id;
    private String authorNom;
    private String authorPrenom;
    private String content;
    private List<String> imageURLs;
    private List<String> videoURLs;
    private List<String> likes;
}
