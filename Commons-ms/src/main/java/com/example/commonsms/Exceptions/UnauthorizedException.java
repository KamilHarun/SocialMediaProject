package com.example.commonsms.Exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(ErrorMessage errorMessage , Object ...args) {
    }
}
