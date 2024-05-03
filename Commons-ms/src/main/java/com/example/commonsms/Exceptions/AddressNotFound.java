package com.example.commonsms.Exceptions;

public class AddressNotFound extends RuntimeException {
    public AddressNotFound (String message){
        super(message);

    }
}
