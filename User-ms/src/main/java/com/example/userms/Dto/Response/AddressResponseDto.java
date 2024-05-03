package com.example.userms.Dto.Response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressResponseDto implements Serializable {
    String street;
    String city;
    String postalCode;
    String country;
}
