package com.library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5433/library_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "kosher";

    private static Connection connection = null;

    private DBConnection() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Подключение к базе данных установлено");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Драйвер PostgreSQL не найден");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к базе данных");
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Подключение к базе данных закрыто");
                }
            } catch (SQLException e) {
                System.err.println("Ошибка при закрытии подключения");
            }
        }
    }
}