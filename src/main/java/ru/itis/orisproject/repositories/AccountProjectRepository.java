package ru.itis.orisproject.repositories;

import ru.itis.orisproject.db.DBConfig;
import ru.itis.orisproject.mappers.AccountEntityMapper;
import ru.itis.orisproject.models.AccountEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountProjectRepository {
    private final AccountEntityMapper accountEntityMapper = new AccountEntityMapper();

    //language=sql
    private final String SQL_HAS_ACCESS = "SELECT COUNT(*) FROM account_project WHERE project_id = ? AND acc_username = ?";
    //language=sql
    private final String SQL_UPDATE_ROLE = """
UPDATE account_project SET role_id = (SELECT role_id FROM project_roles WHERE role_name = ?)
                       WHERE project_id = ? AND acc_username = ?""";
    //language=sql
    private final String SQL_ADD_PARTICIPANT = "INSERT INTO account_project VALUES (?, ?, ?)";
    //language=sql
    private final String SQL_GET_ALL_PARTICIPANTS = """
SELECT * FROM account_project
    INNER JOIN accounts ON account_project.acc_username = accounts.username INNER JOIN project_roles USING(role_id)
         WHERE project_id = ?""";
    //language=sql
    private final String SQL_GET_OWNER = """
SELECT * FROM account_project INNER JOIN accounts ON account_project.acc_username = accounts.username
         WHERE project_id = ? AND role_id = (SELECT role_id FROM project_roles WHERE role_name LIKE 'OWNER')""";
    //language=sql
    private final String SQL_IS_OWNER = """
SELECT count(*)
FROM account_project
WHERE project_id = ? AND acc_username = ? AND role_id = (SELECT role_id FROM project_roles WHERE role_name = 'OWNER')""";

    public boolean hasAccess(UUID projectId, String username) {
        try (   Connection connection = DBConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_HAS_ACCESS)) {
            preparedStatement.setObject(1, projectId);
            preparedStatement.setString(2, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public int updateRole(UUID projectId, String username, String role) {
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ROLE)) {
            preparedStatement.setString(1, role);
            preparedStatement.setObject(2, projectId);
            preparedStatement.setString(3, username);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return -1;
        }
    }

    public int addNewParticipant(UUID projectId, String username, String role) {
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_PARTICIPANT)) {
            preparedStatement.setString(1, username);
            preparedStatement.setObject(2, projectId);
            preparedStatement.setString(3, role);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return -1;
        }
    }

    public Map<AccountEntity, String> getAllParticipants(UUID projectId) {
        Map<AccountEntity, String> map = new HashMap<>();
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_PARTICIPANTS)) {
           preparedStatement.setObject(1, projectId);
           ResultSet resultSet = preparedStatement.executeQuery();
           while (resultSet.next()) {
               AccountEntity entity = accountEntityMapper.mapRow(resultSet);
               String role = resultSet.getString("role_name");
               map.put(entity, role);
           }
           return map;
        } catch (SQLException e) {
            return null;
        }
    }

    public AccountEntity getOwner(UUID projectId) {
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_OWNER)) {
            preparedStatement.setObject(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? accountEntityMapper.mapRow(resultSet) : null;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean isOwner(UUID projectId, String username) {
        try (Connection connection = DBConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_IS_OWNER)) {
            preparedStatement.setObject(1, projectId);
            preparedStatement.setString(2, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
