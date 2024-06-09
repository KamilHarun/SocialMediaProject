package com.example.userms.Dto.Request;

import com.example.commonsms.Constants.ValidationConstants;
import com.example.userms.Model.Users;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressRequestDto implements Serializable {
        long id;
        @NotBlank(message = ValidationConstants.STREET_REQUIRED)
        String street;
        @NotBlank(message = ValidationConstants.CITY_REQUIRED)
        String city;
        @NotBlank(message = ValidationConstants.POSTAL_CODE_REQUIRED)
        String postalCode;
        @NotBlank(message = ValidationConstants.COUNTRY_REQUIRED)
        String country;
}
