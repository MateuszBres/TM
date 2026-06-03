package com.example.task_manager_backend.task.dto;

import com.example.task_manager_backend.task.model.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateTaskRequest(
        @NotBlank
        @Size(min = 3, message = "tytul musi miec co najmniej 3 znaki")
        String title,
        @Size(max = 500, message = "maksymalna możliwa ilość znaków to 500")
        String description,
        @NotNull
        @FutureOrPresent(message = "data nie może byc z przeszłości")
        LocalDate dueDate
){}
