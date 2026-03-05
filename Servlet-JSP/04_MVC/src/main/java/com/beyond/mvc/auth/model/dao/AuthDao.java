package com.beyond.mvc.auth.model.dao;

import com.beyond.mvc.auth.model.vo.User;

public interface AuthDao {
    User getUserById(String userId);

    int insertUser(User user);
}
