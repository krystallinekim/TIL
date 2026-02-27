<%--html과 똑같이 생겼다--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="utf-8">
        <title>JSP(Java Server Page)</title>
    </head>
    <body>
        <h1>JSP(Java Server Page)</h1>
        <h2>1. JSP</h2>
        <p>
            JSP는 HTML 코드 안에서 스크립트 형태로 자바 언어를 사용하여 동적인 페이지를 구현한다.
        </p>

        <h2>2. JSP 요소(Elements)</h2>
        <p>
            JSP는 동적인 페이지를 만들기 위해 다양한 스크립팅 요소를 제공한다.
        </p>

        <ol>
            <li>주석 태그</li>
            <li>지시자 태그</li>
            <li>선언문 태그</li>
            <li>스크립트릿 태그</li>
            <li>표현식 태그</li>
        </ol>

        <a href="<%=request.getContextPath()%>/views/directive/">지시자 태그</a>
        <br>
        <a href="<%=request.getContextPath()%>/views/elements/">기타 태그</a>

<%--        <a href="/jsp/views/directive/">지시자 태그</a>--%>
<%--        <br>--%>
<%--        <a href="/jsp/views/elements/">기타 태그</a>--%>

        <h2>3. JSP 내장 객체</h2>
        <p>
            JSP가 서블릿으로 변환될 때 자바 파일에 자동으로 추가되는 객체들
        </p>
        <a href="/jsp/views/objects/">내장 객체</a>

    </body>
</html>
