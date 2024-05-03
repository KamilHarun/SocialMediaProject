package com.example.commonsms.Exceptions;

import com.example.commonsms.Enums.ErrorFields;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BadRequest extends RuntimeException {

    private final ErrorFields errorFields;

    public BadRequest(String message, ErrorFields errorFields) {
        super(message);
        this.errorFields = errorFields;
    }
}
