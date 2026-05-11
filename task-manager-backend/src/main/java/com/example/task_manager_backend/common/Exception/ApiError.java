package com.example.task_manager_backend.common.Exception;

public record ApiError(
        int Status,
        String message

) {
}
