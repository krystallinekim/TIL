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

@WebServlet(name = "authDeleteServlet", urlPatterns = "/auth/delete")
public class AuthDeleteServlet extends HttpServlet {

    private final AuthService authService;

    public AuthDeleteServlet() {
        this.authService = new AuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int result;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");

        result = authService.delete(user.getNo());

        if (result > 0) {
            request.setAttribute("msg", "정상적으로 탈퇴되었습니다.");
            request.setAttribute("location", "/");

            session.invalidate();
        } else {
            request.setAttribute("msg", "탈퇴를 실패했습니다.");
            request.setAttribute("location", "/auth/profile");
        }

        request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
    }

    @Serial
    private static final long serialVersionUID = -7646061915993205228L;
}
