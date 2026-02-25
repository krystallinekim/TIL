package com.beyond.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/life.do")
public class LifeServlet extends HttpServlet {

    public LifeServlet() {
        System.out.println("서블릿 객체 생성");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("init() 메소드 호출");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("service() 메소드 호출");

        // 요청 방식(GET, POST)에  따라 메소드(doGet, doPost) 호출
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet() 메소드 호출");
    }

    @Override
    public void destroy() {
        System.out.println("destroy() 메소드 호출");
    }
}
