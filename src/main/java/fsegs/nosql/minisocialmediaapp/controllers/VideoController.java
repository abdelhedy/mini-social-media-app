package fsegs.nosql.minisocialmediaapp.controllers;

import jakarta.annotation.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

//@RestController
public class VideoController {
//    private static final String UPLOAD_DIR = "E:/postify_files";
//
//    @GetMapping("/videos/{fileName}")
//    public ResponseEntity<Resource> getVideo(@PathVariable String fileName) throws Exception {
//        Path path = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
//        Resource resource = (Resource) new UrlResource(path.toUri());
//        return ResponseEntity.ok()
//                .header("Content-Type", "video/mp4") // Set appropriate video MIME type
//                .body(resource);
//    }
}
