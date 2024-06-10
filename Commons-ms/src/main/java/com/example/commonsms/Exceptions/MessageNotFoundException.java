package com.example.commonsms.Exceptions;

public class MessageNotFoundException extends RuntimeException{
    public MessageNotFoundException(ErrorMessage  errorMessage , Object ... args) {
        super(String.format(errorMessage.getMessage() , args));
    }
}
