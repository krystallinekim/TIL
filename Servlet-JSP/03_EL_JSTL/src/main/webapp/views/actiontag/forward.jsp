<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>표준 액션 태그</title>
    </head>

    <body>
        <h1>이 내용은 출력 안됨</h1>
        <%
            request.setAttribute("userName", "홍길동");
        %>

        <jsp:forward page="forwardPage.jsp">
            <jsp:param name="year" value="2026"/>
        </jsp:forward>

    </body>
</html>
