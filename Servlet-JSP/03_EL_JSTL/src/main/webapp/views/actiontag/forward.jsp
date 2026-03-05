<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>jsp:forward 액션 태그</title>
</head>
<body>
    <h2>jsp:forward 액션 태그</h2>
    <p>
        jsp:forward 액션 태그는 다른 페이지로 요청을 전달할 때 사용하는 액션 태그이다.
    </p>

    <%
        request.setAttribute("userName", "홍길동");
        request.setAttribute("userAge", 34);
    %>

    <jsp:forward page="forwardPage.jsp">
        <jsp:param name="address" value="서울특별시"/>
    </jsp:forward>
</body>
</html>
