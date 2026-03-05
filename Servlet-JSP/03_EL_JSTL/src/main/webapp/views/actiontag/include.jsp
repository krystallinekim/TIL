<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>jsp:include 액션 태그</title>
</head>
<body>
    <h2>jsp:include 액션 태그</h2>
    <p>
        jsp:include 액션 태그는 다른 페이지를 포함시킬 때 사용하는 액션 태그이다.
    </p>

    <h3>1. include 지시어</h3>
    <p>
        다른 페이지를 포함하는 JSP 파일이 컴파일되기 전에 페이지가 포함된다.
    </p>
    <%--
    <%@ include file="includePage.jsp"%>

    <p>
        includePage.jsp에서 선언된 변수를 그대로 사용할 수 있다.
        include.jsp의 year 변수의 값은 <%= year %> 입니다.
    </p>

    <%
        // 현재 페이지와 포함된 페이지의 변수명이 중복되서 에러가 발생한다.
        int year = 2026;
    %>
    --%>

    <h3>2. jsp:include 액션 태그</h3>
    <p>
        다른 페이지를 포함하는 JSP 파일이 화면에 출력되는 시점(런타임)에 포함된다.
    </p>

    <%-- jsp:param 액션 태그를 사용해서 포함되는 페이지로 값을 전달할 수 있다. --%>
    <jsp:include page="includePage.jsp">
        <jsp:param name="userName" value="홍길동"/>
    </jsp:include>

    <%--
    <p>
        include.jsp의 year 변수의 값은 <%= year %> 입니다.
    </p>
    --%>

    <%
        int year = 2027;
    %>

    <p>
        include.jsp의 year 변수의 값은 <%= year %> 입니다.
    </p>

    <br><br><br><br><br><br>
    <br><br><br><br><br><br>
    <br><br><br><br><br><br>
    <br><br><br><br><br><br>
    <br><br><br><br><br><br>
    <br><br><br><br><br><br>
    <br><br><br><br><br><br>
</body>
</html>
