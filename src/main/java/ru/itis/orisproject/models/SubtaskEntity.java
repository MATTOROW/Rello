package ru.itis.orisproject.models;

import java.sql.Date;

public class SubtaskEntity {
    private Long subtaskId;
    private String name;
    private String description;
    private Long taskId;
    private boolean completed;
    private Date endDate;

    public SubtaskEntity(Long subtaskId, String name, String description, Long taskId, boolean completed, Date endDate) {
        this.subtaskId = subtaskId;
        this.name = name;
        this.description = description;
        this.taskId = taskId;
        this.completed = completed;
        this.endDate = endDate;
    }

    public Long getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(Long subtaskId) {
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

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
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
