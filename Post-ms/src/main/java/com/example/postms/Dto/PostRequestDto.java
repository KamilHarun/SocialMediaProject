package com.example.postms.Dto;

import com.example.commonsms.Constants.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostRequestDto implements Serializable {
    Long id;

    @NotBlank(message = ValidationConstants.TITLE_REQUIRED)
    String title;

    @NotBlank(message = ValidationConstants.CONTENT_REQUIRED)
    String content;

    long userId;
    String imageUrl;
}