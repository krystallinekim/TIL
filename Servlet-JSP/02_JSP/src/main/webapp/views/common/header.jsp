<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
  String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
%>

<%--전체 문서가 아니라, 일부 문서만 가지는 형태--%>
<header>
  <h1> 지시자 태그</h1>
  <p>
    오늘 날짜는 <%=today%>입니다.
  </p>
</header>
