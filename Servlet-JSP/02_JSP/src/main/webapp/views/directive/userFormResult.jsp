<%@ page import="java.util.Arrays" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>개인정보 출력</title>
    </head>
    <body>
        <h2>개인정보 출력</h2>
        <%
            String userName = request.getParameter("userName");
            String age = request.getParameter("age");
            String gender = request.getParameter("gender");
            String[] foods = request.getParameterValues("food");

            /*
            System.out.println(userName);
            System.out.println(age);
            System.out.println(gender);
            System.out.println(Arrays.toString(foods));
            */
        %>

        <p>
            이름: <%=userName%>
            <br>
            나이: <%=age%>
            <br>
            성별: <%=gender%>
            <br>
            선호 음식: <%=String.join(", ", foods)%>
        </p>


    </body>
</html>
