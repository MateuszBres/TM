package com.example.task_manager_backend.task;

import com.example.task_manager_backend.common.Exception.SuccessResponse;
import com.example.task_manager_backend.task.dto.CreateTaskRequest;
import com.example.task_manager_backend.task.dto.PatchTaskRequest;
import com.example.task_manager_backend.task.dto.TaskResponse;
import com.example.task_manager_backend.task.model.Task;
import com.example.task_manager_backend.task.model.TaskStatus;
import com.example.task_manager_backend.user.CurrentUserService;
import com.example.task_manager_backend.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final CurrentUserService currentUserService;



    @GetMapping
    public ResponseEntity<Page<TaskResponse>> getTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "dueDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ){

        Sort sort;
        try{
        sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        }catch (IllegalArgumentException e){
            sort = Sort.by(Sort.Direction.DESC);
        }
        User user = currentUserService.getCurrentUser();
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<TaskResponse> task = taskService.getTaskPageAndStatus(user, status, pageable);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id){
        User user = currentUserService.getCurrentUser();
        Task task =(taskService.getTaskById(id, user));
        return ResponseEntity.ok(TaskMapper.toResponse(task));
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createTask(@RequestBody @Valid CreateTaskRequest req){
        User user = currentUserService.getCurrentUser();
        taskService.createTask(req,user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse("TASK_CREATED"));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateTask(@PathVariable Long id,
                                                      @RequestBody PatchTaskRequest req){
        User user = currentUserService.getCurrentUser();
        taskService.updateTask(id,req,user);
        return ResponseEntity.ok(new SuccessResponse("TASK_UPDATED"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        User user = currentUserService.getCurrentUser();
        taskService.deleteTask(id, user);
        return ResponseEntity.noContent().build();
    }
}
