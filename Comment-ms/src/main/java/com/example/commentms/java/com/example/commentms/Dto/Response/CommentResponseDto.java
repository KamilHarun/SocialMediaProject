package com.example.commentms.java.com.example.commentms.Dto.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponseDto {
    String text;
    long commentedPostId;
}
