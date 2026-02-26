<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%--<%@ page isErrorPage="true" %>--%>

<html>
<head>
    <title>공용 에러 페이지</title>
</head>
<body>
    <h1>에러가 발생했습니다. 관리자에게 문의하세요</h1>

<%--    &lt;%&ndash; 예외 객체 사용 가능 &ndash;%&gt;--%>
<%--    <%= exception %>--%>
<%--    <br>--%>
<%--    <%= exception.getMessage() %>--%>
<%--    <br>--%>
<%--    <%= exception.getClass() %>--%>


    <br>
    <br>
    <br>
    <button onclick="history.back();">이전 페이지로</button>
</body>
</html>
