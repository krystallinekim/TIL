<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="ko">
    <head>
        <meta charset="utf-8">
        <title>JSP 요소</title>
    </head>
    <body>
        <h1>JSP Elements</h1>
        <h2> 주석 태그</h2>
        <p>
            JSP 주석은 JSP 파일에만 있고, 서블릿으로 변환되지 않는다. 페이지 소스보기/개발자 도구에서도 보이지 않음.
            <!-- HTML 주석 -->
            <%-- JSP 주석 --%>
        </p>

        <br>

        <h2>선언문 태그</h2>
        <p>
            서블릿 클래스의 필드와 메소드를 선언할 때 사용한다.
        </p>
        <%!
            // 필드 선언
            private String name = "홍길동";

            // 메소드 선언
            public String getName() {
                return this.name;
            }
        %>

        <br>

        <h2>스크립트릿 태그</h2>
        <p>
            `_jspService()` 메소드의 로컬(지역) 변수와 자바 코드를 작성할 때 사용한다.

        </p>
        <%
            // 자바 코드 작성
            // public int k = 0;  // 지역변수라 접근제한자는 사용 불가
            int sum = 0;

            for (int i = 1; i <= 10 ; i++) {
                sum += i;
        %>

        <%-- <h3> 안녕하세요 </h3> --%>

        <%
            }

            // System.out.println(sum);
        %>
        <br>

        <h2> 표현식 태그 </h2>
        <p>
            서블릿 코드에서 `out.print()`의 역할을 수행하는 태그로 클라이언트로 데이터를 출력하는 코드를 작성할 때 사용한다.
        </p>
        제 이름은 <% out.print(name); %>입니다.
        <br>
        제 이름은 <span style="color: crimson;font-size: 2em"><%= name %></span> 입니다.

        <br>
    </body>
</html>
