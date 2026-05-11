package com.example.task_manager_backend.common.Exception;

public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException(){
        super("Invalid credentials");
    }

    public InvalidCredentialsException(String message){
        super(message);
    }
}
