package com.example.task_manager_backend.user.dto;

import com.example.task_manager_backend.user.Role;

public record UpdateRoleRequest(
        Role role
) {
}
