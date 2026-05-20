package com.example.task_manager_backend.user;


import com.example.task_manager_backend.common.Exception.AppException;
import com.example.task_manager_backend.common.Exception.UserAlreadyExists;
import com.example.task_manager_backend.common.Exception.UserNotFoundException;
import com.example.task_manager_backend.user.model.UpdateRoleRequest;
import com.example.task_manager_backend.user.model.User;
import com.example.task_manager_backend.user.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {

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

    public UserResponse updateUser(Long id, UpdateRoleRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("USER_NOT_FOUND"));
        user.setRole(request.role());
        userRepository.save(user);
        return new UserResponse(user.getId(), user.getEmail(), user.getRole(), user.getCreatedAt());
    }

     public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("USER_NOT_FOUND");
        }
        userRepository.deleteById(id);
    }
}
