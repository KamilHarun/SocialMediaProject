package com.example.commonsms.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public enum ErrorMessage {
    USER_NOT_FOUND_EXCEPTION ("User not found wit id :%s"),
    USER_NOT_FOUND_WITH_ID_EXCEPTION ("User not found " ),
    USER_NOT_FOUND_WITH_EMAIL_EXCEPTION("User not found with given Email address"),
    PASSWORD_DOES_NOT_MATCH_EXCEPTION ("Password does not match"),
    ADDRESS_NOT_FOUND_EXCEPTION ("Address not found"),
    ADDRESS_ALREADY_EXIST_EXCEPTION ("Address already exist");


    private final String message;

}
