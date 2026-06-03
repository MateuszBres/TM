package com.example.task_manager_backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserFacade {

    private final CurrentUserService  currentUserService;

    public User getCurrentUser(){
        return currentUserService.getCurrentUser();
    }

}
