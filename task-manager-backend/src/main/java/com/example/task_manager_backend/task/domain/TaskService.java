package com.example.task_manager_backend.task.domain;

import com.example.task_manager_backend.Exception.TaskNotFoundException;
import com.example.task_manager_backend.task.dto.CreateTaskRequest;
import com.example.task_manager_backend.task.dto.PatchTaskRequest;
import com.example.task_manager_backend.task.dto.TaskResponse;
import com.example.task_manager_backend.user.dto.CurrentUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class TaskService {

    private final TaskRepository taskRepository;

    TaskResponse createTask(CreateTaskRequest req, CurrentUserDto user) {
        Task task = new Task();
        task.setTitle(req.title());
        task.setDescription(req.description());
        task.setStatus(TaskStatus.TODO);
        task.setDueDate(req.dueDate());
        task.setUserId(user.id());
        task.onCreate();

        taskRepository.save(task);
        return TaskMapper.toResponse(task);
    }

    TaskResponse updateTask(CurrentUserDto user, Long id, PatchTaskRequest req) {
        Task task = taskRepository.findByIdAndUserId(id, user.id())
                .orElseThrow(() -> new TaskNotFoundException("TASK_NOT_FOUND"));

        if (req.title() != null) {
            task.setTitle(req.title());
        }
        if (req.description() != null) {
            task.setDescription(req.description());
        }
        if (req.status() != null) {
            task.setStatus(req.status());
        }
        if (req.dueDate() != null) {
            task.setDueDate(req.dueDate());
        }

        task.onUpdate();
        taskRepository.save(task);
        return TaskMapper.toResponse(task);
    }

    void deleteTask(CurrentUserDto user, Long id) {
        Task task = taskRepository.findByIdAndUserId(id, user.id())
                .orElseThrow(() -> new TaskNotFoundException("TASK_NOT_FOUND"));
        taskRepository.delete(task);
    }
}
