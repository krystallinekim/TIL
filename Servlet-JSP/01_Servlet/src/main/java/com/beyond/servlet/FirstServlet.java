package com.beyond.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.time.LocalDateTime;

// 1. 서블릿 클래스 작성 - HttpServlet 상속
// @WebServlet("/first.do")
public class FirstServlet extends HttpServlet {

    // 2. doGet, doPost 등을 재정의하면 된다.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("사용자로부터 GET 요청을 받음.");
        // a. 한글이 깨지는 걸 방지하기 위해 MIME 타입으로 작성한 텍스트 타입 정의
        resp.setContentType("text/html;charset=utf-8");

        // b. 응답 화면을 출력하기 위해 출력 스트림을 얻어옴
        PrintWriter out = resp.getWriter();

        // c. 응답 화면 출력
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"ko\">");
        out.println("<head>");
        // 한글 깨짐 방지는 그냥 문서 전체에 한글 깨짐 방지용 인코딩 양식을 설정해줘도 된다.
        // out.println("<meta charset=\"utf-8\">");
        out.println("<title>FirstServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>FirstServlet이 반환하는 내용</h2>");
        // 요청하는 시간에 따라 현재시각이 계속 바뀌기 때문에 동적인 페이지
        out.println("<p>현재 시간 : " + LocalDateTime.now() + "</p>");
        out.println("</body>");
        out.println("</html>");

        // html 쓰는 게 너무 길다. 이거 불편해서 나온게 JSP

    }

    @Serial
    private static final long serialVersionUID = -5206829137206333067L;

    // 3. 클래스 작성 이후, 서블릿 매핑을 통해 등록해 줘야 한다.
    // 3-1. web.xml에 서블릿 매핑 코드를 작성하면 됨
    // 3-2. 서블릿 클래스에 바로 매핑 어노테이션을 작성할 수도 있음
}
