package ru.itis.orisproject.mappers.dto;

import ru.itis.orisproject.dto.request.TaskRequest;
import ru.itis.orisproject.dto.response.TaskResponse;
import ru.itis.orisproject.models.TaskEntity;

public interface TaskMapper {
    TaskEntity toEntity(TaskRequest taskRequest);

    TaskResponse toResponse(TaskEntity taskEntity);
}
