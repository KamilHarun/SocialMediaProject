package com.example.commonsms.Exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(ErrorMessage errorMessage, Object... args) {
        super(String.format(errorMessage.getMessage(), args));
    }
}

