<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>

<html>
<head>
    <title>에러 발생</title>
</head>
<body>
    <h1>에러가 발생했습니다. 관리자에게 문의하세요</h1>

    <%-- 예외 객체 사용 가능 --%>
    <%= exception %>
    <br>
    <%= exception.getMessage() %>
    <br>
    <%= exception.getClass() %>


    <br>
    <br>
    <br>
    <button onclick="history.back();">이전 페이지로</button>
</body>
</html>
