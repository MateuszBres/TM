package com.example.task_manager_backend.task.query;

import com.example.task_manager_backend.auth.CurrentUser;
import com.example.task_manager_backend.task.domain.TaskStatus;
import com.example.task_manager_backend.task.dto.TaskResponse;
import com.example.task_manager_backend.user.dto.CurrentUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskQueryController {

    private final TaskQueryService taskService;

    @GetMapping
    ResponseEntity<Page<TaskResponse>> getTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam int page,
            @RequestParam int size,
            @CurrentUser CurrentUserDto user,
            @RequestParam(defaultValue = "dueDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return ResponseEntity.ok(
                taskService.getTasks(status, page, size, user.id(), sortBy, direction)
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<TaskResponse> getTaskByIdd(@CurrentUser CurrentUserDto user, @PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(user.id(), id));
    }
}
