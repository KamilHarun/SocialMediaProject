package com.example.commentms.java.com.example.commentms.Dto.Request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentRequestDto implements Serializable {
    Long id;
    String text;
    long commentedPostId;
    String content;

}
