package com.example.commonsms.Exceptions;

public class AddressExistException extends RuntimeException{

    public AddressExistException (String message){
        super(message);
    }
}

