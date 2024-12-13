package fsegs.nosql.minisocialmediaapp.dtos.createInputs;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostCreateInput {
    private String authorId;
    private String content;
    private List<MultipartFile> images;
    private List<MultipartFile> videos;
}
