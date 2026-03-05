package com.beyond.mvc.auth.model.service;

import com.beyond.mvc.auth.model.dao.AuthDao;
import com.beyond.mvc.auth.model.dao.AuthDaoImpl;
import com.beyond.mvc.auth.model.vo.User;

public class AuthServiceImpl implements AuthService {
    private final AuthDao authDao;

    public AuthServiceImpl() {
        this.authDao = new AuthDaoImpl();
    }

    @Override
    public User login(String userId, String userPwd) {
        User user;

        user = authDao.getUserById(userId);

        if (user == null || !user.getPassword().equals(userPwd)) {
            return null;
        }

        return user;
    }
}
