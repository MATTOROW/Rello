package ru.itis.orisproject.mappers.dto;

import ru.itis.orisproject.dto.request.TaskRequest;
import ru.itis.orisproject.dto.response.TaskResponse;
import ru.itis.orisproject.models.TaskEntity;

public class TaskMapperImpl implements TaskMapper {
    @Override
    public TaskEntity toEntity(TaskRequest taskRequest) {
        if (taskRequest == null) {
            return null;
        }
        return new TaskEntity(
                null,
                taskRequest.name(),
                taskRequest.description(),
                taskRequest.projectId(),
                null,
                taskRequest.startDate(),
                taskRequest.endDate()
        );
    }

    @Override
    public TaskResponse toResponse(TaskEntity taskEntity) {
        if (taskEntity == null) {
            return null;
        }
        return new TaskResponse(
                taskEntity.getName(),
                taskEntity.getDescription(),
                taskEntity.getStatus(),
                taskEntity.getStartDate(),
                taskEntity.getEndDate()
        );
    }
}
