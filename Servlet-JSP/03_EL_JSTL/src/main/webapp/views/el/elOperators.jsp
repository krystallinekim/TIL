<%@ page import="com.beyond.eljstl.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>EL 연산자</title>
</head>
<body>
    <h2>EL 연산자</h2>

    <h3>1. 산술 연산자</h3>
    ${ 10 + 5 }<br>
    ${ 10 - 5 }<br>
    ${ 10 * 5 }<br>
    ${ 10 / 5 } 또는 ${ 10 div 5 }<br>
    ${ 10 % 3 } 또는 ${ 10 mod 3 }<br>

    <h3>2. 논리 연산자</h3>
    ${ true && true} 또는 ${ true and true}
    <br>
    ${ true || false} 또는 ${ true or false}
    <br>
    ${ !(10 > 5) } 또는 ${ not(10 > 5) }

    <h3>3. 비교 연산자</h3>

    <h4>1) 숫자 비교</h4>
    <%
        int big = 10;
        int small = 3;

        request.setAttribute("big", big);
        request.setAttribute("small", small);
    %>
    <%-- Object 타입의 산술 연산, 비교 연산은 수행할 수 없다.  --%>
    <%-- 표현식 태그 : <%= request.getAttribute("big") > request.getAttribute("small") %> --%>
    표현식 태그 : <%= (Integer) request.getAttribute("big") > (Integer) request.getAttribute("small") %>

    <br>

    <%-- EL은 영역 객체의 속성을 자동으로 형 변환 후 연산을 처리한다. --%>
    EL : ${ big > small } 또는 ${ big gt small }<br>
    EL : ${ big >= small } 또는 ${ big ge small }<br>
    EL : ${ big < small } 또는 ${ big lt small }<br>
    EL : ${ big <= small } 또는 ${ big le small }<br>
    EL : ${ big == small } 또는 ${ big eq small }<br>
    EL : ${ big != small } 또는 ${ big ne small }<br>

    <h4>2) 객체의 동등 비교</h4>
    <%
        request.setAttribute("str1", "Hello");
        request.setAttribute("str2", new String("Hello"));
        request.setAttribute("student1", new Student("홍길동", 34, 80, 80));
        request.setAttribute("student2", new Student("홍길동", 34, 80, 80));
        // request.setAttribute("student2", new Student("성춘향", 24, 90, 90));
    %>
    표현식 태그 :
    <%= request.getAttribute("str1") == request.getAttribute("str2") %>,
    <%= request.getAttribute("str1").equals(request.getAttribute("str2")) %>
    <br>
    <%-- EL에서 객체의 == 연산은 equals() 호출과 같은 동작을 한다. --%>
    EL : ${ str1 == str2 }
    <br>
    EL : ${ str1 != str2 }
    <br>
    EL : ${ student1 == student2 }
    <br>
    EL : ${ student1 != student2 }

    <h4>3) 객체의 null 비교</h4>
    <%
        // String str = null;
        String str = ""; // 빈 문자열의 경우에 empty 연산의 결과는 true이다.
        List<String> names = new ArrayList<>();

        // names.add("홍길동");

        request.setAttribute("str", str);
        request.setAttribute("names", names);
    %>
    표현식 태그 : <%= str == null %>, <%= names == null %>, <%= names.isEmpty() %>
    <br>
    EL : ${ str == null }, ${ names == null }
    <br>
    EL : ${ empty str}, ${ empty names }

    <br><br><br><br><br>
    <br><br><br><br><br>
    <br><br><br><br><br>
    <br><br><br><br><br>
    <br><br><br><br><br>
    <br><br><br><br><br>
    <br><br><br><br><br>
</body>
</html>
