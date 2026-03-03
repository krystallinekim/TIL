<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>JSTL</title>
    </head>
    <body>
        <h1>JSP Core Tag Library</h1>
        <hr>
        <h2>1. 변수</h2>
        <h3>1) 변수 선언 및 초기화 - c:set</h3>
        <p>
            변수를 선언하고, 초기값을 대입하는 액션 태그<br>

            <%-- pageContext.setAttribute("num1", "10") --%>
            <c:set var="num1" value="${10}"/>
            num1 = ${ num1 } 또는 ${ pageScope.num1 }<br>

            <%-- request.setAttribute("num2", "3") --%>
            <c:set var="num2" value="${3}" scope="request"/>
            num2 = ${ num2 } 또는 ${ requestScope.num2 }<br>

            <c:set var="result" value="${ num1 + num2 }"/>
            num1 + num2 = ${ result }<br>

            <c:set var="colors" scope="application">
                red, green, blue
            </c:set>
            colors : ${ colors }<br>

        </p>
        <h3>2) 변수 삭제 - c:remove</h3>
        <p>
            선언한 변수를 삭제할 때 사용하는 태그<br>
            <c:set var="tmp" value="11111" scope="page"/>
            <c:set var="tmp" value="22222" scope="request"/>
            <c:set var="tmp" value="33333" scope="session"/>
            <c:set var="tmp" value="44444" scope="application"/>

            삭제 전: ${ pageScope.tmp }, ${ requestScope.tmp }, ${ sessionScope.tmp }, ${ applicationScope.tmp }<br>
            tmp: ${tmp}<br>

            <c:remove var="tmp" scope="page"/>

            page 삭제: ${ pageScope.tmp }, ${ requestScope.tmp }, ${ sessionScope.tmp }, ${ applicationScope.tmp }<br>
            tmp: ${tmp}<br>

            <c:remove var="tmp" scope="session"/>

            session 삭제: ${ pageScope.tmp }, ${ requestScope.tmp }, ${ sessionScope.tmp }, ${ applicationScope.tmp }<br>
            tmp: ${tmp}<br>

            <c:remove var="tmp"/>

            scope 조건 없이 삭제: ${ pageScope.tmp }, ${ requestScope.tmp }, ${ sessionScope.tmp }, ${ applicationScope.tmp }<br>
            tmp: ${tmp}<br>

        </p>
        <hr>
        <h2>2. 출력</h2>
        <h3>1) 출력 - c:out</h3>
        <p>
            클라이언트로 데이터를 출력할 때 사용하는 액션 태그<br>
            내부를 문자열로 전부 출력: <c:out value="<h4>데이터 출력하기</h4>"/><br>
            태그 적용 X (escapeXml = true): <c:out value="<h4>데이터 출력하기</h4>" escapeXml="true"/><br>
            태그 적용 O (escapeXml = false): <c:out value="<h4>데이터 출력하기</h4>" escapeXml="false"/><br>
            기본값 출력: <c:out value="${ tmp }" default="기본값 출력"/><br>
        </p>
        <hr>
        <h2>3. 조건문</h2>
        <h3>1) IF문 - c:if</h3>
        <p>
            자바의 if문과 같은 역할을 하는 태그<br>
            num1 = ${ num1 }, num2 = ${ num2 }<br>
            <c:if test="${ num1 < num2 }">
                <b>num1 < num2</b><br>
            </c:if>

            <c:if test="${ num1 > num2 }">
                <b>num1 > num2</b><br>
            </c:if>

        </p>
        <h3>2) switch문 - c:choose</h3>
        <p>
            자바의 switch 구문과 같은 역할을 하는 태그<br>

            <c:choose>
                <c:when test="${num1 > num2}">
                    <b>num1 > num2</b><br>
                </c:when>
                <c:when test="${num1 < num2}">
                    <b>num1 < num2</b><br>
                </c:when>
                <c:otherwise>
                    <b>num1 = num2</b><br>
                </c:otherwise>
            </c:choose>

        </p>
        <hr>
        <h2>4. 반복문</h2>
        <h3>c:forEach</h3>
        <p>

        </p>
        <h3>c:forTokens</h3>
        <p>

        </p>
        <hr>
        <h2>5. URL</h2>
        <h3>c:url</h3>
        <p>

        </p>

    </body>
</html>
