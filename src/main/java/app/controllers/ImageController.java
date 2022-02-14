package app.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;


@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private static String imageDirectory = System.getProperty("user.dir") + "/images/";

    @PostMapping(path = "/add")
//    public String uploadImage(@RequestParam MultipartFile file) throws Exception {
    public String uploadImage(String file) throws Exception {

        makeDirectoryIfNotExist(imageDirectory);

        String[] charArray = file.split(",");

        String extension = charArray[0].split("/")[1].split(";")[0];

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = new byte[0];
        bytes = decoder.decode(charArray[1]);

        String filename= RandomStringUtils.randomAlphabetic(20).concat(".")
                .concat(extension);
        String directory = "images/" + filename;


        FileOutputStream fos = new FileOutputStream(directory);
        fos.write(bytes);
        fos.close();

        return filename;

//        String base64Image = base64.split(",")[1];
//        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
//
//        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write( img, "png", baos );
//
//        MultipartFile file = new MockMultipartFile("file.png", baos.toByteArray());

//        makeDirectoryIfNotExist(imageDirectory);
//        String filename= RandomStringUtils.randomAlphabetic(20).concat(".")
//                .concat(FilenameUtils.getExtension(file.getOriginalFilename()));
//
//        Path fileNamePath = Paths.get(imageDirectory, filename);
//        try {
//            Files.write(fileNamePath, file.getBytes());
//        } catch (IOException ex) {
//            return new ResponseEntity<>("Image is not uploaded", HttpStatus.BAD_REQUEST).toString();
//        }
//        return filename;
    }

    private void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
