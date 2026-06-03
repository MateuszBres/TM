package com.example.task_manager_backend.user;

import java.time.LocalDate;

record UserResponse(
        Long id,
        String email,
        Role role,
        LocalDate createdAt
) {
}
