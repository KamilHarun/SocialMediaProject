package com.example.commonsms.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDto implements Serializable {
    Long id;
    String name;
    String surname;
    String email;
    String password;
    String phoneNumber;
    Long addressId;

}