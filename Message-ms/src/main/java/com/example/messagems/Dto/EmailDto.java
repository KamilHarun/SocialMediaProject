package com.example.messagems.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailDto {
    @Email
    @NotBlank
    String to;

    @NotBlank
    String subject;

    @NotBlank
    String message;
}
