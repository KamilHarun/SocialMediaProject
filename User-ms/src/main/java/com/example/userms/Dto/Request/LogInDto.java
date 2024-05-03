package com.example.userms.Dto.Request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogInDto implements Serializable {
    @NotBlank(message = "Please enter your email address.")
    @Size(min = 3, message = "Email address must be at least 3 characters.")
    @Column(nullable = false)
    String email;

    @NotBlank(message = "Please enter your password.")
    @Size(min = 4, message = "Password must be at least 4 characters.")
    String password;
}
