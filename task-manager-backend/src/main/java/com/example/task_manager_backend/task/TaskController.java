package com.example.task_manager_backend.task;

import com.example.task_manager_backend.auth.CurrentUser;
import com.example.task_manager_backend.common.Exception.SuccessResponse;
import com.example.task_manager_backend.task.dto.CreateTaskRequest;
import com.example.task_manager_backend.task.dto.PatchTaskRequest;
import com.example.task_manager_backend.task.dto.TaskResponse;
import com.example.task_manager_backend.task.dto.TaskStatus;
import com.example.task_manager_backend.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
class TaskController {
    private final TaskService taskService;

    @GetMapping
    ResponseEntity<Page<TaskResponse>> getTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam int page,
            @RequestParam int size,
            @CurrentUser User user,
            @RequestParam(defaultValue = "dueDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return ResponseEntity.ok(
                taskService.getTasks(status, page, size,user, sortBy, direction)
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<TaskResponse> getTaskById(@CurrentUser User user, @PathVariable Long id) {
        Task task = (taskService.getTaskById(user,id));
        return ResponseEntity.ok(TaskMapper.toResponse(task));
    }

    @PostMapping
    ResponseEntity<SuccessResponse> createTask(@RequestBody @Valid CreateTaskRequest req, @CurrentUser User user) {
        taskService.createTask(req, user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse("TASK_CREATED"));
    }

    @PatchMapping("/{id}")
    ResponseEntity<SuccessResponse> updateTask(@CurrentUser User user, @PathVariable Long id,
                                               @RequestBody PatchTaskRequest req) {
        taskService.updateTask(user,id, req);
        return ResponseEntity.ok(new SuccessResponse("TASK_UPDATED"));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTask(@CurrentUser User user, @PathVariable Long id) {
        taskService.deleteTask(user,id);
        return ResponseEntity.noContent().build();
    }
}
