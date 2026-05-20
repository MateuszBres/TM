package com.example.task_manager_backend.common.Exception;

public class AppException extends RuntimeException {
    String errorCode;
    public AppException(String errorCode) {
        this.errorCode = errorCode;

    }

    public String errorCode
            () {
        return errorCode;
    }
}
