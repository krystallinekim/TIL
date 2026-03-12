<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<c:set var="contextPath" value="${ pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Spring MVC</title>
</head>
<body>
    <h1>Hello World!</h1>
    <img src="${ contextPath }/resources/images/pika.png" alt="Pikachu" width="500"/><br>

    <form action="${ contextPath }/auth/login" method="post">
        <label for="userId">아이디 : </label>
        <input type="text" id="userName" name="userName" required><br>
        <label for="userPwd">비밀번호: </label>
        <input type="password" id="password" name="password" required><br>

        <label><input type="checkbox" name="hobby" value="운동" checked>운동</label>
        <label><input type="checkbox" name="hobby" value="등산">등산</label>
        <label><input type="checkbox" name="hobby" value="독서">독서</label><br>

        <br>

        <input type="button" value="회원가입">
        <input type="submit" value="로그인">
    </form>
</body>
</html>
