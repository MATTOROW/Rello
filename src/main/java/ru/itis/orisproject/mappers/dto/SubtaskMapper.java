package ru.itis.orisproject.mappers.dto;

import ru.itis.orisproject.dto.request.SubtaskRequest;
import ru.itis.orisproject.dto.response.SubtaskResponse;
import ru.itis.orisproject.models.SubtaskEntity;

public interface SubtaskMapper {
    SubtaskEntity toEntity(SubtaskRequest taskRequest);

    SubtaskResponse toResponse(SubtaskEntity taskEntity);
}
