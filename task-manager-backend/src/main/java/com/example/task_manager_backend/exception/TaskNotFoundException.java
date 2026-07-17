package com.example.task_manager_backend.exception;

public class TaskNotFoundException extends AppException {

    public TaskNotFoundException(String message) {
        super(message);
    }

}
