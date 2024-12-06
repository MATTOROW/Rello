package ru.itis.orisproject.db.dao;

import ru.itis.orisproject.db.DBConnection;
import ru.itis.orisproject.db.mappers.AccountMapper;
import ru.itis.orisproject.models.Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {
    private final DBConnection dbConnection = DBConnection.getDBConnection();
    private final AccountMapper accountMapper = new AccountMapper();

    //language=sql
    private final String SQL_GET_ACC_BY_USERNAME = "SELECT * FROM accounts WHERE username = ?";
    //language=sql
    private final String SQL_SAVE_ACC = """
INSERT INTO accounts (username, password, email, icon_path, rmmt) VALUES (?, ?, ?, ?, ?)""";
    //language=sql
    private final String SQL_DELETE_BY_USERNAME = "DELETE FROM accounts WHERE username = ?";
    //language=sql
    private final String SQL_UPDATE_BY_USERNAME = """
UPDATE accounts SET username = ?, password = ?, email = ?, icon_path = ?, rmmt = ? WHERE username = ?""";
    //language=sql
    private final String SQL_UPDATE_ACCOUNT_SESSION = "UPDATE accounts SET rmmt = ? WHERE username = ?";
    //language=sql
    private final String SQL_GET_ACC_BY_SESSION = "SELECT * FROM accounts WHERE rmmt = ?";

    public Account getAccountByUsername(String username) {
        try {
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(SQL_GET_ACC_BY_USERNAME);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? accountMapper.mapRow(resultSet) : null;
        } catch (SQLException e) {
            return null;
        }
    }

    public int save(Account acc) {
        try {
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(SQL_SAVE_ACC);
            preparedStatement.setString(1, acc.username());
            preparedStatement.setString(2, acc.password());
            preparedStatement.setString(3, acc.email());
            preparedStatement.setString(4, acc.icon_path());
            preparedStatement.setString(5, acc.rmmt());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return -1;
        }
    }

    public int deleteByUsername(String username) {
        try {
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(SQL_DELETE_BY_USERNAME);
            preparedStatement.setString(1, username);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return -1;
        }
    }

    public int updateByUsername(String username, Account acc) {
        try {
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(SQL_UPDATE_BY_USERNAME);
            preparedStatement.setString(1, acc.username());
            preparedStatement.setString(2, acc.password());
            preparedStatement.setString(3, acc.email());
            preparedStatement.setString(4, acc.icon_path());
            preparedStatement.setString(5, acc.rmmt());
            preparedStatement.setString(6, username);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return -1;
        }
    }

    public int updateRmmtnByUsername(String username, String rmmt) {
        try {
            PreparedStatement preparedStatement = dbConnection
                    .getConnection()
                    .prepareStatement(SQL_UPDATE_ACCOUNT_SESSION);
            preparedStatement.setString(1, rmmt);
            preparedStatement.setString(2, username);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return -1;
        }
    }

    public Account getBySession(String rmmt) {
        try {
            PreparedStatement preparedStatement = dbConnection.getConnection().prepareStatement(SQL_GET_ACC_BY_SESSION);
            preparedStatement.setString(1, rmmt);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? accountMapper.mapRow(resultSet) : null;
        } catch (SQLException e) {
            return null;
        }
    }
}
