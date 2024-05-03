package com.example.postms.Dto.Response;

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
    String username;
    String surname;
    String email;
    String password;
    Long addressId;

}
