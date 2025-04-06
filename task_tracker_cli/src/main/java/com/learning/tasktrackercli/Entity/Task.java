package com.learning.tasktrackercli.Entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.learning.tasktrackercli.Enums.Status;

public class Task {
    public static int id = 1;
    private int taskId;
    private String description;
    private Status status;
    private final LocalDate createdAt;
    private LocalDate updatedAt;


    // for deserialization
    @JsonCreator
    public Task(@JsonProperty("taskId") int taskId,
            @JsonProperty("description") String description,
            @JsonProperty("status") Status status,
            @JsonProperty("created_at") LocalDate createdAt,
            @JsonProperty("updated_at") LocalDate updatedAt) {
        id = taskId;
        this.taskId = taskId;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Task(String description, Status status) {
        this.taskId = ++id;
        this.description = description;
        this.status = status;
        this.createdAt = LocalDate.now();
        this.updatedAt = null;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Task.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Task [taskId=" + taskId + ", description=" + description + ", status=" + status + ", createdAt="
                + createdAt + ", updatedAt=" + updatedAt + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + taskId;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Task other = (Task) obj;
        if (taskId != other.taskId)
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (status != other.status)
            return false;
        if (createdAt == null) {
            if (other.createdAt != null)
                return false;
        } else if (!createdAt.equals(other.createdAt))
            return false;
        if (updatedAt == null) {
            if (other.updatedAt != null)
                return false;
        } else if (!updatedAt.equals(other.updatedAt))
            return false;
        return true;
    }

}
