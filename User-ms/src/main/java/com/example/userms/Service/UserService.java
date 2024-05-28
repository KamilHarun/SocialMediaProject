package com.example.userms.Service;

import com.example.userms.Dto.Request.LogInDto;
import com.example.userms.Dto.Request.RegisterDto;
import com.example.userms.Dto.Response.JwtResponseDto;
import com.example.userms.Dto.Response.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    JwtResponseDto signIn(RegisterDto registerDto);

    UserResponseDto findById(Long id);

    JwtResponseDto logIn(LogInDto logInDto);

    UserResponseDto findByEmail(LogInDto logInDto);

    Page<UserResponseDto> getAll(Pageable pageable);

    Page<UserResponseDto> findWithSpec(String name, String surname, String phoneNumber, Pageable pageable);

//    UserResponseDto findByAddressId(Long userId, Long addressId);
}
