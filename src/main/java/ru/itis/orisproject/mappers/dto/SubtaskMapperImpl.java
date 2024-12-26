package ru.itis.orisproject.mappers.dto;

import ru.itis.orisproject.dto.request.SubtaskRequest;
import ru.itis.orisproject.dto.response.SubtaskResponse;
import ru.itis.orisproject.models.SubtaskEntity;

public class SubtaskMapperImpl implements SubtaskMapper {
    @Override
    public SubtaskEntity toEntity(SubtaskRequest subtaskRequest) {
        if (subtaskRequest == null) {
            return null;
        }
        return new SubtaskEntity(
                null,
                subtaskRequest.name(),
                subtaskRequest.description(),
                null,
                false,
                null
        );
    }

    @Override
    public SubtaskResponse toResponse(SubtaskEntity subtaskEntity) {
        if (subtaskEntity == null) {
            return null;
        }

        return new SubtaskResponse(
                subtaskEntity.getSubtaskId(),
                subtaskEntity.getName(),
                subtaskEntity.getDescription(),
                subtaskEntity.isCompleted()
                );
    }
}
