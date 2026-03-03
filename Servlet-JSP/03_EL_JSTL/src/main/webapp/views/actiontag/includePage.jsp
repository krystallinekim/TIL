<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>include 페이지</title>
    </head>

    <body>
        <%
            String year = "2026";
        %>
        -------------------------------
        <h4>include 페이지</h4>

        include된 페이지 내용<br>
        year = <%= year %><br>
        userName = ${ param.userName }<br><br>
        -------------------------------<br>
    </body>
</html>
