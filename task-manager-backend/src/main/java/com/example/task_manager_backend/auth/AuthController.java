package com.example.task_manager_backend.auth;

import com.example.task_manager_backend.auth.dto.ChangePasswordRequest;
import com.example.task_manager_backend.auth.dto.LoginRequest;
import com.example.task_manager_backend.auth.dto.LoginResponse;
import com.example.task_manager_backend.common.Exception.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/password")
    ResponseEntity<SuccessResponse> changePassword(@RequestBody ChangePasswordRequest req) {
        authService.changePassword(req);
        return ResponseEntity.ok(new SuccessResponse("PASSWORD_CHANGED"));
    }
}
