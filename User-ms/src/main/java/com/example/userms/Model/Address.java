package com.example.userms.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "Street cannot be blank")
    String street;
    @NotBlank(message = "City cannot be blank")
    String city;
    @NotBlank(message = "Postal code cannot be blank")
    String postalCode;
    @NotBlank(message = "Country cannot be blank")
    String country;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    List<Users> users = new ArrayList<>();
}
