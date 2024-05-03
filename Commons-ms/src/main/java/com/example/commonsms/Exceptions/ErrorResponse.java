package com.example.commonsms.Exceptions;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {
    Map<String,String> message;
    String path;
    int statusCode;
    LocalDateTime iat;

}
