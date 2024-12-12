package ru.itis.orisproject.db.mappers;

import ru.itis.orisproject.models.Account;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet resultSet) {
        try {
            String username = resultSet.getString(1);
            String password = resultSet.getString(2);
            String email = resultSet.getString(3);
            String icon_path = resultSet.getString(4);
            return new Account(username, password, email, icon_path);
        } catch (SQLException e) {
            return null;
        }
    }
}
