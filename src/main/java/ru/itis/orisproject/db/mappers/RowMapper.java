package ru.itis.orisproject.db.mappers;

import java.sql.ResultSet;

public interface RowMapper<T> {
    T mapRow(ResultSet resultSet);
}
