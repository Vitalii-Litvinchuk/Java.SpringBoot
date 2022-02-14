package app.storage;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public String store(String base64) {
        try {
            if (base64.isEmpty()) {
                throw new StorageException("Failed to store empty base64 ");
            }
            String[] charArray = base64.split(",");

            String extension = charArray[0].split("/")[1].split(";")[0];

            byte[] imageByte = Base64.decodeBase64(charArray[1]);

            String filename = RandomStringUtils.randomAlphabetic(40).concat(".")
                    .concat(extension);

            String directory = rootLocation + "/" + filename; //servletContext.getRealPath("/")+"images/sample.jpg";

            new FileOutputStream(directory).write(imageByte);

            return filename;
            //Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new StorageException("Failed to store file ", e);
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
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
            Files.createDirectory(Paths.get(rootLocation.toString() + "/" + "300x200"));
            Files.createDirectory(Paths.get(rootLocation.toString() + "/" + "600x500"));
            Files.createDirectory(Paths.get(rootLocation.toString() + "/" + "800x600"));
            Files.createDirectory(Paths.get(rootLocation.toString() + "/" + "1280x720"));
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
