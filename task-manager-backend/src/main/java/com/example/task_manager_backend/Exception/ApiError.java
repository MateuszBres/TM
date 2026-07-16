package com.example.task_manager_backend.Exception;

public record ApiError(
        int status,
        String message

) {
}
