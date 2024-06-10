package com.example.commonsms.Exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(ErrorMessage errorMessage, Object ...args) {
        super(String.format(errorMessage.getMessage() , args));
    }
}

