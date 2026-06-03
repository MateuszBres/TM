package com.example.task_manager_backend.user;

import com.example.task_manager_backend.common.Exception.SuccessResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
class UserController {

    private final UserService userService;
    private final CurrentUserService currentUserService;

    @GetMapping("/me")
    ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(currentUserService.getCurrentUserResponse());
    }

    @PostMapping("/register")
    ResponseEntity<SuccessResponse> registerUser(@RequestBody RegisterRequest req) {
        userService.addUser(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("USER_CREATED"));
    }


}
