package ru.itis.orisproject.services;

import ru.itis.orisproject.api.TaskApi;
import ru.itis.orisproject.dto.request.TaskRequest;
import ru.itis.orisproject.dto.response.TaskResponse;
import ru.itis.orisproject.mappers.dto.TaskMapperImpl;
import ru.itis.orisproject.models.TaskEntity;
import ru.itis.orisproject.repositories.TaskRepository;

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
    public int updateById(TaskRequest task, UUID projectId) {
        return repo.updateById(mapper.toEntity(task), projectId);
    }

    @Override
    public int deleteById(UUID id) {
        return repo.deleteById(id);
    }

    @Override
    public TaskEntity getEntityById(UUID id) {
        return repo.getById(id);
    }

    @Override
    public List<TaskResponse> getByProjectId(UUID projectId) {
        return repo.getByProjectId(projectId).stream().map(mapper::toResponse).toList();
    }
}
