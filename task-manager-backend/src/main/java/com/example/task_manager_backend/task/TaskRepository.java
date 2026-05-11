package com.example.task_manager_backend.task;

import com.example.task_manager_backend.task.model.Task;
import com.example.task_manager_backend.task.model.TaskStatus;
import com.example.task_manager_backend.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findAllByUser(User user);
    Optional <Task> findByIdAndUser(Long id, User user);
    Page<Task> findAllByUser(User user,Pageable pageable);
    Page<Task> findAllByUserAndStatus(User user, TaskStatus status, Pageable pageable);

}
