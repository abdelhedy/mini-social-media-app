package fsegs.nosql.minisocialmediaapp.repositories;

import jakarta.annotation.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private static final String UPLOAD_DIR = "E:/postify_files"; // Match this to your upload directory

    public UrlResource loadFileAsResource(String fileName) {

        try {
            // Resolve the file path
            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();

            // Verify the file exists and is readable
            if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
                throw new RuntimeException("File not found or is not readable: " + fileName);
            }

            // Return the file as a UrlResource
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Error loading file as resource: " + fileName, ex);
        }
    }
}
