package com.beyond.mvc.auth.controller;

import com.beyond.mvc.auth.model.service.AuthService;
import com.beyond.mvc.auth.model.service.AuthServiceImpl;
import com.beyond.mvc.auth.model.vo.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;

@WebServlet(name = "authSignUpServlet", urlPatterns = "/auth/sign-up")
public class AuthSignUpServlet extends HttpServlet {

    private final AuthService authService;

    public AuthSignUpServlet() {
        this.authService = new AuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/auth/sign-up.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String hobbies = request.getParameterValues("hobby") != null ?
                String.join(",", request.getParameterValues("hobby")) : null;

        User user = User.builder()
                .username(request.getParameter("userName"))
                .password(request.getParameter("password"))
                .nickname(request.getParameter("nickname"))
                .phone(request.getParameter("phone"))
                .email(request.getParameter("email"))
                .address(request.getParameter("address"))
                .hobby(hobbies)
                .build();

        // System.out.println(user);

        int result = authService.save(user);

        if (result > 0) {
            // 회원가입 성공
            // 성공메시지 띄우고, 메인화면 이동
            request.setAttribute("msg", "회원가입 성공");
            request.setAttribute("location", "/");
        } else {
            // 회원가입 실패
            // 실패메시지 띄우고, 회원가입 페이지로 다시 이동
            request.setAttribute("msg", "회원가입 실패");
            request.setAttribute("location", "/auth/sign-up");
        }

        // 메시지 보내기(포워드)
        request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
    }

    @Serial
    private static final long serialVersionUID = -6741201100610426L;
}
