package com.example.task_manager_backend.common.Exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super("TASK_NOT_FOUND");
    }
}
