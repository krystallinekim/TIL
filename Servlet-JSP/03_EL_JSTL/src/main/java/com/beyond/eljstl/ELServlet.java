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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 세션, 어플리케이션 객체(요청 객체는 내장객체로 제공)
        HttpSession session = request.getSession();
        ServletContext application = request.getServletContext();

        // 요청영역에 데이터를 저장
        request.setAttribute("scope", "request");
        request.setAttribute("classRoom", 1);
        request.setAttribute("student", new Student("홍길동", 34, 70, 30));

        // 세션 영역에 데이터 저장
        session.setAttribute("scope", "session");
        session.setAttribute("classRoom", 2);
        session.setAttribute("student", new Student("이몽룡", 24, 80, 50));

        // 어플리케이션 영역에 데이터 저장
        application.setAttribute("scope", "application");
        application.setAttribute("classRoom", 3);
        application.setAttribute("student", new Student("성춘향", 18, 90, 70));



        // 비즈니스 로직 수행 후, RequestDispatcher 객체를 이용해 포워딩
        request.getRequestDispatcher("/views/el/el.jsp").forward(request, response);
    }

    @Serial
    private static final long serialVersionUID = -9182725307739245528L;
}
