package com.ToDoList.repository;

import com.ToDoList.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findById(UUID taskId);

    boolean existsById(UUID id);

    void deleteById(UUID id);
}
