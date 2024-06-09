package com.example.commonsms.Exceptions;

public class PasswordException extends RuntimeException {

    public PasswordException (ErrorMessage errorMessage , Object ...args){
        super(String.format(errorMessage.getMessage() ,args));
    }
}
