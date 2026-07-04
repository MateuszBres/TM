package com.example.task_manager_backend.task;

import com.example.task_manager_backend.task.dto.TaskResponse;

class TaskMapper{

    static TaskResponse toResponse(Task task){
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}
