package ru.itis.orisproject.models;

import java.sql.Date;
import java.util.UUID;

public class SubtaskEntity {
    private UUID subtaskId;
    private String name;
    private String description;
    private UUID taskId;
    private boolean completed;
    private Date endDate;

    public SubtaskEntity(UUID subtaskId, String name, String description, UUID taskId, boolean completed, Date endDate) {
        this.subtaskId = subtaskId;
        this.name = name;
        this.description = description;
        this.taskId = taskId;
        this.completed = completed;
        this.endDate = endDate;
    }

    public UUID getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(UUID subtaskId) {
        this.subtaskId = subtaskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
