package com.example.task_manager_backend.user;

import com.example.task_manager_backend.auth.dto.ChangePasswordRequest;
import com.example.task_manager_backend.common.Exception.SuccessResponse;
import com.example.task_manager_backend.auth.AuthService;
import com.example.task_manager_backend.auth.dto.LoginRequest;
import com.example.task_manager_backend.auth.dto.LoginResponse;
import com.example.task_manager_backend.auth.dto.RegisterRequest;
import com.example.task_manager_backend.user.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final AuthService authService;
    private final CurrentUserService currentUserService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(currentUserService.getCurrentUserResponse());
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> registerUser(@RequestBody RegisterRequest req){
        userService.addUser(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("USER_CREATED"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest){
        String token = authService.login(loginRequest);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/password")
    public ResponseEntity<SuccessResponse> changePassword(@RequestBody ChangePasswordRequest req){
        authService.changePassword(req);
        return ResponseEntity.ok(new SuccessResponse("PASSWORD_CHANGED"));
    }
}
