package com.beyond.mvc.auth.model.service;

import com.beyond.mvc.auth.model.dao.AuthDao;
import com.beyond.mvc.auth.model.dao.AuthDaoImpl;
import com.beyond.mvc.auth.model.vo.User;

import java.sql.Connection;

import static com.beyond.mvc.common.jdbc.JDBCTemplate.close;
import static com.beyond.mvc.common.jdbc.JDBCTemplate.commit;
import static com.beyond.mvc.common.jdbc.JDBCTemplate.getConnection;
import static com.beyond.mvc.common.jdbc.JDBCTemplate.rollback;


public class AuthServiceImpl implements AuthService {
    private final AuthDao authDao;

    public AuthServiceImpl() {
        this.authDao = new AuthDaoImpl();
    }

    @Override
    public User login(String userId, String userPwd) {
        User user;
        Connection connection = getConnection();

        user = authDao.getUserById(connection, userId);

        close(connection);

        if (user == null || !user.getPassword().equals(userPwd)) {
            return null;
        }


        return user;
    }

    @Override
    public int save(User user) {
        int result;
        Connection connection = getConnection();

        result = authDao.insertUser(connection, user);

        if (result > 0) {
            commit(connection);
        } else {
            rollback(connection);
        }

        close(connection);

        return result;
    }
}
