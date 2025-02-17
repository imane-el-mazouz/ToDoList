package com.ToDoList;

import com.ToDoList.dto.TaskDto;
import com.ToDoList.enums.Status;
import com.ToDoList.exception.TaskNotFoundException;
import com.ToDoList.mapper.TaskMapper;
import com.ToDoList.model.Task;
import com.ToDoList.repository.TaskRepository;
import com.ToDoList.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private TaskDto taskDto;
    private UUID taskId;

    @BeforeEach
    void setUp() {
        taskId = UUID.randomUUID();

        task = new Task();
        task.setId(taskId);
        task.setTitle("Test");
        task.setDescription("Test Desc");
        task.setStatus(Status.PENDING);

        taskDto = new TaskDto();
        taskDto.setId(taskId);
        taskDto.setTitle("Test");
        taskDto.setDescription("Test Desc");
        taskDto.setStatus(Status.PENDING);
    }

//    @Test
//    void testGetAllTasks() {
//        when(taskRepository.findAll()).thenReturn(List.of(task));
//        when(taskMapper.toDto(task)).thenReturn(taskDto);
//        List<TaskDto> result = taskService.getAllTasks();
//        assertEquals(1, result.size());
//        assertEquals(taskDto, result.get(0));
//        verify(taskRepository).findAll();
//    }

//    @Test
//    void testGetTaskByIdFound() {
//        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
//        when(taskMapper.toDto(task)).thenReturn(taskDto);
//        TaskDto result = taskService.getTaskById(taskId);
//        assertNotNull(result);
//        assertEquals(taskDto.getTitle(), result.getTitle());
//        verify(taskRepository).findById(taskId);
//    }

    @Test
    void testGetTaskByIdNotFound() {
        when(taskRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class,
                () -> taskService.getTaskById(UUID.randomUUID()));
    }

//    @Test
//    void testCreateTask() {
//        when(taskMapper.toEntity(taskDto)).thenReturn(task);
//        when(taskRepository.save(any(Task.class))).thenReturn(task); // <-- Correction ici
//        when(taskMapper.toDto(task)).thenReturn(taskDto);
//        TaskDto createdTask = taskService.createTask(taskDto);
//        assertNotNull(createdTask);
//        assertEquals("Test", createdTask.getTitle()); // <-- Correction du texte attendu
//        verify(taskRepository).save(any(Task.class));
//    }

//    @Test
//    void testUpdateTask() {
//        TaskDto updatedDto = new TaskDto();
//        updatedDto.setTitle("Updated");
//        updatedDto.setDescription("New Description");
//        updatedDto.setStatus(Status.COMPLETED);
//
//        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
//        when(taskRepository.save(task)).thenReturn(task);
//        when(taskMapper.toDto(task)).thenReturn(updatedDto);
//
//        TaskDto result = taskService.updateTask(taskId, updatedDto);
//
//        assertEquals("Updated", result.getTitle());
//        assertEquals(Status.COMPLETED, result.getStatus());
//        verify(taskRepository).save(task);
//    }

    @Test
    void testUpdateTaskNotFound() {
        when(taskRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class,
                () -> taskService.updateTask(UUID.randomUUID(), new TaskDto()));
    }

    @Test
    void testDeleteTask() {
        when(taskRepository.existsById(taskId)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(taskId);
        assertDoesNotThrow(() -> taskService.deleteTask(taskId));
        verify(taskRepository).deleteById(taskId);
    }

    @Test
    void testDeleteTaskNotFound() {
        when(taskRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(TaskNotFoundException.class,
                () -> taskService.deleteTask(UUID.randomUUID()));
    }
}