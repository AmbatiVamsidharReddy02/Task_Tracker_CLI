package com.learning.tasktrackercli.Services;

import java.util.List;

import com.learning.tasktrackercli.DAO.TaskRepo;
import com.learning.tasktrackercli.Entity.Task;
import com.learning.tasktrackercli.Enums.Status;
import com.learning.tasktrackercli.Interfaces.IActions;

public class TaskManager implements IActions<Task> {

    private final TaskRepo taskRepo;

    public TaskManager() {
        this.taskRepo = new TaskRepo();
    }

    @Override
    public void add(Task task) {
        taskRepo.add(task);
    }

    @Override
    public void update(Task task) {
        taskRepo.update(task);
    }

    @Override
    public boolean delete(int taskId) {
        return taskRepo.delete(taskId);
    }

    @Override
    public Task getTaskById(int taskId) {
        return taskRepo.getTaskById(taskId);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepo.getAllTasks();
    }

    @Override
    public List<Task> getTasksByStatus(Status status) {
        return taskRepo.getTasksByStatus(status);
    }

}
