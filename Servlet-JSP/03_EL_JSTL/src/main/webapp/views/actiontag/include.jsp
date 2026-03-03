<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>표준 액션 태그</title>
    </head>

    <body>
        <h2>jsp:include</h2>
        <p>
            다른 페이지를 포함 시킬 때 사용하는 액션 태그
        </p>
        <h3>include 지시어</h3>
        <p>
            다른 페이지를 포함하는 JSP 파일이 컴파일 되기 전에 페이지가 포함된다.
        </p>

        <%-- <%@include file="includePage.jsp"%>--%>
        <%-- <% int year = 2026; %> --%>
        <%-- year = <%= year %><br>--%>

        <p>
            컴파일 전에 페이지가 포함(서블릿으로 변환)되므로, 포함한 페이지의 지역변수를 기존 페이지에서도 이용할 수 있어진다.<br>
            그래서 같은 변수명을 사용할 수 없다.
        </p>

        <h3>액션태그 사용 시</h3>
        <p>
            다른 페이지를 포함하는 JSP 파일이 화면에 출력되는 시점(런타입)에 포함된다.
        </p>

        <jsp:include page="includePage.jsp"/>
        <% int year = 2025; %>
        year = <%= year %>

        <h4>jsp:param 전달 시</h4>
        <jsp:include page="includePage.jsp">
            <jsp:param name="userName" value="홍길동"/>
        </jsp:include>

    </body>
</html>
