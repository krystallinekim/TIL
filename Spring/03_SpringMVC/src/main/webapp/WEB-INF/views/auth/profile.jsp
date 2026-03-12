<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<c:set var="contextPath" value="${ pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원정보</title>
</head>
<body>
<h1>회원정보</h1>

<c:if test="${not empty user}">
    사용자 번호: ${ user.no() }<br>
    사용자 ID: ${ user.userName() }<br>
    사용자 주소: ${ user.address() }<br>
</c:if>

<c:if test="${empty user}">
    회원정보가 존재하지 않습니다<br>
</c:if>


</body>
</html>
