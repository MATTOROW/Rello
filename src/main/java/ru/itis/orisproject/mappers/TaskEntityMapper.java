package ru.itis.orisproject.mappers;

import org.springframework.scheduling.config.Task;
import ru.itis.orisproject.models.TaskEntity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TaskEntityMapper implements RowMapper<TaskEntity> {
    @Override
    public TaskEntity mapRow(ResultSet resultSet) {
        try {
            UUID taskId = resultSet.getObject("task_id", UUID.class);
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            UUID projectId = resultSet.getObject("project_id", UUID.class);
            String status = resultSet.getString("status");
            Date startDate = resultSet.getDate("start_date");
            Date endDate = resultSet.getDate("end_date");
            return new TaskEntity(taskId, name, description, projectId, status, startDate, endDate);
        } catch (SQLException e) {
            return null;
        }
    }
}
