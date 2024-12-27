package ru.itis.orisproject.mappers;

import ru.itis.orisproject.models.SubtaskEntity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SubtaskEntityMapper implements RowMapper<SubtaskEntity> {
    @Override
    public SubtaskEntity mapRow(ResultSet resultSet) {
        try {
            UUID subtaskId = resultSet.getObject("subtask_id", UUID.class);
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            UUID taskId = resultSet.getObject("task_id", UUID.class);
            boolean completed = resultSet.getBoolean("completed");
            Date endDate = resultSet.getDate("end_date");
            return new SubtaskEntity(subtaskId, name, description, taskId, completed, endDate);
        } catch (SQLException e) {
            return null;
        }
    }
}
