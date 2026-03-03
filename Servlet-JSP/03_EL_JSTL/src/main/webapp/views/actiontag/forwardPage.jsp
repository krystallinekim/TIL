<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>forward 페이지</title>
    </head>

    <body>
        <h2>jsp:forward</h2>
        <p>
            다른 페이지로 요청을 전달할 때 사용하는 액션 태그<br>
            기존 forward.jsp의 내용은 출력되지 않는다.<br>
        </p>
        <p>
            그래도 전달받은 값들은 사용이 가능하게 받는다.<br>
            userName: <%= request.getAttribute("userName") %><br>
            year: ${ param.year }<br>
        </p>

    </body>
</html>
