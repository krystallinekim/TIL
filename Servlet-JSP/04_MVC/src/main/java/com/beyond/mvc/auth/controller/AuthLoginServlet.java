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

        // System.out.println(userId + ", " + userPwd);

        if (loginUser != null) {
            session = request.getSession();

            session.setAttribute("loginUser", loginUser);

            // 로그인 완료 시 메인 화면으로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/");

        } else {
            //로그인 실패 시 실행할 로직 - 메시지를 띄우고 다시메인 화면으로 이동
            // 1. 공용 메시지 페이지에 1)전달할 메시지, 2) 이후 이동할 메시지 를 전달
            request.setAttribute("msg", "아이디나 비밀번호가 일치하지 않습니다.");
            request.setAttribute("location", "/");

            // 2. request 객체의 데이터를 그대로 전달해야 하므로 forward로 전달
            request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
        }
    }
}
