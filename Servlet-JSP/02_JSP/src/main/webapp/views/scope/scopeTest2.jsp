<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    request.setAttribute("gender", "Male");
    pageContext.setAttribute("age", "34");

    System.out.println(request.getAttribute("gender"));
    System.out.println(pageContext.getAttribute("age"));

    // response.sendRedirect("scopeTest3.jsp");
    pageContext.forward("scopeTest3.jsp");
%>
