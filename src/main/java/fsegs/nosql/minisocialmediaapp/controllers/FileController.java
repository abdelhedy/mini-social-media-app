package fsegs.nosql.minisocialmediaapp.controllers;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
public class FileController {
    private static final String UPLOAD_DIR = "E:/postify_files/";

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();

            // Check if the file exists
            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build(); // Return 404 if file not found
            }

            Resource resource = new UrlResource(filePath.toUri());
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream"; // Default content type
            }
            String filename = resource.getFilename();

            return ResponseEntity.ok()
                    .header("Content-Type", contentType)
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return ResponseEntity.badRequest().build(); // Return 400 for any other error
        }
    }
//    private static final String UPLOAD_DIR = "E:/postify_files/";
//
//    @GetMapping("/{fileName}")
//    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
//        try {
//            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
//            Resource resource = (Resource) new UrlResource(filePath.toUri());
//            String contentType = Files.probeContentType(filePath);
//            String filename = ((UrlResource) resource).getFilename();
//
//            return ResponseEntity.ok()
//                    .header("Content-Type", contentType)
//                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
//                    .body(resource);
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

}
