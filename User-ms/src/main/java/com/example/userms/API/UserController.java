package com.example.userms.API;

import com.example.userms.Dto.Request.LogInDto;
import com.example.userms.Dto.Request.RegisterDto;
import com.example.userms.Dto.Request.UserRequestDto;
import com.example.userms.Dto.Response.JwtResponseDto;
import com.example.userms.Dto.Response.UserResponseDto;
import com.example.userms.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@RestController
public class UserController {
    private final UserService userService;
    @Operation(summary = "Register a new User" , description = "Register a new User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200" , description = "Created new User"),
            @ApiResponse(responseCode = "400" , description = "Invalid request format") ,
            @ApiResponse(responseCode = "500" , description = "Internal server error")
    })
    @PostMapping("/register")
    public ResponseEntity<JwtResponseDto> signIn(@Valid @RequestBody RegisterDto registerDto){
        return new ResponseEntity<>(userService.signIn(registerDto) , CREATED);
    }

    @Operation(summary = "Find user by Id" , description = "Get all details of user by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })

    @GetMapping("/findById")
    public ResponseEntity<UserResponseDto> findById(@RequestParam("id") Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(summary = "LogInd" , description = "Login to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> logIn (@Valid @RequestBody LogInDto logInDto){
        return new ResponseEntity<>(userService.logIn(logInDto) , OK);
    }

    @Operation(summary = "Find user by Email address" , description = "Get details of User by Email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })

    @GetMapping("/email")
    public ResponseEntity<UserResponseDto> findByEmail(@RequestBody LogInDto logInDto){
        return new ResponseEntity<>(userService.findByEmail(logInDto) , OK);
    }

    @Operation(summary = "Get all users", description = "Get a list of all users with pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the users"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred"),
            @ApiResponse(responseCode = "404", description = "User not found"),

    })
    @GetMapping("/all")
    public Page<UserResponseDto> getAllUser (Pageable pageable)
    {
        return userService.getAll(pageable);
    }

    @Operation(summary = "Get users with spec" , description = "Get a list of users with spec")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the users"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    @GetMapping("/spec")
    public ResponseEntity<Page<UserResponseDto>> findWithSpec (@RequestParam(required = false) String name,
                                               @RequestParam(required = false)  String surname,
                                               @RequestParam (required = false) String phoneNumber,
                                               Pageable pageable){
        return new ResponseEntity<>(userService.findWithSpec(name,surname,phoneNumber,pageable) ,OK);
    }


    @Operation(summary = "Update user", description = "Update a user's information by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("id") Long id
            , @RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(userService.update(id,userRequestDto) , OK);
    }


    @Operation(summary = "Delete user", description = "Delete a user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    @DeleteMapping("/{id}")
    public void deleteUser(@RequestParam Long id){
        userService.delete(id);
    }

    @Operation(summary = "Get users with Address" , description = "Get a list of users with Address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the users"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    @GetMapping("/address/{addressId}")
    public ResponseEntity<List<UserResponseDto>> findUsersByAddressId(@PathVariable Long addressId) {
        List<UserResponseDto> users = userService.findUsersByAddressId(addressId);
        return ResponseEntity.ok(users);
    }


    @Operation(summary = "Reset user password", description = "Reset a user's password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    @PostMapping("/reset-password")
    public ResponseEntity<UserResponseDto> resetUserPassword (@Valid @RequestBody UserRequestDto userRequestDto){
        return new ResponseEntity<>(userService.resetPassword(userRequestDto) , OK);
    }

    @Operation(summary = "Upload user profile picture", description = "Upload a profile picture for the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile picture uploaded successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    @PostMapping("/upload-profile-picture/{userId}")
    public ResponseEntity<Void> uploadProfilePicture(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        userService.uploadProfilePicture(userId, file);
        return ResponseEntity.ok().build();
    }



}





