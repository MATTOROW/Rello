package ru.itis.orisproject.services;

import ru.itis.orisproject.api.ProjectApi;
import ru.itis.orisproject.dto.request.ProjectRequest;
import ru.itis.orisproject.dto.response.ProjectResponse;
import ru.itis.orisproject.mappers.dto.ProjectMapper;
import ru.itis.orisproject.mappers.dto.ProjectMapperImpl;
import ru.itis.orisproject.models.ProjectEntity;
import ru.itis.orisproject.repositories.ProjectRepository;

import java.util.List;
import java.util.UUID;

public class ProjectService implements ProjectApi {
    private final ProjectRepository repo = new ProjectRepository();
    private final ProjectMapper mapper = new ProjectMapperImpl();

    @Override
    public List<ProjectResponse> getAll() {
        return repo.getAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public ProjectResponse getById(UUID id) {
        return mapper.toResponse(repo.getById(id));
    }

    @Override
    public int save(ProjectRequest projectRequest, String username) {
        return repo.save(mapper.toEntity(projectRequest), username);
    }

    @Override
    public int updateById(UUID id, ProjectRequest projectRequest) {
        return repo.updateById(id, mapper.toEntity(projectRequest));
    }

    @Override
    public int deleteById(UUID id) {
        return repo.deleteById(id);
    }

    @Override
    public ProjectEntity getEntityById(UUID id) {
        return repo.getWithTasksById(id);
    }

    @Override
    public List<ProjectResponse> getByUsername(String username) {
        return repo.getByUsername(username).stream().map(mapper::toResponse).toList();
    }
}
