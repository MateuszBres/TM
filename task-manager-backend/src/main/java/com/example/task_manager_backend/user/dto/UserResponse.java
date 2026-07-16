package com.example.task_manager_backend.user.dto;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String email,
        Role role,
        LocalDate createdAt
) {
}
