package com.beyond.mvc.common.jdbc;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
    public static Connection getConnection() {
        Connection connection = null;
        Properties properties = new Properties();
        String filePath = JDBCTemplate.class.getClassLoader().getResource("driver.properties").getPath();

        // target/mvc-1.0-SNAPSHOT/WEB-INF/classes/driver.properties에 들어간다.
        // System.out.println(filePath);

        try {
            properties.load(new FileReader(filePath));

            Class.forName(properties.getProperty("db.driver"));

            connection = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password")
            );

            connection.setAutoCommit(false);

        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void commit(Connection connection) {
        try {
            if(connection != null && !connection.isClosed()) {
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollback(Connection connection) {
        try {
            if(connection != null && !connection.isClosed()) {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection connection) {
        try {
            if(connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Statement statement) {
        try {
            if(statement != null && !statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(ResultSet resultSet) {
        try {
            if(resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
