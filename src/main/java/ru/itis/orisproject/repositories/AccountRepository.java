package ru.itis.orisproject.repositories;

import ru.itis.orisproject.db.DBConfig;
import ru.itis.orisproject.mappers.AccountEntityMapper;
import ru.itis.orisproject.models.AccountEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
    private final AccountEntityMapper accountEntityMapper = new AccountEntityMapper();

    //language=sql
    private final String SQL_GET_BY_USERNAME = "SELECT * FROM accounts WHERE username = ?";
    //language=sql
    private final String SQL_SAVE = """
INSERT INTO accounts (username, password, email, icon_path, description) VALUES (?, ?, ?, ?, ?)""";
    //language=sql
    private final String SQL_DELETE_BY_USERNAME = "DELETE FROM accounts WHERE username = ?";
    //language=sql
    private final String SQL_UPDATE_BY_USERNAME = """
UPDATE accounts SET username = ?, password = ?, email = ?, icon_path = ?, description = ? WHERE username = ?""";
    //language=sql
    private final String SQL_GET_BY_USERNAME_ILIKE = "SELECT * FROM accounts WHERE username ILIKE ?";
    //language=sql
    private final String SQL_GET_ALL = "SELECT * FROM accounts";

    public AccountEntity getByUsername(String username) {
        try (PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(SQL_GET_BY_USERNAME)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? accountEntityMapper.mapRow(resultSet) : null;
        } catch (SQLException e) {
            return null;
        }
    }

    public int save(AccountEntity acc) {
        try (PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(SQL_SAVE)) {
            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2, acc.getPassword());
            preparedStatement.setString(3, acc.getEmail());
            preparedStatement.setString(4, acc.getIconPath());
            preparedStatement.setString(5, acc.getDescription());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                return 0;
            }
            return -1;
        }
    }

    public int deleteByUsername(String username) {
        try (PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(SQL_DELETE_BY_USERNAME)) {
            preparedStatement.setString(1, username);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return -1;
        }
    }

    public int updateByUsername(AccountEntity acc, String username) {
        try (PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(SQL_UPDATE_BY_USERNAME)) {
            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2, acc.getPassword());
            preparedStatement.setString(3, acc.getEmail());
            preparedStatement.setString(4, acc.getIconPath());
            preparedStatement.setString(5, acc.getDescription());
            preparedStatement.setString(6, username);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                return 0;
            }
            return -1;
        }
    }

    public List<AccountEntity> getByUsernameILike(String username) {
        try (PreparedStatement preparedStatement = DBConfig
                .getConnection()
                .prepareStatement(SQL_GET_BY_USERNAME_ILIKE)) {
            preparedStatement.setString(1, "\\%%s\\%".formatted(username));
            ResultSet resultSet = preparedStatement.executeQuery();
            List<AccountEntity> accs = new ArrayList<>();
            while (resultSet.next()) {
                accs.add(accountEntityMapper.mapRow(resultSet));
            }
            return accs;
        } catch (SQLException e) {
            return null;
        }
    }

    public List<AccountEntity> getAll() {
        try (PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(SQL_GET_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<AccountEntity> accs = new ArrayList<>();
            while (resultSet.next()) {
                accs.add(accountEntityMapper.mapRow(resultSet));
            }
            return accs;
        } catch (SQLException e) {
            return null;
        }
    }
}
