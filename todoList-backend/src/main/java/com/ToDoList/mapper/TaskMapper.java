package com.ToDoList.mapper;

import com.ToDoList.dto.TaskDto;
import com.ToDoList.model.Task;
import com.ToDoList.enums.Status;

public class TaskMapper {

    public static Task toEntity(TaskDto dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus() != null ? dto.getStatus() : Status.PENDING);
        return task;
    }

    public static TaskDto toDto(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());  // Ensure task is not null here
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        return taskDto;
    }
}
