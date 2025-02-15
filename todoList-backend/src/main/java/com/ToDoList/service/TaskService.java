package com.ToDoList.service;

import com.ToDoList.dto.TaskDto;
import com.ToDoList.enums.Status;
import com.ToDoList.exception.TaskNotFoundException;
import com.ToDoList.mapper.TaskMapper;
import com.ToDoList.model.Task;
import com.ToDoList.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskMapper::toDto)
                .collect(Collectors.toList());
    }

    public TaskDto getTaskById(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return TaskMapper.toDto(task);
    }

    public TaskDto createTask(TaskDto taskDto) {
        Task task = TaskMapper.toEntity(taskDto);
        taskRepository.save(task);
        return TaskMapper.toDto(task);
    }

    public TaskDto updateTask(UUID id, @org.jetbrains.annotations.NotNull TaskDto updatedTaskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setTitle(updatedTaskDto.getTitle());
        task.setDescription(updatedTaskDto.getDescription());
        task.setStatus(updatedTaskDto.getStatus());

        taskRepository.save(task);
        return TaskMapper.toDto(task);
    }

    public void deleteTask(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }

    public TaskDto markTaskAsCompleted(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setStatus(Status.COMPLETED);
        taskRepository.save(task);

        return TaskMapper.toDto(task);
    }
}
