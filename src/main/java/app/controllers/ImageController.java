package app.controllers;

import app.entities.Image;
import app.repositories.ImageRepository;
import app.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.io.Resource;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private final StorageService storageService;
    private final ImageRepository imageRepository;

    @PostMapping(path = "/add")
    public String uploadImage(@RequestBody String base64) throws Exception {
        try {
            String filename = storageService.store(base64);
            Image image = new Image();
            image.setName(filename);
            image.setDescription("True size");
            imageRepository.save(image);
            return filename;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/get/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws Exception {

        Resource file = storageService.loadAsResource(filename);
        String urlFileName =  URLEncoder.encode("сало.jpg", StandardCharsets.UTF_8.toString());
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION,"filename=\""+urlFileName+"\"")
                .body(file);
    }
}
