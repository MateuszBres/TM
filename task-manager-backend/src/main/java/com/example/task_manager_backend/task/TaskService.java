package com.example.task_manager_backend.task;

import com.example.task_manager_backend.common.Exception.TaskNotFoundException;
import com.example.task_manager_backend.user.UserFacade;
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
    private final UserFacade userFacade;

    Page<TaskResponse> getTasks(
            TaskStatus status,
            int page,
            int size,
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
                status,
                pageable
        );
    }

    Page<TaskResponse> getTaskPageAndStatus(TaskStatus status, Pageable pageable) {
        Page<Task> tasks;

        if (status != null) {
            tasks = taskRepository.findAllByUserAndStatus(userFacade.getCurrentUser(), status, pageable);
        } else {
            tasks = taskRepository.findAllByUser(userFacade.getCurrentUser(), pageable);
        }

        return tasks.map(TaskMapper::toResponse);
    }

    Task getTaskById(Long id) {
        return taskRepository.findByIdAndUser(id, userFacade.getCurrentUser())
                .orElseThrow(() -> new TaskNotFoundException("TASK_NOT_FOUND"));
    }


    TaskResponse createTask(CreateTaskRequest req) {
        Task task = new Task();
        task.setTitle(req.title());
        task.setDescription(req.description());
        task.setStatus(TaskStatus.TODO);
        task.setDueDate(req.dueDate());
        task.setUser(userFacade.getCurrentUser());
        task.onCreate();
        taskRepository.save(task);
        return TaskMapper.toResponse(task);

    }

    TaskResponse updateTask(Long id, PatchTaskRequest req) {
        Task task = taskRepository.findByIdAndUser(id, userFacade.getCurrentUser()).orElseThrow(() ->
                new TaskNotFoundException("TASK_NOT_FOUND"));

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

    void deleteTask(Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }
}
