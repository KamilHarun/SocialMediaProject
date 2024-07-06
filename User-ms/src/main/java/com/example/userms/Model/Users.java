package com.example.userms.Model;

import com.example.userms.Enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Size(min = 3, message = "Name must be at least 3 characters.")
    @Column(nullable = false)
    String name;
    @Size(min = 3, message = "Surname must be at least 3 characters.")
    @Column(nullable = false)
    String surname;
    @Size(min = 3, message = "Password must be at least 3 characters.")
    @Column(nullable = false)
    String password;
    @Email
    @Column(unique = true, nullable = false)
    String email;
    @Column(unique = true,nullable = false)
    String phoneNumber;
    String profilePicturesUrl;
    @ManyToOne
    @JoinColumn(name = "address_id")
    Address address;

    @Enumerated(EnumType.STRING)
    UserType userType;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority" ,
            joinColumns = @JoinColumn(name = "user_id" , referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id" , referencedColumnName = "id"))
    List<Authority> authorities;


}
