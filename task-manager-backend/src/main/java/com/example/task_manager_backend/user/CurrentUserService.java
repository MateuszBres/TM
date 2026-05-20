package com.example.task_manager_backend.user;

import com.example.task_manager_backend.common.Exception.UserNotFoundException;
import com.example.task_manager_backend.user.model.User;
import com.example.task_manager_backend.user.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CurrentUserService {

    private final UserRepository userRepository;

    public User getCurrentUser(){
        Authentication auth = SecurityContextHolder.
                getContext().getAuthentication();
        assert auth != null;
        String email = auth.getName();
        return userRepository.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }
    public UserResponse getCurrentUserResponse(){
        User user = getCurrentUser();
        return new UserResponse(user.getId(), user.getEmail(), user.getRole(), user.getCreatedAt());
    }
}
