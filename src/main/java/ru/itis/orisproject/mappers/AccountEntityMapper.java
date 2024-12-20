package ru.itis.orisproject.mappers;

import ru.itis.orisproject.models.AccountEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountEntityMapper implements RowMapper<AccountEntity> {
    @Override
    public AccountEntity mapRow(ResultSet resultSet) {
        try {
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            String iconPath = resultSet.getString("icon_path");
            String description = resultSet.getString("description");
            return new AccountEntity(username, password, email, iconPath, description);

        } catch (SQLException e) {
            return null;
        }
    }
}
