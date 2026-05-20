package com.example.task_manager_backend.user;

import com.example.task_manager_backend.common.Exception.AppException;
import com.example.task_manager_backend.common.Exception.BadRequestException;
import com.example.task_manager_backend.common.Exception.UserAlreadyExists;
import com.example.task_manager_backend.user.model.Role;
import com.example.task_manager_backend.user.model.User;
import com.example.task_manager_backend.auth.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User addUser(RegisterRequest request){
        if(userRepository.existsByEmail(request.email())) {
                throw new AppException("USER_ALREADY_EXISTS");
        }

        User user = new User();

        String hashedPassword = passwordEncoder.encode(request.password());
        user.setEmail(request.email());
        user.setPassword(hashedPassword);
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

}
