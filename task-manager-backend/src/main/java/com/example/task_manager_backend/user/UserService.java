package com.example.task_manager_backend.user;

import com.example.task_manager_backend.exception.UserAlreadyExists;
import com.example.task_manager_backend.user.dto.RegisterRequest;
import com.example.task_manager_backend.user.dto.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    User addUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExists("USER_ALREADY_EXISTS");
        }

        User user = new User();

        String hashedPassword = passwordEncoder.encode(request.password());
        user.setEmail(request.email());
        user.setPassword(hashedPassword);
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

}
