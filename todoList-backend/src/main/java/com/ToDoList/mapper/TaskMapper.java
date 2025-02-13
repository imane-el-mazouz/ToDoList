package com.ToDoList.mapper;

import com.ToDoList.dto.TaskDto;
import com.ToDoList.model.Task;
import com.ToDoList.enums.Status;

public class TaskMapper {

    public static Task toEntity(TaskDto dto) {
        return Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus() != null ? dto.getStatus() : Status.PENDING)
                .build();
    }

    public static TaskDto toDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        return dto;
    }
}
