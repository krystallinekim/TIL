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

@WebServlet(name = "authProfileServlet", urlPatterns = "/auth/profile")
public class AuthProfileServlet extends HttpServlet {

    private final AuthService authService;

    public AuthProfileServlet() {
        this.authService = new AuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/auth/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int result;
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        String hobbies = request.getParameterValues("hobby") != null ?
            String.join(",", request.getParameterValues("hobby")) : null;

        loginUser.setNickname(request.getParameter("nickname"));
        loginUser.setPhone(request.getParameter("phone"));
        loginUser.setEmail(request.getParameter("email"));
        loginUser.setAddress(request.getParameter("address"));
        loginUser.setHobby(hobbies);

        result = authService.save(loginUser);

        if (result > 0) {
            request.setAttribute("msg", "회원정보를 수정했습니다.");
        } else {
            request.setAttribute("msg", "회원정보 수정을 실패했습니다.");
        }

        request.setAttribute("location", "/auth/profile");
        request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
    }

    @Serial
    private static final long serialVersionUID = -7886339242497175702L;
}
