package ru.itis.orisproject.repositories;


import ru.itis.orisproject.db.DBConfig;
import ru.itis.orisproject.dto.response.TaskResponse;
import ru.itis.orisproject.mappers.ProjectEntityMapper;
import ru.itis.orisproject.mappers.dto.TaskMapperImpl;
import ru.itis.orisproject.models.ProjectEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProjectRepository {
    private final ProjectEntityMapper projectEntityMapper = new ProjectEntityMapper();
    private final TaskRepository taskRepository = new TaskRepository();
    private final TaskMapperImpl taskMapper = new TaskMapperImpl();

    //language=sql
    private final String SQL_GET_ALL = "SELECT * FROM projects";
    //language=sql
    private final String SQL_GET_BY_ID = "SELECT * FROM projects WHERE project_id = ?";
    //language=sql
    private final String SQL_SAVE = """
WITH insert_project AS (
    INSERT INTO projects (name, description) VALUES (?, ?) RETURNING project_id
)
INSERT INTO account_project SELECT ?, project_id, (SELECT role_id FROM project_roles WHERE role_name LIKE 'OWNER')
                            FROM insert_project""";
    //language=sql
    private final String SQL_UPDATE_BY_ID = "UPDATE projects SET name = ?, description = ? WHERE project_id = ?";
    //language=sql
    private final String SQL_DELETE_BY_ID = "DELETE FROM projects WHERE project_id = ?";
    //language=sql
    private final String SQL_GET_BY_USERNAME = """
SELECT * FROM projects INNER JOIN account_project USING(project_id) WHERE acc_username = ?""";

    public List<ProjectEntity> getAll() {
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ProjectEntity> projects = new ArrayList<>();
            while (resultSet.next()) {
                projects.add(projectEntityMapper.mapRow(resultSet));
            }
            return projects;
        } catch (SQLException e) {
            return null;
        }
    }

    public ProjectEntity getById(UUID id) {
        try (Connection connection = DBConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? projectEntityMapper.mapRow(resultSet) : null;
        } catch (SQLException e) {
            return null;
        }
    }

    public int save(ProjectEntity project, String username) {
        try (Connection connection = DBConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE)) {
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setString(3, username);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                return 0;
            }
            return -1;
        }
    }

    public int updateById(UUID id, ProjectEntity project) {
        try (Connection connection = DBConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BY_ID)) {
            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setObject(3, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                return 0;
            }
            return -1;
        }
    }

    public int deleteById(UUID id) {
        try (Connection connection = DBConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            preparedStatement.setObject(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return -1;
        }
    }

    public List<ProjectEntity> getByUsername(String username) {
        try (Connection connection = DBConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_USERNAME)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ProjectEntity> projects = new ArrayList<>();
            while (resultSet.next()) {
                projects.add(projectEntityMapper.mapRow(resultSet));
            }
            return projects;
        } catch (SQLException e) {
            return null;
        }
    }

    public ProjectEntity getWithTasksById(UUID id) {
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID)) {
            connection.setAutoCommit(false);
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ProjectEntity projectEntity = projectEntityMapper.mapRow(resultSet);
                List<TaskResponse> tasks = taskRepository.getByProjectId(id, connection).stream()
                        .map(taskMapper::toResponse)
                        .toList();
                projectEntity.setTasks(tasks);
                connection.commit();
                return projectEntity;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }
}
