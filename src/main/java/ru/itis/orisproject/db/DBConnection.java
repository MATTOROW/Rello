package ru.itis.orisproject.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;

public class DBConnection {
    private Deque<Connection> connections;
    private static final int max_size = 100;
    private static DBConnection entity;
    private Deque<Connection> usedConnections;

    private DBConnection() {
    }

    synchronized public void initDB(String url, String username, String password) {
        this.connections = new ArrayDeque<>(max_size);
        this.usedConnections = new ArrayDeque<>(max_size);
        try {
            Class.forName("org.postgresql.Driver");
            for (int i = 0; i < max_size; ++i) {
                try {
                    connections.add(DriverManager.getConnection(url, username, password));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("initted");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBConnection getDBConnection() {
        if (entity == null) {
            synchronized (DBConnection.class) {
                if (entity == null) {
                    entity = new DBConnection();
                }
            }
        }
        return entity;
    }

    synchronized public Connection getConnection() {
        if (this.connections.isEmpty()) {
            return null;
        }
        Connection conn = this.connections.pop();
        this.usedConnections.add(conn);
        return conn;
    }

    public void releaseConnection(Connection connect) {
        if (this.connections.size() == max_size) {
            try {
                connect.close();
            } catch (SQLException e) {
                System.out.println("Ошибка! Ошибка!");
            }
        } else {
            this.connections.add(connect);
            this.usedConnections.remove(connect);
        }
    }

    public void closeAll() {
        for (Connection conn: usedConnections) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
