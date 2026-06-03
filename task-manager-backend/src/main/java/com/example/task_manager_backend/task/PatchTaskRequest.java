package com.example.task_manager_backend.task.dto;

import com.example.task_manager_backend.task.model.TaskStatus;

import java.time.LocalDate;

public record PatchTaskRequest(
        String title,
        String description,
        TaskStatus status,
        LocalDate dueDate
) {

}
