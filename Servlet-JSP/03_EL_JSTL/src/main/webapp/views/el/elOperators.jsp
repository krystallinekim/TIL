<%@ page import="com.beyond.eljstl.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>EL 연산자</title>
    </head>

    <body>
        <h1>EL 연산자</h1>
        <h2>산술연산자</h2>
        <p>
            10 + 3 = ${ 10 + 3 }<br>
            10 - 3 = ${ 10 - 3 }<br>
            10 * 3 = ${ 10 * 3 }<br>
            10 / 3 = ${ 10 / 3 } 또는 ${ 10 div 3 }<br>
            10 % 3 = ${ 10 % 3 } 또는 ${ 10 mod 3 }<br>
            연산자, 혹은 키워드를 통해 연산이 가능함
        </p>
        <h2>논리연산자</h2>
        <p>
            True and False = ${ true && false } 또는 ${ true and false }<br>
            True or False = ${ true || false } 또는 ${ true or false }<br>
            not True = ${ !true }
        </p>

        <h2>비교연산자</h2>
        <h3>숫자 비교</h3>
        <%
            int a = 10;
            int b = 3;

            request.setAttribute("a", a);
            request.setAttribute("b", b);
        %>
        <p>
            표현식 태그를 사용할 경우: <%= (Integer) request.getAttribute("a") > (Integer) request.getAttribute("b") %><br>
            그냥 사용할 경우 object 타입 끼리의 계산이라 계산이 불가함 - 매우 길고 복잡하다
        </p>

        <p>
            EL을 사용할 경우 자동으로 형변환을 해서 계산한다.<br>
            10 > 3 = ${ a > b } 또는 ${ a gt b }<br>
            10 >= 3 = ${ a >= b } 또는 ${ a ge b }<br>
            10 < 3 = ${ a < b } 또는 ${ a lt b }<br>
            10 <= 3 = ${ a <= b } 또는 ${ a le b }<br>
        </p>

        <h3>객체 동등 비교</h3>
        <%
            request.setAttribute("str1", "Hello");
            request.setAttribute("str2", new String("Hello"));
            request.setAttribute("student1", new Student("홍길동", 34, 80, 30));
            request.setAttribute("student2", new Student("홍길동", 34, 80, 30));
        %>
        <p>
            표현식 태그 - 객체의 주소를 비교하므로 값이 같아도 일반 동등비교로는 다르다고 나온다.<br>
            str1 == str2 = <%= request.getAttribute("str1") == request.getAttribute("str2")%><br>
            str1.equals(str2) = <%= request.getAttribute("str1").equals(request.getAttribute("str2"))%><br>
            student1 == student2 = <%= request.getAttribute("student1") == request.getAttribute("student2")%><br>
            student1.equals(student2) = <%= request.getAttribute("student1").equals(request.getAttribute("student2"))%><br>
        </p>
        <p>
            EL의 경우, 알아서 값을 가지고 비교함. 내부적으로 `.equals()` 메서드를 호출해 비교한다.<br>
            str1 == str2 = ${str1 == str2}<br>
            student1 == student2 = ${student1 == student2}<br>
        </p>

        <h3>null 비교</h3>
        <%
            String str = null;
            List<String> names = new ArrayList<>();

            request.setAttribute("str", str);
            request.setAttribute("names", names);
        %>

        <p>
            표현식 태그 - 일반 변수의 경우<br>
            str == null = <%= str == null %><br>
            names == null = <%= names == null %><br>
            names.isEmpty() = <%= names.isEmpty() %><br>
        </p>
        <p>
            EL에서는 ==null과 .isEmpty()를 합쳐서 empty로 사용함. null이 아니지만, 내용이 없으면 empty에서는 true로 나온다.<br>
            str == null = ${ str == null }<br>
            str == empty = ${ empty str }<br>
            names == null = ${ names == null }<br>
            names == empty = ${ empty names }<br>



        </p>
    </body>
</html>
