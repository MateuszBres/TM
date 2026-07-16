package com.example.task_manager_backend.task.dto;

import com.example.task_manager_backend.task.domain.TaskStatus;

import java.time.LocalDate;

public record PatchTaskRequest(
        String title,
        String description,
        TaskStatus status,
        LocalDate dueDate
) {

}
