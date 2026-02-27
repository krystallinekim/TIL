<%@ page import="java.util.Enumeration" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>내장 객체</title>
</head>
<body>
    <h1>내장 객체</h1>
<%--<%
    // 내장 객체로 이미 자동으로 추가된 객체들은 새로 선언할 수 없다.
    String out;
    String request;
    String session;
%>--%>
    <h2>1. request</h2>
    <p>
        웹 브라우저의 요청 정보를 가지는 객체
    </p>
    <h3>헤더와 관련된 메소드</h3>
    <p>
        헤더는 HTTP 메시지의 메타데이터를 담고 있는 부분
        <br>
        `request.getHeader(헤더이름)`를 이용하면 특정 헤더의 값을 가져올 수 있다.
        <br>
        `request.getHeaderNames()`는 전체 헤더 목록을 Enumeration 객체로 받아옴
    </p>
    <%
        Enumeration<String> headerNames = request.getHeaderNames();
    %>
    <table border="1">
        <tr>
            <th>이름</th>
            <th>값</th>
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
    <br>
    <h3>URL/URI, 요청 방식과 관련된 메소드</h3>

    <table border="1">
        <tr>
            <th>이름</th>
            <th>값</th>
        </tr>
        <tr>
            <td>프로토콜</td>
            <td><%=request.getProtocol()%></td>
        </tr>
        <tr>
            <td>요청 방식</td>
            <td><%=request.getMethod()%></td>
        </tr>
        <tr>
            <td>URL(Uniform Resource Location, 네트워크 기준 위치)</td>
            <td><%=request.getRequestURL()%></td>
        </tr>
        <tr>
            <td>URI(Uniform Resource Identifier, 현재 서버 기준 식별자)</td>
            <td><%=request.getRequestURI()%></td>
        </tr>
        <tr>
            <td>Query String(`?`뒷부분)</td>
            <td><%=request.getQueryString()%></td>
        </tr>
        <tr>
            <td>Context Path(현재 웹 애플리케이션 경로)</td>
            <td><%=request.getContextPath()%></td>
        </tr>
    </table>

</body>
</html>
