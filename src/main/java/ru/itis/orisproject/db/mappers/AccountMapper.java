package ru.itis.orisproject.db.mappers;

import ru.itis.orisproject.models.Account;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet resultSet) {
        try {
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            String icon_path = resultSet.getString("icon_path");
            System.out.println(username + " " +  password + " " + email + " " + icon_path);
            return new Account(username, password, email, icon_path);

        } catch (SQLException e) {
            return null;
        }
    }
}
