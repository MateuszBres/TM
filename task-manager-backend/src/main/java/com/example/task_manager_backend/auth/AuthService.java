package com.example.task_manager_backend.auth;

import com.example.task_manager_backend.exception.InvalidCredentialsException;
import com.example.task_manager_backend.exception.PasswordException;
import com.example.task_manager_backend.auth.dto.ChangePasswordRequest;
import com.example.task_manager_backend.auth.dto.LoginRequest;
import com.example.task_manager_backend.user.UserFacade;
import com.example.task_manager_backend.user.dto.CustomUserDetailsDto;
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
        CustomUserDetailsDto user = userFacade.getUserDetailsByEmail(loginRequest.email());


        if (!passwordEncoder.matches(loginRequest.password(), user.password())) {
            throw new InvalidCredentialsException("INVALID_CREDENTIALS");
        }

        return jwtService.generateToken(org.springframework.security.core.userdetails.User
                .withUsername(user.email())
                .password(user.password())
                .roles(user.role().name())
                .build());

    }

    void changePassword(ChangePasswordRequest req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        CustomUserDetailsDto user = userFacade.getUserDetailsByEmail(email);


        if (!passwordEncoder.matches(req.currentPassword(), user.password())) {
            throw new PasswordException("INVALID_CURRENT_PASSWORD");
        }

        if (!req.confirmPassword().equals(req.newPassword())) {
            throw new PasswordException("PASSWORD_NOT_SAME");
        }

        if (passwordEncoder.matches(req.newPassword(), user.password())) {
            throw new PasswordException("PASSWORD_SAME_AS_OLD");
        }

        userFacade.updatePassword(passwordEncoder.encode(req.newPassword()));
    }

}
