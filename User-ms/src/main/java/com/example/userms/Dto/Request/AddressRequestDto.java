package com.example.userms.Dto.Request;

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
        @NotBlank(message = "Street cannot be null")
        String street;
        @NotBlank(message = "City cannot be null")
        String city;
        @NotBlank(message = "Postal code cannot be null")
        String postalCode;
        @NotBlank(message = "Country cannot be null")
        String country;


}
