package com.example.task_manager_backend.task;

import com.example.task_manager_backend.common.Exception.UserNotFoundException;
import com.example.task_manager_backend.task.dto.CreateTaskRequest;
import com.example.task_manager_backend.task.dto.PatchTaskRequest;
import com.example.task_manager_backend.task.dto.TaskResponse;
import com.example.task_manager_backend.task.model.Task;
import com.example.task_manager_backend.task.model.TaskStatus;
import com.example.task_manager_backend.user.UserRepository;
import com.example.task_manager_backend.user.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final UserRepository userRepository;

    private User getCurrentUser (){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        String email = auth.getName();

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException());
    }


    @GetMapping
    public ResponseEntity<Page<TaskResponse>> getTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam @Min(0) int page,
            @RequestParam @Min(5) @Max(50) int size,
            @RequestParam(defaultValue = "dueDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ){
        User user = getCurrentUser();
        Sort sort;
        try{
        sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        }catch (IllegalArgumentException e){
            sort = Sort.by(Sort.Direction.DESC);
        }
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<TaskResponse> task = taskService.getTaskPageAndStatus(user, status, pageable);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id){
        User user = getCurrentUser();
        Task task =(taskService.getTaskById(id, user));
        return ResponseEntity.ok(TaskMapper.toResponse(task));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid CreateTaskRequest req){
        User user = getCurrentUser();
        TaskResponse taskResponse = taskService.createTask(req,user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id,
                                                      @RequestBody PatchTaskRequest req){
        User user = getCurrentUser();
        TaskResponse taskResponse = taskService.updateTask(id,req,user);
        return ResponseEntity.ok(taskResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        User user = getCurrentUser();
        taskService.deleteTask(id, user);
        return ResponseEntity.noContent().build();
    }
}
