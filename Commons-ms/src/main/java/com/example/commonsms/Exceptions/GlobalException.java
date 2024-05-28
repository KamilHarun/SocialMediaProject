package com.example.commonsms.Exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalException {

    private final MessageSource messageSource;

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(BadRequest badRequest) {
        Map<String, String> error = new HashMap<>();
        error.put("error message : ", messageSource.getMessage(badRequest.getLocalizedMessage(), null, Locale.getDefault()));
        ErrorResponse errorResponse = ErrorResponse.builder()
                .iat(LocalDateTime.now())
                .message(error)
                .statusCode(400)
                .build();
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);

    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Map<String, String>> handleAlreadyExistException(AlreadyExistException alreadyExistException) {
        Map<String, String> error = new HashMap<>();
        error.put("error message", alreadyExistException.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<Map<String, String>> handlePasswordException(PasswordException passwordException) {
        Map<String, String> error = new HashMap<>();
        error.put("error message :", passwordException.getLocalizedMessage());
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePasswordException(UserNotFoundException userNotFoundException) {
        Map<String, String> error = new HashMap<>();
        error.put("error message :", userNotFoundException.getLocalizedMessage());
        return ResponseEntity.status(NOT_FOUND).body(error);

    }

    @ExceptionHandler(AddressExistException.class)
    public ResponseEntity<Map<String, String>> handleAddressException(AddressExistException addressExistException) {
        Map<String, String> error = new HashMap<>();
        error.put("error message : ", addressExistException.getLocalizedMessage());
        return ResponseEntity.status(CONFLICT).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String ,String>> handleUnauthorizedException(UnauthorizedException unauthorizedException){
        Map<String ,String> error = new HashMap<>();
        error.put("error message:" , unauthorizedException.getLocalizedMessage());
        return ResponseEntity.status(UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(EmailSendException.class)
    public ResponseEntity<Map<String,String>> handleEmailException (EmailSendException emailSendException){
        Map<String,String> error = new HashMap<>();
        error.put("error message :" , emailSendException.getLocalizedMessage());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(error);
    }


}



