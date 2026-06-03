package com.example.task_manager_backend.task;

import java.time.LocalDate;

record PatchTaskRequest(
        String title,
        String description,
        TaskStatus status,
        LocalDate dueDate
) {

}
