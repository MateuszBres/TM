package com.example.task_manager_backend.common.Exception;

public enum ErrorCode {
    USER_NOT_FOUND("USER_NOT_FOUND"),
    TASK_NOT_FOUND("TASK_NOT_FOUND"),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS"),
    BAD_REQUEST("BAD_REQUEST"),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS"),
    VALIDATION_ERROR("VALIDATION_ERROR"),
    INTERNAL_ERROR("INTERNAL_ERROR"),
    PASSWORD_NOT_THE_SAME("PASSWORD_NOT_THE_SAME");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

