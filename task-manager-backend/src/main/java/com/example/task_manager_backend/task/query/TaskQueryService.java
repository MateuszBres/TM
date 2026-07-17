package com.example.task_manager_backend.task.query;

import com.example.task_manager_backend.exception.TaskNotFoundException;
import com.example.task_manager_backend.task.domain.Task;
import com.example.task_manager_backend.task.domain.TaskMapper;
import com.example.task_manager_backend.task.domain.TaskRepository;
import com.example.task_manager_backend.task.domain.TaskStatus;
import com.example.task_manager_backend.task.dto.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class TaskQueryService {

    private final TaskRepository taskRepository;

    Page<TaskResponse> getTasks(
            TaskStatus status,
            int page,
            int size,
            Long userId,
            String sortBy,
            String direction
    ) {
        Sort sort = buildSort(sortBy, direction);
        Pageable pageable = PageRequest.of(page, size, sort);

        return getTaskPageByStatusAndUserId(userId, status, pageable);
    }

    TaskResponse getTaskById(Long userId, Long id) {
        Task task = taskRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new TaskNotFoundException("TASK_NOT_FOUND"));
        return TaskMapper.toResponse(task);
    }

    private Page<TaskResponse> getTaskPageByStatusAndUserId(Long userId, TaskStatus status, Pageable pageable) {
        Page<Task> tasks;

        if (status != null) {
            tasks = taskRepository.findAllByUserIdAndStatus(userId, status, pageable);
        } else {
            tasks = taskRepository.findAllByUserId(userId, pageable);
        }

        return tasks.map(TaskMapper::toResponse);
    }

    private Sort buildSort(String sortBy, String direction) {
        try {
            return Sort.by(
                    Sort.Direction.fromString(direction),
                    sortBy
            );
        } catch (IllegalArgumentException e) {
            return Sort.by(Sort.Direction.DESC, "dueDate");
        }
    }
}
