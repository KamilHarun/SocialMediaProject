package com.example.commonsms.Exceptions;

public class AddressNotFound extends RuntimeException {
    public AddressNotFound (ErrorMessage errorMessage , Object ...args){
        super(String.format(errorMessage.getMessage() , args));

    }
}
