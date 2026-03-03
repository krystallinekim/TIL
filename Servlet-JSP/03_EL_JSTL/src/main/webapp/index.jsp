<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>EL/JSTL</title>
    </head>
    <body>
        <h1>EL(Expression Language)</h1>
        <p>
            표현식(Expression) 태그를 대신하여 클라이언트에 출력하고자 하는 값들을 좀 더 간결하게 사용하는 방법이다.
        </p>

        <h2>1. EL 내장 객체</h2>
        <a href="<%=request.getContextPath()%>/el.do">View Details</a>

        <h2>2. EL 연산자</h2>
        <a href="${ pageContext.request.contextPath }/views/el/elOperators.jsp">View Details</a>

        <br>
        <h1>JSTL(JSP Standard Tag Library)</h1>

        <h2>JSP Action Tag</h2>

        <p>
            JSP에서 자바코드를 직접 입력하지 않아도 특정 작업을 수행할 수 있도록 사용하는 태그
        </p>
        <h3>표준 액션 태그</h3>
        <p>
            별도의 라이브러리 없이 사용 가능한 기본 제공 액션 태그
        </p>
        <a href="${ pageContext.request.contextPath }/views/actiontag/include.jsp">include</a>
        <a href="${ pageContext.request.contextPath }/views/actiontag/forward.jsp">forward</a>
    </body>
</html>
