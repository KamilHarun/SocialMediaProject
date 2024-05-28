package com.example.userms.API;

import com.example.userms.Dto.Request.LogInDto;
import com.example.userms.Dto.Request.RegisterDto;
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
    public Page<UserResponseDto> getAllUser (Pageable pageable){
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

//    @GetMapping("/{userId}/{addressId}")
//    public ResponseEntity<UserResponseDto> getUserWithAddressId(@PathVariable Long userId, @PathVariable Long addressId) {
//        return new ResponseEntity<>(userService.findByAddressId(userId, addressId), OK);
//    }



}




