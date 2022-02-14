package app.controllers;

import app.entities.Image;
import app.repositories.ImageRepository;
import app.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private final StorageService storageService;
    private final ImageRepository imageRepository;

    @PostMapping(path = "/add")
    public String uploadImage(String base64) throws Exception {
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
}
