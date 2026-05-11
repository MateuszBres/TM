package com.example.task_manager_backend.user;

import com.example.task_manager_backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User>findUserByEmail(String email);

    boolean existsByEmail(String email);
}
