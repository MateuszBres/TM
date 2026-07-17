package com.example.task_manager_backend.user;

import com.example.task_manager_backend.exception.UserNotFoundException;
import com.example.task_manager_backend.user.dto.CurrentUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
class CurrentUserService {

    private final UserRepository userRepository;

    User getCurrentUser() {
        Authentication auth = SecurityContextHolder.
                getContext().getAuthentication();
        assert auth != null;
        String email = auth.getName();
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("USER_NOT_FOUND"));
    }

    CurrentUserDto getCurrentUserResponse() {
        User user = getCurrentUser();
        return new CurrentUserDto(user.getId());
    }
}
