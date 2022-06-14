package org.filmland.catalog.web.errors;

import lombok.Getter;

@Getter
public enum ErrorCodes {
    LOGIN_FAILED("LOGIN_FAILED", "Login Failed"),
    USER_ALREADY_EXIST("USer already exists","User with given email already exists in system"),
    OPERATION_NOT_PERMITTED("Operation not permitted", "The user is not allowed to perform the operation"),
    UNKNOWN("UNKNOWN", "Something went wrong"),
    CATEGORY_DOESNT_EXIST("Category is not available", "Please check the category name. Either is misspelled or category does not exist anymore"),
    ALREADY_SUBSCRIBED("User is already subscribed","User is already subscribed to category or is already sharing subscription" );

    private String status, message;

    ErrorCodes(String status, String message) {
        this.message = message;
        this.status = status;
    }


}
