<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="ko">
    <head>
        <meta charset="utf-8">
        <title>영역 객체</title>
    </head>
    <body>
        <h1>영역 객체</h1>

        <h2>1. Session <-> Application</h2>
        <%
            session.setAttribute("address", "Seoul");
            application.setAttribute("name", "Hong");
        %>

        <a href="scopeTest1.jsp">View Details</a>
        <br>
        <h2>2. Request <-> Page</h2>
        <a href="scopeTest2.jsp">View Details</a>
    </body>
</html>
