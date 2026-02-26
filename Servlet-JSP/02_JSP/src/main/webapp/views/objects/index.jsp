<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>내장 객체</title>
</head>
<body>
    <h1>내장 객체</h1>
<%
    // 내장 객체로 이미 자동으로 추가된 객체들은 새로 선언할 수 없다.
    // String out;
    // String request;
    // String session;
%>
    <h2>1. request</h2>
    <p>
        웹 브라우저의 요청 정보를 가지는 객체
    </p>
    <h3>헤더와 관련된 메소드</h3>
    <%
        Enumeration<String> headerNames = request.getHeaderNames();
    %>
    <table border="1">
        <tr>
            <th>헤더 이름</th>
            <th>헤더 값</th>
        </tr>
    <%
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
    %>
        <tr>
            <td><%=headerName%></td>
            <td><%=request.getHeader(headerName)%></td>
        </tr>
    <%
        }
    %>
    </table>
</body>
</html>
