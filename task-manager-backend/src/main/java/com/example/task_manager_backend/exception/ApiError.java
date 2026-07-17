package com.example.task_manager_backend.exception;

public record ApiError(
        int status,
        String message

) {
}
