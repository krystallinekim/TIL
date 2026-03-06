package com.beyond.mvc.common.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

//@WebFilter(filterName = "passwordCheckFilter", servletNames = { "authSignUpServlet" })
public class PasswordCheckFIlter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String userPwd = request.getParameter("userPwd");
        String userPwd2 = request.getParameter("userPwd2");

        if (!userPwd.equals(userPwd2)) {

            request.setAttribute("msg", "비밀번호가 다릅니다.");
            request.setAttribute("location", "/auth/sign-up");

            request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);

            return;
        }

        filterChain.doFilter(request, response);
    }
}
