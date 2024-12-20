package ru.itis.orisproject.api;

import ru.itis.orisproject.dto.request.AccountRequest;
import ru.itis.orisproject.dto.request.ProjectRequest;
import ru.itis.orisproject.dto.response.ProjectResponse;
import ru.itis.orisproject.models.ProjectEntity;

import java.util.List;

public interface ProjectApi {
    List<ProjectResponse> getAll();

    ProjectResponse getById(Long id);

    int save(ProjectRequest projectRequest, String username);

    int updateById(Long id, ProjectRequest projectRequest);

    int deleteById(Long id);

    ProjectEntity getEntityById(Long id);

    List<ProjectResponse> getByUsername(String username);
}
