package com.example.task_manager_backend.task.domain;

import com.example.task_manager_backend.exception.TaskNotFoundException;
import com.example.task_manager_backend.task.dto.CreateTaskRequest;
import com.example.task_manager_backend.task.dto.PatchTaskRequest;
import com.example.task_manager_backend.task.dto.TaskResponse;
import com.example.task_manager_backend.user.dto.CurrentUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private CurrentUserDto currentUser;
    private Task task;
    private CreateTaskRequest createRequest;
    private PatchTaskRequest patchRequest;

    @BeforeEach
    void setUp() {

        currentUser = new CurrentUserDto(1L);

        task = new Task();
        task.setId(1L);
        task.setTitle("Old title");
        task.setDescription("Old description");
        task.setStatus(TaskStatus.TODO);
        task.setDueDate(LocalDate.now().plusDays(1));
        task.setUserId(1L);

        createRequest = new CreateTaskRequest(
                "New task",
                "Task description",
                LocalDate.now().plusDays(2)
        );

        patchRequest = new PatchTaskRequest(
                "Updated title",
                "Updated description",
                TaskStatus.DONE,
                LocalDate.now().plusDays(5)
        );
    }

    @Test
    void shouldCreateTask() {

        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TaskResponse response = taskService.createTask(createRequest, currentUser);

        ArgumentCaptor<Task> captor =
                ArgumentCaptor.forClass(Task.class);

        verify(taskRepository).save(captor.capture());

        Task savedTask = captor.getValue();

        assertNotNull(response);

        assertEquals("New task", savedTask.getTitle());
        assertEquals("Task description", savedTask.getDescription());
        assertEquals(TaskStatus.TODO, savedTask.getStatus());
        assertEquals(1L, savedTask.getUserId());
        assertNotNull(savedTask.getCreatedAt());
    }

    @Test
    void shouldUpdateTask() {

        when(taskRepository.findByIdAndUserId(1L, 1L))
                .thenReturn(Optional.of(task));

        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TaskResponse response =
                taskService.updateTask(currentUser, 1L, patchRequest);

        assertNotNull(response);

        assertEquals("Updated title", task.getTitle());
        assertEquals("Updated description", task.getDescription());
        assertEquals(TaskStatus.DONE, task.getStatus());
        assertEquals(LocalDate.now().plusDays(5), task.getDueDate());
        assertNotNull(task.getUpdatedAt());

        verify(taskRepository).save(task);
    }

    @Test
    void shouldDeleteTask() {

        when(taskRepository.findByIdAndUserId(1L, 1L))
                .thenReturn(Optional.of(task));

        taskService.deleteTask(currentUser, 1L);

        verify(taskRepository).delete(task);
    }

    @Test
    void shouldThrowTaskNotFoundExceptionWhenTaskDoesNotExist() {

        when(taskRepository.findByIdAndUserId(999L, 1L))
                .thenReturn(Optional.empty());

        assertThrows(
                TaskNotFoundException.class,
                () -> taskService.updateTask(currentUser, 999L, patchRequest)
        );

        verify(taskRepository, never()).save(any(Task.class));
    }
}