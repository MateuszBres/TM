package com.example.task_manager_backend.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDate dueDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
