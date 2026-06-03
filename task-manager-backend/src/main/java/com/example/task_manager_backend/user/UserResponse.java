package com.example.task_manager_backend.user.model;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String email,
        Role role,
        LocalDate createdAt
) {
}
