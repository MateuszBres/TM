package com.example.task_manager_backend.user.dto;

public record CustomUserDetailsDto(
        String email,
        String password,
        Role role
) {

}
