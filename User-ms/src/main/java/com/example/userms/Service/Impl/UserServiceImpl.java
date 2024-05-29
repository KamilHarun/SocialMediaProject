package com.example.userms.Service.Impl;

import com.example.commonsms.Dto.EmailDto;
import com.example.commonsms.Exceptions.*;
import com.example.securityms.config.JwtService;
import com.example.userms.Config.UserMapper;
import com.example.userms.Dto.Request.LogInDto;
import com.example.userms.Dto.Request.RegisterDto;
import com.example.userms.Dto.Response.JwtResponseDto;
import com.example.userms.Dto.Response.UserResponseDto;
import com.example.userms.Enums.UserAuthority;
import com.example.userms.Enums.UserType;
import com.example.userms.Feign.EmailFeign;
import com.example.userms.Model.Address;
import com.example.userms.Model.Authority;
import com.example.userms.Model.Users;
import com.example.userms.Repository.AddressRepo;
import com.example.userms.Repository.UserRepo;
import com.example.userms.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepo addressRepo;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final EmailFeign emailFeign;


    @Override
    public JwtResponseDto signIn(RegisterDto registerDto) {

        Optional<Address> address = Optional.ofNullable(addressRepo.findById(registerDto.getAddressId()).orElseThrow(() ->
                new NotFoundException("Address not found")));
        if (!registerDto.getPassword().equals(registerDto.getRepeatedPassword())) {
            throw new PasswordException("Passwords are different");
        }
        List<Authority> authorityList = new ArrayList<>();

        registerDto.getAuthorities().forEach(s -> {
            UserAuthority userAuthority = UserAuthority.valueOf(s);
            authorityList.add(Authority.builder()
                    .userAuthority(userAuthority)
                    .build());
        });

        Users user = Users.builder()
                .name(registerDto.getName())
                .surname(registerDto.getSurname())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .authorities(authorityList)
                .userType(UserType.valueOf(registerDto.getUserType()))
                .phoneNumber(registerDto.getPhoneNumber())
                .address(address.get())
                .build();
        Users saveUser = userRepo.save(user);

        Set<SimpleGrantedAuthority> authorities = saveUser.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getUserAuthority().name()))
                .collect(Collectors.toSet());

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(saveUser.getName())
                .password(passwordEncoder.encode(saveUser.getPassword()))
                .authorities(authorities)
                .build();

        ResponseEntity<String> stringResponseEntity = emailFeign.sendEmail(EmailDto);

        String token = jwtService.generateToken((org.springframework.security.core.userdetails.User) userDetails);
            return JwtResponseDto.builder()
                    .jwt(token)
                    .build();
        }

    @Override
    public UserResponseDto findById(Long id) {
        Optional<Users> byId = userRepo.findById(id);
        Users user = byId.orElseThrow(() ->
                new UserNotFoundException("User not found with id" + id));
        return userMapper.UserToDto(user);
    }

    @Override
    public JwtResponseDto logIn(LogInDto loginDto) {
        Optional<Users> byEmail = userRepo.findByEmail(loginDto.getEmail());
        Users user = byEmail.orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new PasswordException("Password does not match");
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getName())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();

        String token = jwtService.generateToken((org.springframework.security.core.userdetails.User) userDetails);
        return JwtResponseDto.builder()
                .jwt(token)
                .build();
    }

    @Override
    public UserResponseDto findByEmail(LogInDto logInDto) {
        Optional<Users> userOptional = userRepo.findByEmail(logInDto.getEmail());
        Users user = userOptional.orElseThrow(() ->
                new UserNotFoundException("The user with the given email address was not found"));

        return Mappers.getMapper(UserMapper.class).UserToDto(user);
    }

    @Override
    public Page<UserResponseDto> getAll(Pageable pageable) {
        Page<Users> users = userRepo.findAll(pageable);
        List<UserResponseDto> userResponseDtoList = users.getContent().stream()
                .map(user -> UserResponseDto.builder()
                        .name(user.getName())
                        .surname(user.getSurname())
                        .email(user.getEmail())
                        .addressId(user.getId())
                        .build())
                .collect(Collectors.toList());
        return new PageImpl<>(userResponseDtoList, pageable, users.getTotalElements());
    }

    @Override
    public Page<UserResponseDto> findWithSpec(String name, String surname, String phoneNumber, Pageable pageable) {
        Page<Users> users = userRepo.findWithSpec(name, surname, phoneNumber, pageable);
        return users.map(users1 -> UserResponseDto.builder()
                .name(users1.getName())
                .surname(users1.getSurname())
                .email(users1.getEmail())
                .password(users1.getPassword())
                .addressId(users1.getAddress().getId())
                .build());
    }

//    @Override
//    public UserResponseDto findByAddressId(Long userId, Long addressId) {
//        Users user = userRepo.findByUserIdAndAddressId(userId, addressId);
//
//        if (user == null) {
//            throw new ResourceNotFoundException("User not found for userId: " + userId + " and addressId: " + addressId);
//        }
//        UserResponseDto userResponseDto = userMapper.UserToDto(user);
//
//        return userResponseDto;
//    }


}




