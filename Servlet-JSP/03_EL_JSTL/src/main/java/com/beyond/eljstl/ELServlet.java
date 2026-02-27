package com.beyond.eljstl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;

@WebServlet("/el.do")
public class ELServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 요청영역에 데이터를 저장
        request.setAttribute("classRoom", 1);
        request.setAttribute("student", new student("홍길동", 34, 70, 30));
        request.setAttribute("scope", "request");

        // 비즈니스 로직 수행 후, RequestDispatcher 객체를 이용한 포워딩
        request.getRequestDispatcher("/views/el/el.jsp").forward(request, response);
    }

    @Serial
    private static final long serialVersionUID = -9182725307739245528L;
}
