package com.example.task_manager_backend.task;

import com.example.task_manager_backend.common.Exception.TaskNotFoundException;
import com.example.task_manager_backend.task.dto.CreateTaskRequest;
import com.example.task_manager_backend.task.dto.PatchTaskRequest;
import com.example.task_manager_backend.task.dto.TaskResponse;
import com.example.task_manager_backend.task.model.Task;
import com.example.task_manager_backend.task.model.TaskStatus;
import com.example.task_manager_backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<TaskResponse> getAllTask(User user){
         return taskRepository.findAllByUser(user)
                 .stream()
                 .map(TaskMapper::toResponse)
                 .toList();
    }

    public Page<TaskResponse> getTaskPageAndStatus(User user, TaskStatus status, Pageable pageable){
        Page<Task> tasks;

        if(status !=null){
            tasks = taskRepository.findAllByUserAndStatus(user,status,pageable);
        }else{
            tasks = taskRepository.findAllByUser(user,pageable);
        }

        return tasks.map(TaskMapper::toResponse);
    }

    public Task getTaskById (Long id, User user){
        return taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new TaskNotFoundException("Nie znaleziono zadania"));
    }


    public TaskResponse createTask(CreateTaskRequest req, User user){
        Task task = new Task();
        task.setTitle(req.title());
        task.setDescription(req.description());
        task.setStatus(TaskStatus.TODO);
        task.setDueDate(req.dueDate());
        task.setUser(user);
        task.onCreate();
        taskRepository.save(task);
        return TaskMapper.toResponse(task);

    }

    public TaskResponse updateTask(Long id, PatchTaskRequest req, User user ){

        Task task = taskRepository.findByIdAndUser(id,user).orElseThrow(()->
                new TaskNotFoundException("Task not found"));

        if(req.title() != null){
            task.setTitle(req.title());
        }

        if(req.description() != null){
            task.setDescription(req.description());
        }

        if(req.status() != null){
            task.setStatus(req.status());
        }

        if(req.dueDate() != null){
            task.setDueDate(req.dueDate());
        }

        task.onUpdate();
        Task updatedTask = taskRepository.save(task);
        return TaskMapper.toResponse(updatedTask);

    }

    public void deleteTask (Long id, User user){
        Task task = getTaskById(id, user);
        taskRepository.delete(task);
    }
}
