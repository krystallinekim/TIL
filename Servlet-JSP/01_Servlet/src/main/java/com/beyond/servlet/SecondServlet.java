package com.beyond.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

// 어노테이션을 이용해서 할 수도 있다(tomcat 7버전 이후부터 사용가능)
// 따로 설정 안하면 클래스 이름과 같은 서블릿에 매핑된다.
@WebServlet(name = "second", urlPatterns = "/second.do")
public class SecondServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 이건 서버의 cmd에 출력되는 내용
        System.out.println(req.getContextPath());
        System.out.println(req.getServletPath());
        System.out.println(req.getServerName());
        System.out.println(req.getServerPort());
        System.out.println(req.getRemoteAddr());

        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"ko\">");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println("<title>SecondServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>SecondServlet이 반환하는 내용</h2>");
        // req를 이용하면 다양한 클라이언트 관련 정보를 얻을 수 있다.
        out.println("<p>ContextPath : " + req.getContextPath() + "</p>");
        out.println("<p>ServletPath : " + req.getServletPath() + "</p>");
        out.println("<p>ServerName : " + req.getServerName() + "</p>");
        out.println("<p>ServerPort : " + req.getServerPort() + "</p>");
        out.println("<p>RemoteAddr : " + req.getRemoteAddr() + "</p>");
        out.println("</body>");
        out.println("</html>");

    }

    @Serial
    private static final long serialVersionUID = 3641833957103034498L;
}
