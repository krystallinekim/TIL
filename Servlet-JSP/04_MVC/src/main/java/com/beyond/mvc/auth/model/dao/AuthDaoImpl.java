package com.beyond.mvc.auth.model.dao;

import com.beyond.mvc.auth.model.vo.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDaoImpl implements AuthDao {

    @Override
    public User getUserById(String userId) {
        User user = null;
        Connection connection = null;
        // Statement statement = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        // String query = "SELECT * FROM user WHERE username = 'admin' AND status ='Y'";
        // String query = "SELECT * FROM user WHERE username = '" + userId + "' AND status ='Y'";
        String query = "SELECT * FROM user WHERE username = ? AND status ='Y'";

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            connection = DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/web",
                    "beyond",
                    "beyond"
            );
            // statement = connection.createStatement();
            // resultSet = statement.executeQuery(query);
            statement = connection.prepareStatement(query);

            statement.setString(1, userId);

            resultSet = statement.executeQuery();

            if (resultSet.next()){
               user = User.builder()
                       .no(resultSet.getInt("no"))
                       .username(resultSet.getString("username"))
                       .password(resultSet.getString("password"))
                       .role(resultSet.getString("role"))
                       .nickname(resultSet.getString("nickname"))
                       .phone(resultSet.getString("phone"))
                       .email(resultSet.getString("email"))
                       .address(resultSet.getString("address"))
                       .hobby(resultSet.getString("hobby"))
                       .status(resultSet.getString("status"))
                       .createdAt(resultSet.getDate("created_at"))
                       .updatedAt(resultSet.getDate("updated_at"))
                       .build();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }
}
