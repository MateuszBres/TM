package com.example.task_manager_backend.task.dto;

import com.example.task_manager_backend.task.model.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDate dueDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
}
