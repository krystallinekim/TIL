package com.beyond.mvc.auth.model.dao;

import com.beyond.mvc.auth.model.vo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.beyond.mvc.common.jdbc.JDBCTemplate.close;

public class AuthDaoImpl implements AuthDao {

    @Override
    public User getUserById(Connection connection, String userId) {
        User user = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM user WHERE username = ? AND status ='Y'";

        try {
            Class.forName("org.mariadb.jdbc.Driver");

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
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(statement);
        }

        return user;
    }

    @Override
    public int insertUser(Connection connection, User user) {
        int result = 0;

        String query = "INSERT INTO user VALUES(NULL,?,?,DEFAULT,?,?,?,?,?,DEFAULT,DEFAULT,DEFAULT)";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getNickname());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getAddress());
            statement.setString(7, user.getHobby());

            result = statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(statement);
        }

        return result;
    }
}
