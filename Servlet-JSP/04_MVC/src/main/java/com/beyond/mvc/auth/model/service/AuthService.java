package com.beyond.mvc.auth.model.service;

import com.beyond.mvc.auth.model.vo.User;

public interface AuthService {
    User login(String userId, String userPwd);
}
