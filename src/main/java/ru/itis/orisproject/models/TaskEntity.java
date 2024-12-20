package ru.itis.orisproject.models;

import java.sql.Date;
import java.util.List;

public class TaskEntity {
    private Long taskId;
    private String name;
    private String description;
    private Long project_id;
    private String status;
    private Date startDate;
    private Date endDate;
    private List<SubtaskEntity> subtasks;

    public TaskEntity(
            Long taskId,
            String name,
            String description,
            Long project_id,
            String status,
            Date startDate,
            Date endDate,
            List<SubtaskEntity> subtasks
    ) {
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.project_id = project_id;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.subtasks = subtasks;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<SubtaskEntity> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<SubtaskEntity> subtasks) {
        this.subtasks = subtasks;
    }
}
