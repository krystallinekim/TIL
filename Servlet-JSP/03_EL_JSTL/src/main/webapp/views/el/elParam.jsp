<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>주문 내역</title>
</head>
<body>
    <h2>주문 내역</h2>
    <%-- 상품명 : ${ param.pName } --%>
    상품명 : ${ param['pName'] }
    <br>
    수량 : ${ param.pCount }
    <br>
    옵션(param.option) : ${ param.option }
    <br>
    옵션 1 : ${ paramValues['option'][0] }
    <br>
    옵션 2 : ${ paramValues.option[1] }
</body>
</html>
