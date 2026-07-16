package com.example.task_manager_backend.user;

import com.example.task_manager_backend.user.dto.CurrentUserDto;
import com.example.task_manager_backend.user.dto.CustomUserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;

    public User getCurrentUser() {
        return currentUserService.getCurrentUser();
    }

    public CurrentUserDto getCurrentUserDto() {
        return currentUserService.getCurrentUserResponse();
    }


    public CustomUserDetailsDto getUserDetailsByEmail(String email) {
        return userRepository.findUserDetailsByEmail(email)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
    }


    public void updatePassword(String encodedPassword) {
        User user = getCurrentUser();
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

}
