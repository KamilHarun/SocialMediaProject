package com.example.commonsms.Exceptions;

public class AddressExistException extends RuntimeException{

    public AddressExistException (ErrorMessage errorMessage , Object ... args){
        super(String.format(errorMessage.getMessage() , args));
    }
}

