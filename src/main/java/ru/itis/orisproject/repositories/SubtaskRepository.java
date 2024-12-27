package ru.itis.orisproject.repositories;

import ru.itis.orisproject.db.DBConfig;
import ru.itis.orisproject.mappers.SubtaskEntityMapper;
import ru.itis.orisproject.models.SubtaskEntity;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SubtaskRepository {
    private final SubtaskEntityMapper subtaskEntityMapper = new SubtaskEntityMapper();
    private final TaskRepository taskRepository;

    public SubtaskRepository() {
        this.taskRepository = new TaskRepository(this);
    }

    public SubtaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //language=sql
    private final String SQL_GET_BY_ID = "SELECT * FROM subtasks WHERE subtask_id = ?";
    //language=sql
    private final String SQL_SAVE = """
INSERT INTO subtasks (name, description, task_id) VALUES (?, ?, ?)""";
    //language=sql
    private final String SQL_UPDATE_BY_ID = """
UPDATE subtasks SET name = ?, description = ? WHERE subtask_id = ?""";
    //language=sql
    private final String SQL_DELETE_BY_ID = "DELETE FROM subtasks WHERE subtask_id = ?";
    //language=sql
    private final String SQL_GET_BY_TASK_ID = "SELECT * FROM subtasks WHERE task_id = ?";
    //language=sql
    private final String SQL_UPDATE_STATUS = "UPDATE subtasks SET completed = ?, end_date = ? WHERE subtask_id = ?";

    public SubtaskEntity getById(UUID id) {
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? this.subtaskEntityMapper.mapRow(resultSet) : null;
        } catch (SQLException e) {
            return null;
        }
    }

    public int save(SubtaskEntity subtask, UUID taskId) {
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE)) {
            preparedStatement.setString(1, subtask.getName());
            preparedStatement.setString(2, subtask.getDescription());
            preparedStatement.setObject(3, taskId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                return 0;
            }
            return -1;
        }
    }

    public int updateById(UUID subtaskId, SubtaskEntity subtask) {
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BY_ID)) {
            preparedStatement.setString(1, subtask.getName());
            preparedStatement.setString(2, subtask.getDescription());
            preparedStatement.setObject(3, subtaskId);
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

    public List<SubtaskEntity> getByTaskId(UUID taskId) {
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_TASK_ID)) {
            preparedStatement.setObject(1, taskId);
            List<SubtaskEntity> subtaskResponse = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                subtaskResponse.add(this.subtaskEntityMapper.mapRow(resultSet));
            }
            return subtaskResponse;
        } catch (SQLException e) {
            return null;
        }
    }

    public List<SubtaskEntity> getByTaskId(UUID taskId, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_TASK_ID)) {
            preparedStatement.setObject(1, taskId);
            List<SubtaskEntity> subtaskResponse = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                subtaskResponse.add(this.subtaskEntityMapper.mapRow(resultSet));
            }
            return subtaskResponse;
        } catch (SQLException e) {
            return null;
        }
    }

    public int updateStatus(UUID subtaskId, boolean status, UUID taskId) {
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_STATUS)) {
            connection.setAutoCommit(false);
            preparedStatement.setObject(3, subtaskId);
            if (status) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formatted = df.format(new java.util.Date());
                preparedStatement.setDate(2, Date.valueOf(formatted));
            } else {
                preparedStatement.setObject(2, null);
            }
            preparedStatement.setBoolean(1, status);
           int code = preparedStatement.executeUpdate();
           int taskCode = taskRepository.updateTaskStatus(taskId, connection);
           connection.commit();
           return code == 1 && taskCode != -1 ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
