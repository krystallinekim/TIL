<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="ko">
    <head>
        <meta charset="utf-8">
        <title>영역 객체</title>
    </head>
    <body>
        <h1>영역 객체</h1>
        <h2>Session <-> Application</h2>
        <p>
            Session영역 데이터: <%=session.getAttribute("address")%>
            <br>
            Application영역 데이터: <%=application.getAttribute("name")%>
        </p>
        <p>
            브라우저를 닫았다 다시 들어오면, Session이 초기화되므로 address는 날아가지만, 돌아가는 Application을 끈 것이 아니므로 name은 그대로 남아있다.
        </p>

    </body>
</html>
