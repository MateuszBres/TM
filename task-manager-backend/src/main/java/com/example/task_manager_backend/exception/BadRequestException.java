package com.example.task_manager_backend.exception;

public class BadRequestException extends AppException {
    public BadRequestException(String message) {
        super(message);
    }

}
