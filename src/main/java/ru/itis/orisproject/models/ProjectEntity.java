package ru.itis.orisproject.models;

import org.springframework.scheduling.config.Task;

import java.util.List;

public class ProjectEntity {
    private long projectId;
    private String name;
    private String description;
    private List<Task> tasks;

    public ProjectEntity(long projectId, String name, String description, List<Task> tasks) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.tasks = tasks;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
