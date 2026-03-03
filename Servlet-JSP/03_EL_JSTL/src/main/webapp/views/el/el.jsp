<%@ page import="com.beyond.eljstl.Student" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--
<%
    // request 영역의 속성(Attribute) 가져오기
    // int classRoom = (Integer) request.getAttribute("classRoom");
    // Student student = (Student) request.getAttribute("student");
    // session 영역
    int classRoom = (Integer) session.getAttribute("classRoom");
    Student student = (Student) session.getAttribute("student");
%>
--%>

<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>EL</title>
    </head>
    <body>
        <h1>EL(Expression Language)</h1>
<%--
        <h2>0. 기존 방식으로 접근</h2>
        <p>
            classroom: <%= classRoom %>
            <br>
            student: <%= student.getName() %>(<%= student.getAge() %>)
            <br>
            score: 수학 <%= student.getMath() %>점 / 영어 <%= student.getEnglish() %>점
            <br><br>
            직접 영역에서 객체를 꺼내와야 하고, 그 객체의 getter 메소드를 이용해 속성값을 출력할 수 있다.
        </p>
--%>

        <h2>1. EL 사용 시</h2>
        <p>
            -------------------------------------------
            <br>
            scope: ${scope}
            <br>
            classroom: ${classRoom}
            <br>
            student: ${student.name}(${student.age})
            <br>
            score: 수학 ${student.math}점 / 영어 ${student.english}점
            <br>
            -------------------------------------------
            <br>
            따로 값을 꺼내오지 않아도 값을 출력할 수 있다.
            <br>
            -> EL은 영역객체에 지정된 속성명을 검색해 존재할 경우 속성값을 가져올 수 있다.
            <br>
            코드만 보면 EL에서 객체의 private 필드에 직접 접근하는 것처럼 보인다.
            <br>
            -> 실제로 직접 접근하는건 아니고, 내부적으로 해당 필드의 getter 메소드를 이용해 가져온다.
        </p>

        <h2>2. session 영역의 데이터를 가져오고 싶을 경우</h2>
        <p>
            request 영역에도 데이터가 있고, session 영역에도 같은 이름의 데이터가 있어 일반적으로 꺼내오면 request 영역 데이터 먼저 꺼내온다.
            <br>
            -> 내장 객체를 이용함
            <br>
            -------------------------------------------
            <br>
            scope: ${sessionScope.scope}
            <br>
            classroom: ${sessionScope.classRoom}
            <br>
            student: ${sessionScope.student.name}(${sessionScope.student.age})
            <br>
            score: 수학 ${sessionScope.student.math}점 / 영어 ${sessionScope.student.english}점
            <br>
            -------------------------------------------
        </p>

        <h3>영역 개체에 저장된 속성명이 같은 경우</h3>
        <%
            // Page 영역에 데이터 저장
            pageContext.setAttribute("scope", "page");
            pageContext.setAttribute("classRoom", 4);
            pageContext.setAttribute("student", new Student("임꺽정", 45, 20, 20));
        %>
        <p>
            scope: ${scope}
            <br>
            page: ${pageScope.scope}
            <br>
            request: ${requestScope.scope}
            <br>
            session: ${sessionScope.scope}
            <br>
            application: ${applicationScope.scope}
        </p>

        <h2>3. ContextPath 가져오기</h2>
        <h3>표현식 태그 사용</h3>
        ContextPath: <%= request.getContextPath() %>
        <br>
        <h3>EL 사용 시</h3>
        바로 접근하는 방법이 없어, request 객체에 먼저 접근한 뒤 getter를 이용한다.
        <br>
        ContextPath: ${ pageContext.request.contextPath }

        <h2>4. 헤더 접근하기</h2>

        <h3>표현식 태그 사용</h3>
        Host: <%= request.getHeader("Host") %>
        <br>
        User-Agent: <%= request.getHeader("User-Agent") %>
        <br>

        <h3>EL 사용 시</h3>
        Host: ${ header.Host }
        <br>
        User-Agent: ${ header['User-Agent'] }
        <br>

        <h2>5. 쿠키 접근하기</h2>

        <h3>표현식 태그 사용</h3>
        Cookie: <%= request.getCookies()[0].getValue() %>
        <br>
        <h3>EL 사용 시</h3>
        Cookie: ${ cookie.JSESSIONID.value }
        <br>

        <h2>6. 파라미터</h2>

        <form action="${ pageContext.request.contextPath }/views/el/elParam.jsp" method="get">
            <fieldset>
                <legend>제품 입력</legend>
                <input type="text" name="pName" placeholder="제품명을 입력해 주세요">
                <br>
                <input type="number" name="pCount" placeholder="수량을 입력해 주세요">
                <br>
                <input type="text" name="option" placeholder="옵션을 입력해 주세요">
                <br>
                <input type="text" name="option" placeholder="옵션을 입력해 주세요">
                <br>
                <br>
                <input type="submit" name="제출">
                <input type="reset" name="초기화">
            </fieldset>
        </form>


        <br>
        <br>
    </body>
</html>
