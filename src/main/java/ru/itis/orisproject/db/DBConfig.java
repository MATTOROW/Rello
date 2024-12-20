package ru.itis.orisproject.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConfig {
    private static final HikariDataSource ds = init();

    private DBConfig() {
    }

    public static HikariDataSource init() {
        try {
            Properties properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(properties.getProperty("url"));
            config.setUsername(properties.getProperty("username"));
            config.setPassword(properties.getProperty("password"));
            config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("hikari.max-pool-size")));
            config.setDriverClassName(properties.getProperty("driver.classname"));
            return new HikariDataSource(config);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void closeAll() {
        ds.close();
    }
}
