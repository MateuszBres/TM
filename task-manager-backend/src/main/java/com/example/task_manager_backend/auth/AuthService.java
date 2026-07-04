package com.example.task_manager_backend.auth;

import com.example.task_manager_backend.auth.dto.ChangePasswordRequest;
import com.example.task_manager_backend.auth.dto.LoginRequest;
import com.example.task_manager_backend.common.Exception.InvalidCredentialsException;
import com.example.task_manager_backend.common.Exception.PasswordException;
import com.example.task_manager_backend.user.User;
import com.example.task_manager_backend.user.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
class AuthService {

    private final UserFacade userFacade;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    String login(LoginRequest loginRequest) {
        User user = userFacade.getUserByEmail(loginRequest.email());


        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new InvalidCredentialsException("INVALID_CREDENTIALS");
        }

        return jwtService.generateToken(org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build());

    }

    void changePassword(ChangePasswordRequest req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userFacade.getUserByEmail(email);


        if (!passwordEncoder.matches(req.currentPassword(), user.getPassword())) {
            throw new PasswordException("INVALID_CURRENT_PASSWORD");
        }

        if (!req.confirmPassword().equals(req.newPassword())) {
            throw new PasswordException("PASSWORD_NOT_SAME");
        }

        if (passwordEncoder.matches(req.newPassword(), user.getPassword())) {
            throw new PasswordException("PASSWORD_SAME_AS_OLD");
        }

        userFacade.updatePassword(passwordEncoder.encode(req.newPassword()));
    }

}
