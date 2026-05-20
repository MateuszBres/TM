package com.example.task_manager_backend.common.Exception;

public class TaskNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public TaskNotFoundException(String message) {
        super(message);
        this.errorCode = ErrorCode.TASK_NOT_FOUND;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
