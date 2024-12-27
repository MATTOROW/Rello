package ru.itis.orisproject.api;

import ru.itis.orisproject.dto.request.SubtaskRequest;
import ru.itis.orisproject.dto.response.SubtaskResponse;
import ru.itis.orisproject.models.SubtaskEntity;

import java.util.List;
import java.util.UUID;

public interface SubtaskApi {
    SubtaskResponse getById(UUID id);

    int save(SubtaskRequest subtask, UUID taskId);

    int updateById(UUID subtaskId, SubtaskRequest subtask);

    int deleteById(UUID id);

    SubtaskEntity getEntityById(UUID id);

    List<SubtaskResponse> getByTaskId(UUID taskId);

    int updateStatus(UUID subtaskId, boolean status, UUID taskId);
}
