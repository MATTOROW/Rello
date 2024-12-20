package ru.itis.orisproject.repositories;

import ru.itis.orisproject.db.DBConfig;
import ru.itis.orisproject.mappers.AccountMapper;
import ru.itis.orisproject.models.AccountEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
    private final AccountMapper accountMapper = new AccountMapper();

    //language=sql
    private final String SQL_GET_BY_USERNAME = "SELECT * FROM accounts WHERE username = ?";
    //language=sql
    private final String SQL_SAVE = """
INSERT INTO accounts (username, password, email, icon_path) VALUES (?, ?, ?, ?)""";
    //language=sql
    private final String SQL_DELETE_BY_USERNAME = "DELETE FROM accounts WHERE username = ?";
    //language=sql
    private final String SQL_UPDATE_BY_USERNAME = """
UPDATE accounts SET username = ?, password = ?, email = ?, icon_path = ? WHERE username = ?""";
    //language=sql
    private final String SQL_GET_BY_USERNAME_ILIKE = "SELECT * FROM accounts WHERE username ILIKE ?";

    public AccountEntity getByUsername(String username) {
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(SQL_GET_BY_USERNAME);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? accountMapper.mapRow(resultSet) : null;
        } catch (SQLException e) {
            return null;
        }
    }

    public int save(AccountEntity acc) {
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(SQL_SAVE);
            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2, acc.getPassword());
            preparedStatement.setString(3, acc.getEmail());
            preparedStatement.setString(4, acc.getIconPath());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return -1;
        }
    }

    public int deleteByUsername(String username) {
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(SQL_DELETE_BY_USERNAME);
            preparedStatement.setString(1, username);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return -1;
        }
    }

    public int updateByUsername(AccountEntity acc, String username) {
        try {
            PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(SQL_UPDATE_BY_USERNAME);
            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2, acc.getPassword());
            preparedStatement.setString(3, acc.getEmail());
            preparedStatement.setString(4, acc.getIconPath());
            preparedStatement.setString(5, username);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return -1;
        }
    }

    public List<AccountEntity> getByUsernameILike(String username) {
        try {
            PreparedStatement preparedStatement = DBConfig
                    .getConnection()
                    .prepareStatement(SQL_GET_BY_USERNAME_ILIKE);
            preparedStatement.setString(1, "\\%%s\\%".formatted(username));
            ResultSet resultSet = preparedStatement.executeQuery();
            List<AccountEntity> accs = new ArrayList<>();
            while (resultSet.next()) {
                accs.add(accountMapper.mapRow(resultSet));
            }
            return accs;
        } catch (SQLException e) {
            return null;
        }
    }
}
