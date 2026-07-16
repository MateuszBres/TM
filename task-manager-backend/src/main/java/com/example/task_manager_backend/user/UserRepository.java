package com.example.task_manager_backend.user;

import com.example.task_manager_backend.user.dto.CustomUserDetailsDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    Optional<CustomUserDetailsDto> findUserDetailsByEmail(String email);

    boolean existsByEmail(String email);
}
