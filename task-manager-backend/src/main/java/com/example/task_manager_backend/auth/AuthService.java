package com.example.task_manager_backend.auth;

import com.example.task_manager_backend.auth.dto.ChangePasswordRequest;
import com.example.task_manager_backend.common.Exception.*;
import com.example.task_manager_backend.auth.security.JwtService;
import com.example.task_manager_backend.auth.dto.LoginRequest;
import com.example.task_manager_backend.user.model.User;
import com.example.task_manager_backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public String login(LoginRequest loginRequest) {
        User user = userRepository.findUserByEmail(loginRequest.email())
                .orElseThrow(() -> new UserNotFoundException("INVALID_CREDENTIALS"));


        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new InvalidCredentialsException("INVALID_CREDENTIALS");
        }

        return jwtService.generateToken(org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build());

    }

    public void changePassword(ChangePasswordRequest req){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("USER_NOT_FOUND"));


        if(!passwordEncoder.matches(req.currentPassword(), user.getPassword())){
            throw new PasswordException("INVALID_CURRENT_PASSWORD");
        }

        if(!req.confirmPassword().equals(req.newPassword())){
            throw new PasswordException("PASSWORD_NOT_SAME");
        }

        if(passwordEncoder.matches(req.newPassword(), user.getPassword())){
            throw new PasswordException("PASSWORD_SAME_AS_OLD");
        }

        user.setPassword(passwordEncoder.encode(req.newPassword()));
        userRepository.save(user);
    }

}
