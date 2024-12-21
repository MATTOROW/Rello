package ru.itis.orisproject.api;

import ru.itis.orisproject.dto.request.ProjectRequest;
import ru.itis.orisproject.dto.response.ProjectResponse;
import ru.itis.orisproject.models.ProjectEntity;

import java.util.List;
import java.util.UUID;

public interface ProjectApi {
    List<ProjectResponse> getAll();

    ProjectResponse getById(UUID id);

    int save(ProjectRequest projectRequest, String username);

    int updateById(UUID id, ProjectRequest projectRequest);

    int deleteById(UUID id);

    ProjectEntity getEntityById(UUID id);

    List<ProjectResponse> getByUsername(String username);
}
