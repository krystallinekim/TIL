<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String year = "2026";
%>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>include 페이지</title>
</head>
<body>
    <h4>include 페이지</h4>
    <p>
        includePage.jsp의 year 변수의 값은 <%= year %> 입니다.
    </p>

    userName : <%= request.getParameter("userName") %>
    <br>
    userName : ${ param.userName }
</body>
</html>
