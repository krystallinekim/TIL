<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>forward 페이지</title>
</head>
<body>
    <h2>forward 페이지</h2>

    사용자 이름 : ${ userName }
    <br>
    사용자 나이 : ${ userAge }
    <br>
    주소 : ${ param['address'] }
</body>
</html>
