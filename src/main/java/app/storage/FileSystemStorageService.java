package app.storage;


import lombok.SneakyThrows;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Path loadSpecial(String format, String filename) {
        Path location = Paths.get(this.rootLocation.toString() + "/" + format);
        return location.resolve(filename);
    }

    @Override
    public String store(String base64) {
        try {
            if (base64.isEmpty()) {
                throw new StorageException("Failed to store empty base64 ");
            }
            String[] charArray = base64.split(",");

//          String extension = charArray[0].split("/")[1].split(";")[0];

            byte[] imageByte = Base64.decodeBase64(charArray[1]);

            UUID uuid = UUID.randomUUID();

            String filename = uuid.toString().concat(".jpg");
            //.concat(extension);

            String directory = rootLocation + "/";

            new FileOutputStream(directory.concat(filename)).write(imageByte);

            InputStream is = new ByteArrayInputStream(imageByte);
            BufferedImage bi = ImageIO.read(is);

            BufferedImage image300x200 = resizeImage(bi, 300, 200);
            File imagef300x200 = new File(directory.concat("300x200/".concat("300x200_".concat(filename))));
            ImageIO.write(image300x200, "JPEG", imagef300x200);

            BufferedImage image600x350 = resizeImage(bi, 600, 350);
            File imagef600x350 = new File(directory.concat("600x350/".concat("600x350_".concat(filename))));
            ImageIO.write(image600x350, "JPEG", imagef600x350);

            BufferedImage image800x450 = resizeImage(bi, 800, 450);
            File imagef800x450 = new File(directory.concat("800x450/".concat("800x450_".concat(filename))));
            ImageIO.write(image800x450, "JPEG", imagef800x450);

            BufferedImage image1280x720 = resizeImage(bi, 1280, 720);
            File imagef1280x720 = new File(directory.concat("1280x720/".concat("1280x720_".concat(filename))));
            ImageIO.write(image1280x720, "JPEG", imagef1280x720);

            return filename;
        } catch (IOException e) {
            throw new StorageException("Failed to store file ", e);
        } catch (Exception e) {
            throw new StorageException("Failed to resize file ", e);
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public Resource loadAsResourceSpecial(String filename) {
        try {
            String[] fileParts = filename.split("_");
            Path file = loadSpecial(fileParts[0], filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        } finally {
            try {
                Files.createDirectory(Paths.get(rootLocation.toString() + "/" + "300x200"));
                Files.createDirectory(Paths.get(rootLocation.toString() + "/" + "600x350"));
                Files.createDirectory(Paths.get(rootLocation.toString() + "/" + "800x450"));
                Files.createDirectory(Paths.get(rootLocation.toString() + "/" + "1280x720"));
            } catch (IOException e) {
                throw new StorageException("Could not initialize storage", e);
            }
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(originalImage)
                .size(targetWidth, targetHeight)
                .outputFormat("JPEG")
                .outputQuality(1)
                .toOutputStream(outputStream);
        byte[] data = outputStream.toByteArray();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        return ImageIO.read(inputStream);
    }
}
