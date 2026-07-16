package com.example.task_manager_backend.auth;

import com.example.task_manager_backend.user.UserFacade;
import com.example.task_manager_backend.user.dto.CustomUserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class CustomUserDetailsService implements UserDetailsService {

    private final UserFacade userFacade;

    @Override
    public UserDetails loadUserByUsername(String email) {
        CustomUserDetailsDto user = userFacade.getUserDetailsByEmail(email);
        return org.springframework.security.core.userdetails.User
                .withUsername(user.email())
                .password(user.password())
                .roles(user.role().name())
                .build();
    }
}
