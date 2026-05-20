package com.example.task_manager_backend.common.Exception;

public class UserNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public UserNotFoundException() {
        super("USER_NOT_FOUND");
        this.errorCode = ErrorCode.USER_NOT_FOUND;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
