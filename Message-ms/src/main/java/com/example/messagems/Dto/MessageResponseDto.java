package com.example.messagems.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageResponseDto implements Serializable {
    Long id;
    Long senderId;
    Long receiverId;
    String content;

}
