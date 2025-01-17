package com.example.messagems.Repository;

import com.example.messagems.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository <Users, Long> {
    Optional<Users> findByName(String username);
}
