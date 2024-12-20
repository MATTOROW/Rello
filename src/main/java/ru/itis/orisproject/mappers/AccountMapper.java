package ru.itis.orisproject.mappers;

import ru.itis.orisproject.models.AccountEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements RowMapper<AccountEntity> {
    @Override
    public AccountEntity mapRow(ResultSet resultSet) {
        try {
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            String icon_path = resultSet.getString("icon_path");
            return new AccountEntity(username, password, email, icon_path);

        } catch (SQLException e) {
            return null;
        }
    }
}
