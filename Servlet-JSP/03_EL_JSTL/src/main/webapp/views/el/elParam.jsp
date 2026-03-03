<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>EL-Parameter</title>
    </head>

    <body>
        <h1>주문내역</h1>

        <%--
        <h2>표현식 태그 사용</h2>
        제품명: <%= request.getParameter("pName") %>
        <br>
        수량: <%= request.getParameter("pCount") %>
        <br>
        옵션1: <%= request.getParameterValues("option")[0]%>
        <br>
        옵션2: <%= request.getParameterValues("option")[1]%>
        <br>
        --%>

        <h2>EL 사용 시</h2>
        제품명: ${ param.pName }
        <br>
        수량: ${ param.pCount }
        <br>
        옵션1: ${ paramValues.option[0] }
        <br>
        옵션2: ${ paramValues.option[1] }
        <br>
    </body>
</html>
