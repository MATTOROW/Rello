package ru.itis.orisproject.repositories;

import ru.itis.orisproject.db.DBConfig;
import ru.itis.orisproject.mappers.AccountEntityMapper;
import ru.itis.orisproject.models.AccountEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RmmtRepository {
    private final AccountEntityMapper accountEntityMapper = new AccountEntityMapper();

    //language=sql
    private final String SQL_GET_ACC_BY_TOKEN = """
SELECT * FROM accounts INNER JOIN rmmt USING(username) WHERE token = ?""";
    //language=sql
    private final String SQL_UPDATE_ACC_TOKEN = "UPDATE rmmt SET token = ? WHERE username = ? AND device_id = ?";
    //language=sql
    private final String SQL_SAVE = "INSERT INTO rmmt VALUES (?, ?, ?)";
    //language=sql
    private final String SQL_DEVICE_REMEMBERED = "SELECT device_id FROM rmmt WHERE device_id = ?";

    public AccountEntity getAccByToken(String token) {
        try (PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(SQL_GET_ACC_BY_TOKEN)) {
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? accountEntityMapper.mapRow(resultSet) : null;
        } catch (SQLException e) {
            return null;
        }
    }

    public int updateAccToken(String username, String token, String device_id) {
        try (PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(SQL_UPDATE_ACC_TOKEN)) {
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, device_id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return -1;
        }
    }

    public int save(String username, String token, String deviceId) {
        try (PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(SQL_SAVE)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, token);
            preparedStatement.setString(3, deviceId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return -1;
        }
    }

    public boolean deviceRemembered(String deviceId) {
        try (PreparedStatement preparedStatement = DBConfig.getConnection().prepareStatement(SQL_DEVICE_REMEMBERED)) {
            preparedStatement.setString(1, deviceId);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            return false;
        }
    }
}
