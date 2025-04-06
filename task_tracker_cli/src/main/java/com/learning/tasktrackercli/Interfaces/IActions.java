package com.learning.tasktrackercli.Interfaces;

import java.util.List;

import com.learning.tasktrackercli.Entity.Task;
import com.learning.tasktrackercli.Enums.Status;

public interface IActions<Task> {
    void add(Task task);
    void update(Task task);
    boolean delete(int taskId);
    Task getTaskById(int taskId);
    List<Task> getAllTasks();
    List<Task> getTasksByStatus(Status status);
}
