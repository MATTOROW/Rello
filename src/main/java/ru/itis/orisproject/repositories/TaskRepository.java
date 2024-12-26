package ru.itis.orisproject.repositories;

import ru.itis.orisproject.db.DBConfig;
import ru.itis.orisproject.dto.request.ProjectRequest;
import ru.itis.orisproject.dto.response.SubtaskResponse;
import ru.itis.orisproject.dto.response.TaskResponse;
import ru.itis.orisproject.mappers.TaskEntityMapper;
import ru.itis.orisproject.mappers.dto.SubtaskMapper;
import ru.itis.orisproject.mappers.dto.SubtaskMapperImpl;
import ru.itis.orisproject.models.ProjectEntity;
import ru.itis.orisproject.models.SubtaskEntity;
import ru.itis.orisproject.models.TaskEntity;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskRepository {
    private final TaskEntityMapper taskEntityMapper = new TaskEntityMapper();
    private final SubtaskRepository subtaskRepository;
    private final SubtaskMapper subtaskMapper = new SubtaskMapperImpl();

    public TaskRepository() {
        this.subtaskRepository = new SubtaskRepository(this);
    }

    public TaskRepository(SubtaskRepository subtaskRepository) {
        this.subtaskRepository = subtaskRepository;
    }

    //language=sql
    private final String SQL_GET_BY_ID = "SELECT * FROM tasks WHERE task_id = ?";
    //language=sql
    private final String SQL_SAVE = """
INSERT INTO tasks (name, description, project_id, start_date, end_date) VALUES (?, ?, ?, ?, ?)""";
    //language=sql
    private final String SQL_UPDATE_BY_ID = "UPDATE tasks SET name = ?, description = ?, start_date = ? WHERE task_id = ?";
    //language=sql
    private final String SQL_DELETE_BY_ID = "DELETE FROM tasks WHERE task_id = ?";
    //language=sql
    private final String SQL_GET_BY_PROJECT_ID = "SELECT * FROM tasks WHERE project_id = ?";
    //language=sql
    private final String SQL_UPDATE_TASK_STATUS = "UPDATE tasks SET status = ?, end_date = ? WHERE task_id = ?";

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

    public int updateById(UUID taskId, TaskEntity task) {
        try (Connection connection = DBConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BY_ID)) {
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setDate(3, task.getStartDate());
            preparedStatement.setObject(4, taskId);
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

    public int updateTaskStatus(UUID id, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_TASK_STATUS)) {
            preparedStatement.setObject(3, id);
            List<SubtaskEntity> subtasks = subtaskRepository.getByTaskId(id, connection);
            int count = subtasks.size();
            long completed = subtasks.stream().filter(SubtaskEntity::isCompleted).count();
            preparedStatement.setDate(2, null);
            if (completed == 0) {
                preparedStatement.setString(1, "Not started");
            } else if (completed == count) {
                preparedStatement.setString(1, "Completed");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formatted = df.format(new Date());
                preparedStatement.setDate(2, java.sql.Date.valueOf(formatted));
            } else {
                preparedStatement.setString(1, "In progress");
            }
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
