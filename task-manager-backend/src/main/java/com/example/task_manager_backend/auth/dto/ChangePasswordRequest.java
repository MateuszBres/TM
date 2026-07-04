package com.example.task_manager_backend.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        String currentPassword,
        @Size(min = 8, message = "Password must be at least 8 characters")
        @NotBlank
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[\\d])(?=.*[!@#$%^&*?].+$)",
                message = "Password must contains uppercase, number and special characters")
        String newPassword,
        String confirmPassword
) {
}
