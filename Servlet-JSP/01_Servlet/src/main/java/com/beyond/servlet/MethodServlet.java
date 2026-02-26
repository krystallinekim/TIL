package com.beyond.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.util.Arrays;

@WebServlet("/method.do")
public class MethodServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = -3351415601818853875L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userName = req.getParameter("userName");
        String age = req.getParameter("age");
        String gender = req.getParameter("gender");
        // food 값이 여러개 들어와서 하나만 저장됨
        // String foods = req.getParameter("foods");
        String[] foods = req.getParameterValues("food");

        // 콘솔창에 출력될 내용
        System.out.println(userName);
        System.out.println(age);
        System.out.println(gender);
        System.out.println(Arrays.toString(foods));

        // 웹에 출력될 내용
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"ko\">");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println("<title>개인정보 출력</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>개인정보 출력</h2>");
        out.println("<p>이름 : " + userName + "</p>");
        out.println("<p>나이 : " + age + "</p>");
        out.println("<p>성별 : " + gender + "</p>");
        out.println("<p>선호 음식 : ");
        out.print(String.join(", ", foods));
        out.println("</p>");
        out.println("</body>");
        out.println("</html>");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 기존 doPost의 경우 405 에러만 던지도록 설정되어 있다.
        // super.doPost(req, resp);

        // get이나 post나 하는 동작은 같음. 단, URL에 표시되느냐 request body에 데이터를 보내느냐의 차이
        this.doGet(req, resp);
    }
}
