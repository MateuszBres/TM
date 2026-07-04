package com.example.task_manager_backend.task;

import com.example.task_manager_backend.task.dto.TaskStatus;
import com.example.task_manager_backend.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByIdAndUser(Long id, User user);

    Page<Task> findAllByUser(User user, Pageable pageable);

    Page<Task> findAllByUserAndStatus(User user, TaskStatus status, Pageable pageable);

}
