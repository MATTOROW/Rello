package ru.itis.orisproject.api;

import ru.itis.orisproject.dto.request.TaskRequest;
import ru.itis.orisproject.dto.response.TaskResponse;
import ru.itis.orisproject.models.TaskEntity;

import java.sql.Connection;
import java.util.List;
import java.util.UUID;

public interface TaskApi {
    TaskResponse getById(UUID id);

    int save(TaskRequest task, UUID projectId);

    int updateById(UUID projectId, TaskRequest task);

    int deleteById(UUID id);

    TaskEntity getEntityById(UUID id);

    List<TaskResponse> getByProjectId(UUID projectId);

    int updateTaskStatus(UUID id, Connection connection);
}
