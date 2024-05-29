package com.example.imagems.Service.Impl;

import com.example.imagems.Model.Image;
import com.example.imagems.Repository.ImageRepo;
import com.example.imagems.Service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepo imageRepo;

    public Image uploadImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setData(file.getBytes());
        return imageRepo.save(image);
    }

}