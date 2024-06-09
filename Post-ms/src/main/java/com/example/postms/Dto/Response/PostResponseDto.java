package com.example.postms.Dto.Response;

import com.example.commonsms.Constants.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponseDto implements Serializable {
    @NotBlank(message = ValidationConstants.TITLE_REQUIRED)
    String title;

    @NotBlank(message = ValidationConstants.CONTENT_REQUIRED)
    String content;

    long userId;
}
