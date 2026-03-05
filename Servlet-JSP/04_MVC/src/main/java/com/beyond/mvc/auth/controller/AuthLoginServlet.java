package com.beyond.mvc.auth.controller;

import com.beyond.mvc.auth.model.service.AuthService;
import com.beyond.mvc.auth.model.service.AuthServiceImpl;
import com.beyond.mvc.auth.model.vo.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Serial;

@WebServlet(name = "authLoginServlet", urlPatterns = "/auth/login")
public class AuthLoginServlet extends HttpServlet {
    private final AuthService authService;

    public AuthLoginServlet() {
        authService = new AuthServiceImpl();
    }

    @Serial
    private static final long serialVersionUID = -2228580693258078689L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session;
        String userId = request.getParameter("userId");
        String userPwd = request.getParameter("userPwd");

        User loginUser = authService.login(userId, userPwd);

        if (loginUser != null) {
            session = request.getSession();

            session.setAttribute("loginUser", loginUser);
        }

        // System.out.println(loginUser);

        response.sendRedirect(request.getContextPath() + "/");
    }
}
