package ru.itis.orisproject.repositories;

import ru.itis.orisproject.db.DBConfig;
import ru.itis.orisproject.dto.request.ProjectRequest;
import ru.itis.orisproject.dto.response.SubtaskResponse;
import ru.itis.orisproject.dto.response.TaskResponse;
import ru.itis.orisproject.mappers.TaskEntityMapper;
import ru.itis.orisproject.mappers.dto.SubtaskMapper;
import ru.itis.orisproject.mappers.dto.SubtaskMapperImpl;
import ru.itis.orisproject.models.ProjectEntity;
import ru.itis.orisproject.models.TaskEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository {
    private final TaskEntityMapper taskEntityMapper = new TaskEntityMapper();
    private final SubtaskRepository subtaskRepository = new SubtaskRepository();
    private final SubtaskMapper subtaskMapper = new SubtaskMapperImpl();

    //language=sql
    private final String SQL_GET_BY_ID = "SELECT * FROM tasks WHERE task_id = ?";
    //language=sql
    private final String SQL_SAVE = """
INSERT INTO tasks (name, description, project_id, start_date, end_date) VALUES (?, ?, ?, ?, ?)""";
    //language=sql
    private final String SQL_UPDATE_BY_ID = "UPDATE tasks SET name = ?, description = ?, end_date = ? WHERE task_id = ?";
    //language=sql
    private final String SQL_DELETE_BY_ID = "DELETE FROM tasks WHERE task_id = ?";
    //language=sql
    private final String SQL_GET_BY_PROJECT_ID = "SELECT * FROM tasks WHERE project_id = ?";

    public TaskEntity getById(UUID id) {
        try (Connection connection = DBConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? taskEntityMapper.mapRow(resultSet) : null;
        } catch (SQLException e) {
            return null;
        }
    }

    public int save(TaskEntity task, UUID projectId) {
        try (Connection connection = DBConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setObject(3, projectId);
            preparedStatement.setDate(4, task.getStartDate());
            preparedStatement.setDate(5, task.getEndDate());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return -1;
        }
    }

    public int updateById(TaskEntity task, UUID projectId) {
        try (Connection connection = DBConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BY_ID)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setObject(3, projectId);
            preparedStatement.setDate(4, task.getStartDate());
            preparedStatement.setDate(5, task.getEndDate());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
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

    public List<TaskEntity> getByProjectId(UUID projectId) {
        return getByProjectId(projectId, DBConfig.getConnection());
    }

    public List<TaskEntity> getByProjectId(UUID projectId, Connection connection) {
        try (PreparedStatement preparedStatement =connection.prepareStatement(SQL_GET_BY_PROJECT_ID)) {
            preparedStatement.setObject(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<TaskEntity> tasks = new ArrayList<>();
            while (resultSet.next()) {
                tasks.add(taskEntityMapper.mapRow(resultSet));
            }
            return tasks;
        } catch (SQLException e) {
            return null;
        }
    }

    public TaskEntity getWithSubtasksById(UUID taskId) {
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID)) {
            connection.setAutoCommit(false);
            preparedStatement.setObject(1, taskId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                TaskEntity taskEntity = taskEntityMapper.mapRow(resultSet);
                List<SubtaskResponse> subtasks = subtaskRepository.getByTaskId(taskId, connection).stream()
                        .map(subtaskMapper::toResponse)
                        .toList();
                taskEntity.setSubtasks(subtasks);
                connection.commit();
                return taskEntity;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }
}
