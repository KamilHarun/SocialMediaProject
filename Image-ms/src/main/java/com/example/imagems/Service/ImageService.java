package com.example.imagems.Service;

import com.example.imagems.Model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    Image uploadImage(MultipartFile file) throws IOException;

}
