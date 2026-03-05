<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>

<c:set var="contextPath" value="${ pageContext.request.contextPath }" />

<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>Hello World</title>
    </head>
    <body>
        <h2>Hello World!</h2>

        <form action="${ contextPath }/auth/login" method="post">
            <label for="userId">아이디 : </label>
            <input type="text" name="userId" id="userId" required>

            <br>

            <label for="userPwd">비밀번호 : </label>
            <input type="password" name="userPwd" id="userPwd" required>

            <br><br>

            <input type="button" onclick="alert('미구현');" value="회원가입">
            <input type="submit" value="로그인">
        </form>
    </body>
</html>