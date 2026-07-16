package com.example.task_manager_backend.task.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByIdAndUserId(Long id, Long userId);

    Page<Task> findAllByUserId(Long userId, Pageable pageable);

    Page<Task> findAllByUserIdAndStatus(Long userId, TaskStatus status, Pageable pageable);

}
