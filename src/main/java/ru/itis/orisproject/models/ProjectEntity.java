package ru.itis.orisproject.models;


import ru.itis.orisproject.dto.response.TaskResponse;

import java.util.List;
import java.util.UUID;

public class ProjectEntity {
    private UUID projectId;
    private String name;
    private String description;
    private List<TaskResponse> tasks;

    public ProjectEntity(UUID projectId, String name, String description, List<TaskResponse> tasks) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.tasks = tasks;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
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

    public List<TaskResponse> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskResponse> tasks) {
        this.tasks = tasks;
    }
}
