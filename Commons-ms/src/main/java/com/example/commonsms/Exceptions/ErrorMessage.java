package com.example.commonsms.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public enum ErrorMessage {

    POST_NOT_FOUND_WITH_ID_EXCEPTION("Post not found with given ID : %s"),
    POST_NOT_FOUND_EXCEPTION ("Post not found"),
    POST_ALREADY_SHARED("Post with id %s is already shared."),
    USER_NOT_FOUND_EXCEPTION ("User not found wit id :%s"),
    USER_NOT_FOUND_WITH_ID_EXCEPTION ("User not found " ),
    USER_NOT_FOUND_WITH_EMAIL_EXCEPTION("User not found with given Email address"),
    PASSWORD_DOES_NOT_MATCH_EXCEPTION ("Password does not match"),
    ADDRESS_NOT_FOUND_EXCEPTION ("Address not found"),
    ADDRESS_ALREADY_EXIST_EXCEPTION ("Address already exist"),
    UNAUTHORIZED_EXCEPTION("You are not authorized to update this post"),
    EMAIL_SEND_SUCCESS("Email sent successfully to %s."),
    EMAIL_SEND_FAILURE("Failed to send email to %s."),
    ERROR_WHILE_SENDING_EMAIL("Error while sending email to %s."),
    SENDER_NOT_FOUND_EXCEPTION("Sender not found"),
    RECEIVER_NOT_F0UND_EXCEPTION("Receiver not found"),
    MESSAGE_CONTENT_EXCEPTION("Message content cannot be empty"),
    MESSAGE_NOT_FOUND_EXCEPTION("Message not found"),
    MESSAGE_NOT_FOUND_WITH_ID_EXCEPTION("Message not found with given ID : %s"),
    COMMENT_NOT_FOUND_EXCEPTION("Comment not found");



    private final String message;
    public String format(Object... args) {
        return String.format(this.message, args);
    }
}
