package ru.itis.orisproject.mappers;

import ru.itis.orisproject.models.ProjectEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ProjectEntityMapper implements RowMapper<ProjectEntity> {
    @Override
    public ProjectEntity mapRow(ResultSet resultSet) {
        try {
            UUID projectId = resultSet.getObject("project_id", UUID.class);
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            return new ProjectEntity(projectId, name, description, null);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
