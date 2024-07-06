package com.example.userms.Service.Impl;

import com.example.commonsms.Dto.EmailDto;
import com.example.commonsms.Exceptions.*;
import com.example.securityms.config.JwtService;
import com.example.userms.Config.UserMapper;
import com.example.userms.Dto.Request.LogInDto;
import com.example.userms.Dto.Request.RegisterDto;
import com.example.userms.Dto.Request.UserRequestDto;
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
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.commonsms.Exceptions.ErrorMessage.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepo addressRepo;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    private final KafkaTemplate<Object, Object> kafkaTemplate;
    private final EmailFeign emailFeign;


    @Override
    public JwtResponseDto signIn(RegisterDto registerDto) {
        log.info("Starting registration for user with email address: {}", registerDto.getEmail());

        Optional<Address> address = Optional.ofNullable(addressRepo.findById(registerDto.getAddressId())
                .orElseThrow(() -> new AddressNotFound(ADDRESS_NOT_FOUND_EXCEPTION)));

        if (!registerDto.getPassword().equals(registerDto.getRepeatedPassword())) {
            log.error("Password mismatch for email: {}", registerDto.getEmail());
            throw new PasswordException(PASSWORD_DOES_NOT_MATCH_EXCEPTION);
        }

        Set<Authority> authorityList = new HashSet<>();
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
                .authorities(new ArrayList<>(authorityList))
                .userType(UserType.valueOf(registerDto.getUserType()))
                .phoneNumber(registerDto.getPhoneNumber())
                .address(address.get())
                .build();
        Users saveUser = userRepo.save(user);
        log.info("User saved with email: {}", saveUser.getEmail());

        Set<SimpleGrantedAuthority> authorities = saveUser.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getUserAuthority().name()))
                .collect(Collectors.toSet());

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(saveUser.getName())
                .password(passwordEncoder.encode(saveUser.getPassword()))
                .authorities(authorities)
                .build();

//        kafkaTemplate.send("userTopic", registerDto);

        String token = jwtService.generateToken((org.springframework.security.core.userdetails.User) userDetails);
        log.info("Token generated for email: {}", saveUser.getEmail());

        sendWelcomeEmail(saveUser.getEmail(), token);

        return JwtResponseDto.builder()
                .jwt(token)
                .build();
    }

    private void sendWelcomeEmail(String email, String token) {
        EmailDto emailDto = new EmailDto();
        emailDto.setTo(email);
        emailDto.setSubject("Welcome");
        emailDto.setBody("Your registration is successful.");

        emailFeign.sendEmail(emailDto);
    }

    @Override
    @Cacheable(cacheNames = "users")
    public UserResponseDto findById(Long id) {
        log.info("Finding User by Id : {}", id);
        Optional<Users> byId = userRepo.findById(id);
        Users user = byId.orElseThrow(() ->
                new UserNotFoundException(USER_NOT_FOUND_WITH_ID_EXCEPTION , id));
        return userMapper.UserToDto(user);
    }

    @Override
    public JwtResponseDto logIn(LogInDto loginDto) {
        log.info("Logging in user with email: {}", loginDto.getEmail());
        Optional<Users> byEmail = userRepo.findByEmail(loginDto.getEmail());
        Users user = byEmail.orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_EXCEPTION));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            log.error("Password mismatch for email: {}", loginDto.getEmail());
            throw new PasswordException(PASSWORD_DOES_NOT_MATCH_EXCEPTION);
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getName())
                .password(passwordEncoder.encode(user.getPassword()
                )
                )
                .build();

        kafkaTemplate.send("userTopic" , loginDto);

        String token = jwtService.generateToken((org.springframework.security.core.userdetails.User) userDetails);
        log.info("Token generated for email: {}", user.getEmail());
        return JwtResponseDto.builder()
                .jwt(token)
                .build();
    }

    @Override
    @Cacheable(value = "users"  ,key = "#logInDto.getEmail()")
    public UserResponseDto findByEmail(LogInDto logInDto) {
        log.info("Finding user by email: {}", logInDto.getEmail());
        Optional<Users> userOptional = userRepo.findByEmail(logInDto.getEmail());
        Users user = userOptional.orElseThrow(() ->
                new UserNotFoundException(USER_NOT_FOUND_WITH_EMAIL_EXCEPTION)
        );

        return Mappers.getMapper(UserMapper.class).UserToDto(user);
    }

    @Override
    @Cacheable(value = "getAllUsers", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort.toString()")
    public Page<UserResponseDto> getAll(Pageable pageable) {
        log.info("Getting all user with pagination : {}" , pageable);
        Page<Users> users = userRepo.findAll(pageable);
        if (users.isEmpty()) {
            log.warn("No users found");
            throw new UserNotFoundException(USER_NOT_FOUND_EXCEPTION);
        }
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
    @Cacheable(value = "findWithSpecUsers", key = "#name + '-' + #surname + '-' + #phoneNumber + '-' + #pageable.pageNumber " +
            "+ '-' + #pageable.pageSize + '-' + #pageable.sort.toString()")
    public Page<UserResponseDto> findWithSpec(String name, String surname, String phoneNumber, Pageable pageable) {
        log.info("Finding users with spec - Name: {}, Surname: {}, Phone Number: {}", name, surname, phoneNumber);
        Page<Users> users = userRepo.findWithSpec(name, surname, phoneNumber, pageable);
        if (users.isEmpty()) {
            log.warn("No users found with specified criteria");
            throw new UserNotFoundException(USER_NOT_FOUND_EXCEPTION);
        }
        List<UserResponseDto> userResponseDtos = users.getContent().stream()
                .map(user -> UserResponseDto.builder()
                        .name(user.getName())
                        .surname(user.getSurname())
                        .email(user.getEmail())
                        .password(passwordEncoder.encode(user.getPassword()))
                        .addressId(user.getAddress().getId())
                        .build())
                .collect(Collectors.toList());

        return new PageImpl<>(userResponseDtos, pageable, users.getTotalElements());
    }

    @Override
    public UserResponseDto update(Long id, UserRequestDto userRequestDto) {
        log.info("Updating user with ID: {}", id);
        Users existUser = userRepo.findById(id).orElseThrow(() ->
                new UserNotFoundException(USER_NOT_FOUND_WITH_ID_EXCEPTION, id));

        Address address = addressRepo.findById(userRequestDto.getAddressId()).orElseThrow(() ->
                new AddressNotFound(ADDRESS_NOT_FOUND_EXCEPTION));

        existUser.setName(userRequestDto.getName());
        existUser.setSurname(userRequestDto.getSurname());
        existUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        existUser.setEmail(userRequestDto.getEmail());
        existUser.setAddress(address);

        Users users = userRepo.save(existUser);

        return userMapper.UserToDto(users);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user");
        Users users = userRepo.findById(id).orElseThrow(() ->
                new UserNotFoundException(USER_NOT_FOUND_EXCEPTION));
        userRepo.delete(users);
    }

    @Override
    public List<UserResponseDto> findUsersByAddressId(Long addressId) {
        log.info("Find user by Address Id : {}" , addressId);
        List<Users> users = userRepo.findByAddressId(addressId);
        if (users.isEmpty()) {
            log.warn("No user found with given address");
            throw new UserNotFoundException(USER_NOT_FOUND_WITH_ID_EXCEPTION, addressId);
        }
        return users.stream()
                .map(userMapper::UserToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto resetPassword(UserRequestDto userRequestDto) {
        Users users = userRepo.findByEmail(userRequestDto.getEmail()).orElseThrow(() ->
                new UserNotFoundException(USER_NOT_FOUND_WITH_EMAIL_EXCEPTION));
        users.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        Users save = userRepo.save(users);
        return userMapper.UserToDto(save);
    }

    @Override
    public void uploadProfilePicture(Long userId, MultipartFile file) {
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_WITH_ID_EXCEPTION, userId));

        String filePath = saveFile(file);

        user.setProfilePicturesUrl(filePath);
        userRepo.save(user);
    }

    private String saveFile(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + extension;

            Path uploadPath = Paths.get("uploads/");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(file.getInputStream(), filePath);

            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        }
    }
}
