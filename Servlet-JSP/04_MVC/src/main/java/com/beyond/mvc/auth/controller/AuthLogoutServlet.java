package com.beyond.mvc.auth.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Serial;

@WebServlet(name = "authLogoutServlet", urlPatterns = "/auth/logout")
public class AuthLogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 로그아웃
        // 1. 세션을 받아와서
        HttpSession session = request.getSession();

        // 2. 세션을 삭제
        session.invalidate();

        // 3. 메인화면으로 리다이렉트
        response.sendRedirect(request.getContextPath() + "/");
    }

    @Serial
    private static final long serialVersionUID = 8595001314052904186L;
}
