package com.example.imagems.Service.Impl;

import com.example.imagems.Service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.example.commonms.Constants.CustomConstants.UPLOAD_DIR;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    @Override
    public String uploadImage(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image file: " + e.getMessage());
        }
    }

    @Override
    public void deleteImage(String imageId) {
        try {
            String imagePath = getImagePathFromDatabase(imageId);

            Path filePath = Paths.get(imagePath).toAbsolutePath().normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image file: " + e.getMessage());
        }
    }

    private String getImagePathFromDatabase(String imageId) {
        return UPLOAD_DIR + imageId + ".jpg"; // Örnek olarak sabit bir yol döndürüyoruz
    }
}