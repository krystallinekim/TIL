<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="ko">
    <head>
        <meta charset="utf-8">
        <title>영역 객체</title>
    </head>
    <body>
        <h1>영역 객체</h1>
        <h2>Request <-> Page</h2>
        <p>
            Request영역 데이터: <%=request.getAttribute("gender")%>
            <br>
            Page영역 데이터: <%=pageContext.getAttribute("age")%>
        </p>
        <p>
            기존 페이지에서 포워드를 했기 때문에, 요청 내용은 그대로 남는다.
            <br>
            대신, 페이지를 이동해서 페이지 영역 데이터는 없어짐
            <br>
            리다이렉트시에는 요청을 새로 보내기 때문에, 요청 영역 데이터도 사라진다.
        </p>

    </body>
</html>
