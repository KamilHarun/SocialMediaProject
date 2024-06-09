package com.example.userms.Dto.Request;

import com.example.commonsms.Constants.ValidationConstants;
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
    @NotBlank(message = ValidationConstants.NAME_REQUIRED)
    String name;
    @NotBlank(message = ValidationConstants.SURNAME_REQUIRED)
    String surname;
    @Size(min = 3, message = ValidationConstants.EMAIL_LENGTH)
    @NotBlank(message = ValidationConstants.EMAIL_REQUIRED)
    String email;
    @NotBlank(message = ValidationConstants.PASSWORD_REQUIRED)
    String password;

    @NotBlank(message = ValidationConstants.PHONE_NUMBER_REQUIRED)
    @Size(min = 10, max = 12, message = ValidationConstants.PHONE_NUMBER_LENGTH)
    String phoneNumber;

    String repeatedPassword;
    Long addressId;
    String userType;
    List<String> authorities;
}
