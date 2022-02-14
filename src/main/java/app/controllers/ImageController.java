package app.controllers;

import app.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.id.GUIDGenerator;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;


@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final StorageService storageService;

    @PostMapping(path = "/add")
//    public String uploadImage(@RequestParam MultipartFile file) throws Exception {
    public String uploadImage(String base64) throws Exception {
        String filename = storageService.store(base64);

        return filename;
    }

}
