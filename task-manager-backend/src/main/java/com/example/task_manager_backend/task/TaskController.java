package com.example.task_manager_backend.task;

import com.example.task_manager_backend.common.Exception.SuccessResponse;
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
            @RequestParam(defaultValue = "dueDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return ResponseEntity.ok(
                taskService.getTasks(status, page, size, sortBy, direction)
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        Task task = (taskService.getTaskById(id));
        return ResponseEntity.ok(TaskMapper.toResponse(task));
    }

    @PostMapping
    ResponseEntity<SuccessResponse> createTask(@RequestBody @Valid CreateTaskRequest req) {
        taskService.createTask(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse("TASK_CREATED"));
    }

    @PatchMapping("/{id}")
    ResponseEntity<SuccessResponse> updateTask(@PathVariable Long id,
                                               @RequestBody PatchTaskRequest req) {
        taskService.updateTask(id, req);
        return ResponseEntity.ok(new SuccessResponse("TASK_UPDATED"));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
