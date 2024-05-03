package com.example.userms.Dto.Request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterDto implements Serializable {
    @NotBlank(message = "Please enter your name.")
    String name;
    @NotBlank(message = "Please enter your surname.")
    String surname;
    @Size(min = 5, max = 15, message = "Email must be between 5 and 15 characters.")
    @NotBlank(message = "Please enter your email address.")
    String email;
    @NotBlank(message = "Please enter your password.")
    String password;

    @NotBlank(message = "Please enter your phone number")
    @Column(unique = true, nullable = false)
    @Size(min = 5, max = 15, message = "Phone number must be between 10 and 12 characters.")
    String phoneNumber;

    String repeatedPassword;
    Long addressId;
    String userType;
    List<String> authorities;
}
