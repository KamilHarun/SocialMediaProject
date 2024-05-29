package com.example.imagems.Controller;

import com.example.imagems.Model.Image;
import com.example.imagems.Service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<Image> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(imageService.uploadImage(file) , OK);
    }
    }
