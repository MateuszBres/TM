package com.example.task_manager_backend.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiError> handleException(Exception e) {


        if (e instanceof AppException ex) {
            return build(
                    HttpStatus.UNPROCESSABLE_CONTENT,
                    ex.getMessage()
            );
        }
        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage() != null ? e.getMessage() : "INTERNAL_SERVER_ERROR"
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
