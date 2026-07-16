package com.example.task_manager_backend.Exception;

public class BadRequestException extends AppException {
    public BadRequestException(String message) {
        super(message);
    }

}
