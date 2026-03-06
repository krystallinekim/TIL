package com.beyond.mvc.auth.model.dao;

import com.beyond.mvc.auth.model.vo.User;

import java.sql.Connection;

public interface AuthDao {
    User getUserById(Connection connection, String userId);

    int insertUser(Connection connection, User user);

    int updateUser(Connection connection, User user);

    int deleteUser(Connection connection, int userNo);

    int updateUserStatus(Connection connection, int userNo, String status);
}
