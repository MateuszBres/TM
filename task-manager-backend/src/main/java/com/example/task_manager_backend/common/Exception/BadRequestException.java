package com.example.task_manager_backend.common.Exception;

public class BadRequestException extends RuntimeException {
    private  String errorCode;


    public BadRequestException(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
