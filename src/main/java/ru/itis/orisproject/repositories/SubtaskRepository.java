package ru.itis.orisproject.repositories;

import ru.itis.orisproject.db.DBConfig;
import ru.itis.orisproject.dto.request.SubtaskRequest;
import ru.itis.orisproject.dto.response.SubtaskResponse;
import ru.itis.orisproject.mappers.SubtaskEntityMapper;
import ru.itis.orisproject.models.SubtaskEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SubtaskRepository {
    private final SubtaskEntityMapper subtaskEntityMapper = new SubtaskEntityMapper();

    //language=sql
    private final String SQL_GET_BY_ID = "SELECT * FROM subtasks WHERE subtask_id = ?";
    //language=sql
    private final String SQL_SAVE = """
INSERT INTO subtasks (name, description, task_id, completed, end_date) VALUES (?, ?, ?, ?, ?)""";
    //language=sql
    private final String SQL_UPDATE_BY_ID = """
UPDATE subtasks SET name = ?, description = ?, completed = ?, end_date = ? WHERE subtask_id = ?""";
    //language=sql
    private final String SQL_DELETE_BY_ID = "DELETE FROM subtasks WHERE subtask_id = ?";
    //language=sql
    private final String SQL_GET_BY_TASK_ID = "SELECT * FROM subtasks WHERE task_id = ?";

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

    public int save(SubtaskRequest subtask, UUID taskId) {
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE)) {
            preparedStatement.setString(1, subtask.name());
            preparedStatement.setString(2, subtask.description());
            preparedStatement.setObject(3, taskId);
            preparedStatement.setBoolean(4, subtask.completed());
            preparedStatement.setDate(5, subtask.endDate());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                return 0;
            }
            return -1;
        }
    }

    public int updateById(SubtaskRequest subtask, UUID subtaskId) {
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_BY_ID)) {
            preparedStatement.setString(1, subtask.name());
            preparedStatement.setString(2, subtask.description());
            preparedStatement.setBoolean(3, subtask.completed());
            preparedStatement.setDate(4, subtask.endDate());
            preparedStatement.setObject(5, subtaskId);
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
}
