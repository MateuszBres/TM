package com.example.task_manager_backend.common.Exception;

public class InvalidCredentialsException extends RuntimeException{

    private final ErrorCode errorCode;

    public InvalidCredentialsException(){
        super("Invalid credentials");
        this.errorCode = ErrorCode.INVALID_CREDENTIALS;
    }

    public InvalidCredentialsException(String message){
        super(message);
        this.errorCode = ErrorCode.INVALID_CREDENTIALS;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
