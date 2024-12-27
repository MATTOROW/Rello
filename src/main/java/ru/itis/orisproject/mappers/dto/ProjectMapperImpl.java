package ru.itis.orisproject.mappers.dto;

import ru.itis.orisproject.dto.request.ProjectRequest;
import ru.itis.orisproject.dto.response.ProjectResponse;
import ru.itis.orisproject.models.ProjectEntity;

public class ProjectMapperImpl implements ProjectMapper {
    @Override
    public ProjectEntity toEntity(ProjectRequest projectRequest) {
        if (projectRequest == null) {
            return null;
        }

        return new ProjectEntity(
                null,
                projectRequest.name(),
                projectRequest.description(),
                null
        );
    }

    @Override
    public ProjectResponse toResponse(ProjectEntity projectEntity) {
        if (projectEntity == null) {
            return null;
        }

        return new ProjectResponse(
                projectEntity.getProjectId(),
                projectEntity.getName(),
                projectEntity.getDescription()
        );
    }
}
