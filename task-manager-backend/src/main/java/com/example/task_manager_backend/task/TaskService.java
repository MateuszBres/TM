package com.example.task_manager_backend.task;

import com.example.task_manager_backend.auth.CurrentUser;
import com.example.task_manager_backend.common.Exception.TaskNotFoundException;
import com.example.task_manager_backend.task.dto.CreateTaskRequest;
import com.example.task_manager_backend.task.dto.PatchTaskRequest;
import com.example.task_manager_backend.task.dto.TaskResponse;
import com.example.task_manager_backend.task.dto.TaskStatus;
import com.example.task_manager_backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class TaskService {

    private final TaskRepository taskRepository;

    Page<TaskResponse> getTasks(
            TaskStatus status,
            int page,
            int size,
            User user,
            String sortBy,
            String direction
    ) {
        Sort sort;

        try {
            sort = Sort.by(
                    Sort.Direction.fromString(direction),
                    sortBy
            );
        } catch (IllegalArgumentException e) {
            sort = Sort.by(Sort.Direction.DESC, "dueDate");
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );

        return getTaskPageAndStatus(
                user,
                status,
                pageable
        );
    }

    Page<TaskResponse> getTaskPageAndStatus(User user, TaskStatus status, Pageable pageable) {
        Page<Task> tasks;

        if (status != null) {
            tasks = taskRepository.findAllByUserAndStatus(user,status, pageable);
        } else {
            tasks = taskRepository.findAllByUser(user,pageable);
        }

        return tasks.map(TaskMapper::toResponse);
    }

    Task getTaskById(User user,Long id) {
        return taskRepository.findByIdAndUser(id,user)
                .orElseThrow(() -> new TaskNotFoundException("TASK_NOT_FOUND"));
    }


    TaskResponse createTask(CreateTaskRequest req, User user) {
        Task task = new Task();
        task.setTitle(req.title());
        task.setDescription(req.description());
        task.setStatus(TaskStatus.TODO);
        task.setDueDate(req.dueDate());
        task.setUser(user);
        task.onCreate();
        taskRepository.save(task);
        return TaskMapper.toResponse(task);

    }

    TaskResponse updateTask(User user,Long id, PatchTaskRequest req) {
        Task task = taskRepository.findByIdAndUser(id,user)
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
        Task updatedTask = taskRepository.save(task);
        return TaskMapper.toResponse(updatedTask);

    }

    void deleteTask(User user,Long id) {
        Task task = getTaskById(user,id);
        taskRepository.delete(task);
    }
}
