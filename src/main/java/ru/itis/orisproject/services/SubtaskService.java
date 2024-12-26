package ru.itis.orisproject.services;

import ru.itis.orisproject.api.SubtaskApi;
import ru.itis.orisproject.dto.request.SubtaskRequest;
import ru.itis.orisproject.dto.response.SubtaskResponse;
import ru.itis.orisproject.mappers.dto.SubtaskMapperImpl;
import ru.itis.orisproject.models.SubtaskEntity;
import ru.itis.orisproject.repositories.SubtaskRepository;

import java.util.List;
import java.util.UUID;

public class SubtaskService implements SubtaskApi {
    private final SubtaskRepository repo = new SubtaskRepository();
    private final SubtaskMapperImpl mapper = new SubtaskMapperImpl();

    @Override
    public SubtaskResponse getById(UUID id) {
        return mapper.toResponse(repo.getById(id));
    }

    @Override
    public int save(SubtaskRequest subtask, UUID taskId) {
        return repo.save(subtask, taskId);
    }

    @Override
    public int updateById(SubtaskRequest subtask, UUID subtaskId) {
        return repo.updateById(subtask, subtaskId);
    }

    @Override
    public int deleteById(UUID id) {
        return repo.deleteById(id);
    }

    @Override
    public SubtaskEntity getEntityById(UUID id) {
        return repo.getById(id);
    }

    @Override
    public List<SubtaskResponse> getByTaskId(UUID taskId) {
        return repo.getByTaskId(taskId).stream().map(mapper::toResponse).toList();
    }
}
