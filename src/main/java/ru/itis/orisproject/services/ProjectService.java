package ru.itis.orisproject.services;

import ru.itis.orisproject.api.ProjectApi;
import ru.itis.orisproject.dto.request.ProjectRequest;
import ru.itis.orisproject.dto.response.ProjectResponse;
import ru.itis.orisproject.mappers.dto.ProjectMapper;
import ru.itis.orisproject.mappers.dto.ProjectMapperImpl;
import ru.itis.orisproject.models.ProjectEntity;
import ru.itis.orisproject.repositories.ProjectRepository;

import java.util.List;

public class ProjectService implements ProjectApi {
    private final ProjectMapper mapper = new ProjectMapperImpl();
    private final ProjectRepository repo = new ProjectRepository();

    @Override
    public List<ProjectResponse> getAll() {
        return repo.getAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public ProjectResponse getById(Long id) {
        return mapper.toResponse(repo.getById(id));
    }

    @Override
    public int save(ProjectRequest projectRequest, String username) {
        return repo.save(mapper.toEntity(projectRequest), username);
    }

    @Override
    public int updateById(Long id, ProjectRequest projectRequest) {
        return repo.updateById(id, mapper.toEntity(projectRequest));
    }

    @Override
    public int deleteById(Long id) {
        return repo.deleteById(id);
    }

    @Override
    public ProjectEntity getEntityById(Long id) {
        return repo.getById(id);
    }

    @Override
    public List<ProjectResponse> getByUsername(String username) {
        return repo.getByUsername(username).stream().map(mapper::toResponse).toList();
    }
}
