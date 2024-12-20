package ru.itis.orisproject.mappers.dto;


import ru.itis.orisproject.dto.request.ProjectRequest;
import ru.itis.orisproject.dto.response.ProjectResponse;
import ru.itis.orisproject.models.ProjectEntity;

public interface ProjectMapper {
    ProjectEntity toEntity(ProjectRequest projectRequest);

    ProjectResponse toResponse(ProjectEntity projectEntity);
}
