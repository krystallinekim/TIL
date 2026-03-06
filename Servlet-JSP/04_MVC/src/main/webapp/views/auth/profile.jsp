<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:set var="contextPath" value="${ pageContext.request.contextPath }"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 정보</title>
</head>
<body>
<h2>회원 정보</h2>

<form action="${ contextPath }/auth/profile" method="post">
    <table>
        <tr>
            <th><label for="userId">아이디</label></th>
            <td><input type="text" id="userId" name="userId" value="${ loginUser.username }" readonly required/></td>
        </tr>
        <tr>
            <th><label for="nickname">닉네임</label></th>
            <td><input type="text" id="nickname" name="nickname" value="${ loginUser.nickname }" required></td>
        </tr>
        <tr>
            <th><label for="phone">휴대전화</label></th>
            <td><input type="text" id="phone" name="phone" value="${ loginUser.phone }" maxlength="11"></td>
        </tr>
        <tr>
            <th><label for="email">이메일</label></th>
            <td><input type="text" id="email" name="email" value="${ loginUser.email }"></td>
        </tr>
        <tr>
            <th><label for="address">주소</label></th>
            <td><input type="text" id="address" name="address" value="${ loginUser.address }"></td>
        </tr>
        <tr>
            <th>취미</th>
            <td>
                <label><input type="checkbox" name="hobby" value="운동"
                ${ fn:contains(loginUser.hobby, '운동') ? 'checked' : '' }>운동</label>
                <label><input type="checkbox" name="hobby" value="등산"
                ${ fn:contains(loginUser.hobby, '등산') ? 'checked' : '' }>등산</label>
                <label><input type="checkbox" name="hobby" value="독서"
                ${ fn:contains(loginUser.hobby, '독서') ? 'checked' : '' }>독서</label>
                <br>
                <label><input type="checkbox" name="hobby" value="게임"
                ${ fn:contains(loginUser.hobby, '게임') ? 'checked' : '' }>게임</label>
                <label><input type="checkbox" name="hobby" value="여행"
                ${ fn:contains(loginUser.hobby, '여행') ? 'checked' : '' }>여행</label>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="정보수정">
                <input type="button" onclick="userDelete();" value="탈퇴">
            </td>
        </tr>
    </table>
</form>
<script>
    function userDelete() {
        if (confirm("정말로 탈퇴하시겠습니까?")) {
            location.replace('${ contextPath }/auth/delete');
        }
    }
</script>
</body>
</html>
