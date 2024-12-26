package ru.itis.orisproject.services;

import ru.itis.orisproject.api.TaskApi;
import ru.itis.orisproject.dto.request.TaskRequest;
import ru.itis.orisproject.dto.response.TaskResponse;
import ru.itis.orisproject.mappers.dto.TaskMapperImpl;
import ru.itis.orisproject.models.TaskEntity;
import ru.itis.orisproject.repositories.TaskRepository;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

public class TaskService implements TaskApi {
    private final TaskRepository repo = new TaskRepository();
    private final TaskMapperImpl mapper = new TaskMapperImpl();

    @Override
    public TaskResponse getById(UUID id) {
        return mapper.toResponse(repo.getById(id));
    }

    @Override
    public int save(TaskRequest task, UUID projectId) {
        return repo.save(mapper.toEntity(task), projectId);
    }

    @Override
    public int updateById(UUID taskId, TaskRequest task) {
        return repo.updateById(taskId, mapper.toEntity(task));
    }

    @Override
    public int deleteById(UUID id) {
        return repo.deleteById(id);
    }

    @Override
    public TaskEntity getEntityById(UUID id) {
        return repo.getWithSubtasksById(id);
    }

    @Override
    public List<TaskResponse> getByProjectId(UUID projectId) {
        return repo.getByProjectId(projectId).stream().map(mapper::toResponse).toList();
    }

    @Override
    public int updateTaskStatus(UUID id, Connection connection) {
        return repo.updateTaskStatus(id, connection);
    }
}
