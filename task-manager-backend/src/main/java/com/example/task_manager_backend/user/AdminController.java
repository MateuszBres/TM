package com.example.task_manager_backend.user;

import com.example.task_manager_backend.common.Exception.SuccessResponse;
import com.example.task_manager_backend.user.model.UpdateRoleRequest;
import com.example.task_manager_backend.user.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getUserById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateStatus(@PathVariable Long id, @RequestBody UpdateRoleRequest request) {
        adminService.updateUser(id, request);
        return ResponseEntity.ok(new SuccessResponse("USER_UPDATED"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        adminService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
