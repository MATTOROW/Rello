package ru.itis.orisproject.mappers;

import ru.itis.orisproject.models.ProjectEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectEntityMapper implements RowMapper<ProjectEntity> {
    @Override
    public ProjectEntity mapRow(ResultSet resultSet) {
        try {
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            return new ProjectEntity(0, name, description, null);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
