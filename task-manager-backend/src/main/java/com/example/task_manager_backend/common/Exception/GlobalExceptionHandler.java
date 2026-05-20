package com.example.task_manager_backend.common.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import org.springframework.validation.FieldError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e) {


//        if (e instanceof MethodArgumentNotValidException validationEx) {
//            String message = validationEx.getBindingResult()
//                    .getFieldErrors()
//                    .stream()
//                    .map(FieldError::getDefaultMessage)
//                    .collect(Collectors.joining(", "));
//
//            return build(
//                    HttpStatus.BAD_REQUEST,
//                    message,
//                    ErrorCode.VALIDATION_ERROR.getCode()
//            );
//        }
//
//        if (e instanceof InvalidCredentialsException ex) {
//            return build(
//                    HttpStatus.UNAUTHORIZED,
//                    ex.getMessage() != null ? ex.getMessage() : "Invalid credentials",
//                    ex.getErrorCode().getCode()
//            );
//        }
//
//        if (e instanceof UserNotFoundException ex) {
//            return build(
//                    HttpStatus.NOT_FOUND,
//                    e.getMessage(),
//                    ex.getErrorCode().getCode()
//            );
//        }
//
//        if (e instanceof TaskNotFoundException ex) {
//            return build(
//                    HttpStatus.NOT_FOUND,
//                    e.getMessage(),
//                    ex.getErrorCode().getCode()
//            );
//        }
//
//        if(e instanceof UserAlreadyExists ex){
//            return build(
//                    HttpStatus.CONFLICT,
//                    e.getMessage(),
//                    "BAD_REQUEST"
//            );
//        }
//
//        if (e instanceof BadRequestException ex) {
//            return build(
//                    HttpStatus.BAD_REQUEST,
//                    e.getMessage(),
//                    ex.getErrorCode()
//            );
//        }
//
//        return build(
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                "Coś poszło nie tak",
//                ErrorCode.INTERNAL_ERROR.getCode()
//        );
        if(e instanceof AppException ex) {
            return build(
                    HttpStatus.NOT_FOUND,
                    ex.errorCode()
            );
        }
        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_ERROR.getCode()
        );
    }

    private ResponseEntity<ApiError> build(
            HttpStatus status,
            String errorCode
    ) {
        return ResponseEntity.status(status)
                .body(new ApiError(status.value(), errorCode));
    }
}