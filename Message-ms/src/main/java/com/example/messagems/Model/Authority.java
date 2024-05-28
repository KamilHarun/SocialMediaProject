package com.example.messagems.Model;

import com.example.userms.Enums.UserAuthority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Authority implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    UserAuthority userAuthority;


    @ManyToMany(mappedBy = "authorities")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<Users> users;




}
