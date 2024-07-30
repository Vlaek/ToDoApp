package com.example.todo.service;

import com.example.todo.entity.TaskEntity;
import com.example.todo.exception.TaskNotFoundException;
import com.example.todo.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;

    public List<TaskEntity> getAll() {
        return taskRepository.findAll();
    }

    public TaskEntity create(TaskEntity taskEntity) {
        TaskEntity newTaskEntity = TaskEntity.builder()
                .title(taskEntity.getTitle())
                .description(taskEntity.getDescription())
                .completed(taskEntity.isCompleted())
                .build();
        return taskRepository.save(newTaskEntity);
    }

    public TaskEntity update(Long id, TaskEntity updatedTask) throws TaskNotFoundException {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена"));

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setCompleted(updatedTask.isCompleted());

        return taskRepository.save(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
