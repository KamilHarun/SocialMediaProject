package com.example.userms.Dto.Request;

import com.example.userms.Model.Address;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestDto {
    Long id;
    String name;
    String surname;
    String email;
    String password;
    String phoneNumber;
    Long addressId;
}
