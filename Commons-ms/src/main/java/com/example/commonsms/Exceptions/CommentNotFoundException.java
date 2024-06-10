package com.example.commonsms.Exceptions;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(ErrorMessage errorMessage,Object ...args) {
        super(String.format(errorMessage.getMessage(),args));
    }
}
