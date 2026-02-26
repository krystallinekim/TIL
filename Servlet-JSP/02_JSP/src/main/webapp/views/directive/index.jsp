<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ page errorPage="/views/error/error500.jsp"%>--%>
<%--<%@ page errorPage="../error/error500.jsp"%>--%>

<%--<%--%>
<%--    // 에러 페이지 확인--%>
<%--    int result = 10 / 0;--%>
<%--    // HTTP 500 - 내부 서버 오류 발생--%>
<%--%>--%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>지시자 태그</title>
</head>
<body>
    <%--시멘틱 태그: 페이지 구조에서 변하지 않을 부분 고정하는 역할, 문서 구조를 정해놓는다--%>
    <%@ include file="/views/common/header.jsp"%>
    <main>
        <form action="userFormResult.jsp" method="post">
            <label for="userName">이름: </label>
            <input type="text" name="userName" id="userName">

            <br>

            <label for="age">나이: </label>
            <input type="text" name="age" id="age">

            <br>

            <label>성별: </label>
            <label>
                <input type="radio" name="gender" value="남성" checked>남성
            </label>
            <label>
                <input type="radio" name="gender" value="여성">여성
            </label>

            <br>

            <label>좋아하는 음식:</label>
            <label><input type="checkbox" name="food" value="한식">한식</label>
            <label><input type="checkbox" name="food" value="양식">양식</label>
            <label><input type="checkbox" name="food" value="중식">중식</label>
            <label><input type="checkbox" name="food" value="일식">일식</label>
            <label><input type="checkbox" name="food" value="분식">분식</label>

            <br>
            <br>

            <input type="submit" value="전송">
            <input type="reset" value="리셋">
        </form>

    </main>
    <%@ include file="../common/footer.jsp"%>
</body>
</html>
