package com.example.task_manager_backend.task.domain;

import com.example.task_manager_backend.Exception.SuccessResponse;
import com.example.task_manager_backend.auth.CurrentUser;
import com.example.task_manager_backend.task.dto.CreateTaskRequest;
import com.example.task_manager_backend.task.dto.PatchTaskRequest;
import com.example.task_manager_backend.user.dto.CurrentUserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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


    @PostMapping
    ResponseEntity<SuccessResponse> createTask(@RequestBody @Valid CreateTaskRequest req, @CurrentUser CurrentUserDto user) {
        taskService.createTask(req, user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse("TASK_CREATED"));
    }

    @PatchMapping("/{id}")
    ResponseEntity<SuccessResponse> updateTask(@CurrentUser CurrentUserDto user, @PathVariable Long id,
                                               @RequestBody PatchTaskRequest req) {
        taskService.updateTask(user, id, req);
        return ResponseEntity.ok(new SuccessResponse("TASK_UPDATED"));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteTask(@CurrentUser CurrentUserDto user, @PathVariable Long id) {
        taskService.deleteTask(user, id);
        return ResponseEntity.noContent().build();
    }
}
