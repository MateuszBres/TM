package com.example.task_manager_backend.user;

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


    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
    }


    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
    }

    public void updatePassword(String encodedPassword) {
        User user = getCurrentUser();
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

}
