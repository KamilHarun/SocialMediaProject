package com.example.userms.Service;

import com.example.userms.Dto.Request.LogInDto;
import com.example.userms.Dto.Request.RegisterDto;
import com.example.userms.Dto.Request.UserRequestDto;
import com.example.userms.Dto.Response.JwtResponseDto;
import com.example.userms.Dto.Response.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    JwtResponseDto signIn(RegisterDto registerDto);

    UserResponseDto findById(Long id);

    JwtResponseDto logIn(LogInDto logInDto);

    UserResponseDto findByEmail(LogInDto logInDto);

    Page<UserResponseDto> getAll(Pageable pageable);

    Page<UserResponseDto> findWithSpec(String name, String surname, String phoneNumber, Pageable pageable);

    UserResponseDto update(Long id, UserRequestDto userRequestDto);

    void delete(Long id);

    List<UserResponseDto> findUsersByAddressId(Long addressId);

    UserResponseDto resetPassword(UserRequestDto userRequestDto);

    void uploadProfilePicture(Long userId, MultipartFile file);

//    UserResponseDto findByAddressId(Long userId, Long addressId);
}
