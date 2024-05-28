package com.example.userms.Repository;

import com.example.userms.Dto.Response.UserResponseDto;
import com.example.userms.Model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users , Long> {
    Optional<Users> findByEmail(String email);

    Optional<Users> findByName(String username);

    @Query("select u from Users u where" +
            "(:name is null or u.name=:name)" +
            " and (:surname is null or u.surname=:surname)" +
            " and (:email is null or u.email = :email)" +
            "and (:phoneNumber is null or u.phoneNumber = :phoneNumber)"
    )
    Page<Users> findWithSpec(String name, String surname, String phoneNumber, Pageable pageable);

//    @Query
//    Users findByUserIdAndAddressId(Long userId, Long addressId);
//}
}