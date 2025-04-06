package com.learning.tasktrackercli.DAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.learning.tasktrackercli.Entity.Task;
import com.learning.tasktrackercli.Enums.Status;
import com.learning.tasktrackercli.Interfaces.ITaskRepo;

public class TaskRepo implements ITaskRepo<Task> {

    private final ObjectMapper mapper;
    private final File file;
    private List<Task> taskList;

    public TaskRepo() {
        this.taskList = new ArrayList<>();
        this.mapper = new ObjectMapper();

        // used for proper serialization & deserialization of time related fileds in
        // entities.
        this.mapper.registerModule(new JavaTimeModule());
        this.file = new File("taskTracker.json");
        // creates the file if it is not created.
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        // load data from tracker json to taskList
        loadData();

    }

    private void loadData() {
        // load data from json file to task List.

        // new TypeReference<List<Task>>(){} -> This tells Jackson how to map the JSON
        // data in the file to a specific type—in this case, a list of Task objects.
        // TypeReference is needed for handling generic types like list<Task> because
        // Java's type system erases generic type information at runtime.
        try {
            this.taskList = this.mapper.readValue(this.file, new TypeReference<List<Task>>() {
            });
        } catch (Exception e) {
            System.out.println("Exception occured, Error:- " + e.getMessage());
        }
    }

    private void saveData() {
        try {
            mapper.writeValue(file, taskList);
        } catch (Exception e) {
            System.out.println("Exception occured, Error:- " + e.getMessage());
        }

    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public void add(Task task) {
        taskList.add(task);
        // TODO save to the file.
        saveData();

    }

    @Override
    public void update(Task task) {
        taskList.stream().filter((t) -> {
            return t.getTaskId() == task.getTaskId();
        }).forEach((filteredTask) -> {
            filteredTask.setDescription(task.getDescription());
            filteredTask.setStatus(task.getStatus());
            filteredTask.setUpdatedAt(task.getUpdatedAt());
        });

        // TODO save to the file.
        saveData();
    }

    @Override
    public boolean delete(int taskId) {
        boolean status = taskList.removeIf((t) -> t.getTaskId() == taskId);

        if (status) {
            // TODO remove from the json file as well. save the updated tasklist to json
            // file.
            saveData();
        }
        return status;

    }

    @Override
    public Task getTaskById(int taskId) {
        return taskList.stream().filter((t) -> t.getTaskId() == taskId).findFirst().orElse(null);
    }

    @Override
    public List<Task> getAllTasks() {
        return this.taskList;
    }

    @Override
    public List<Task> getTasksByStatus(Status status) {
        return taskList.stream().filter((t) -> t.getStatus().equals(status)).toList();
    }

}
