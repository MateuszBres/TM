package com.example.task_manager_backend.user;

import com.example.task_manager_backend.exception.UserAlreadyExists;
import com.example.task_manager_backend.exception.UserNotFoundException;
import com.example.task_manager_backend.user.dto.UpdateRoleRequest;
import com.example.task_manager_backend.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class AdminFacade {
    private final UserRepository userRepository;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse
                        (user.getId(), user.getEmail(), user.getRole(), user.getCreatedAt()))
                .toList();
    }

    public UserResponse getUserById(Long id) {
        return userRepository.findById(id).map(user -> new UserResponse
                        (user.getId(), user.getEmail(), user.getRole(), user.getCreatedAt()))
                .orElseThrow(() -> new UserAlreadyExists("USER_ALREADY_EXISTS"));
    }

    public void updateUser(Long id, UpdateRoleRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("USER_NOT_FOUND"));
        user.setRole(request.role());
        userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("USER_NOT_FOUND");
        }
        userRepository.deleteById(id);
    }
}
