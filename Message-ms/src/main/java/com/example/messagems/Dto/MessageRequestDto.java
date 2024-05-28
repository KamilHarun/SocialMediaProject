package com.example.messagems.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageRequestDto implements Serializable {
    Long id;
    Long senderId;
    Long receiverId;
    String content;
}
