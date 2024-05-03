package com.example.postms.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostRequestDto {
    Long id;
    String title;
    String content;
    long userId;
    String imageUrl;


}
