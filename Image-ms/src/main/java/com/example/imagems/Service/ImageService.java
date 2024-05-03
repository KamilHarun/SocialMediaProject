package com.example.imagems.Service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String uploadImage(MultipartFile file);

    void deleteImage(String imageId);
}
