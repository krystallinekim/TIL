package com.beyond.eljstl;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Serial;

@WebServlet("/el.do")
public class ELServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = -2952761693551385580L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext application = request.getServletContext();

        // Request 영역에 데이터를 저장
        request.setAttribute("classRoom", 1);
        request.setAttribute("student", new Student("홍길동", 34, 70, 70));
        request.setAttribute("scope", "request");

        // Session 영역에 데이터를 저장
        session.setAttribute("classRoom", 2);
        session.setAttribute("student", new Student("성춘향", 24, 90, 90));
        session.setAttribute("scope", "session");

        // Application 영역에 데이터를 저장
        application.setAttribute("classRoom", 3);
        application.setAttribute("student", new Student("이몽룡", 24, 80, 80));
        application.setAttribute("scope", "application");

        request.getRequestDispatcher("/views/el/el.jsp").forward(request, response);
    }
}
