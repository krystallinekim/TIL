# JSTL

## JSP 액션 태그

- JSP 페이지에서 자바 코드를 최대한 직접 입력하지 않고 특정 작업을 수행하는데 사용하는 태그이다.
    - 단, 실제 실행될 때는 서블릿으로 변환 후 자바 코드로 실행된다.

- 액션 태그의 경우 웹 브라우저에서 실행되는 것이 아니라 웹 컨테이너에서 실행된다.  
    
    | 구분 | 표준 액션 태그 | 커스텀 액션 태그 |
    | --- | --- | --- |
    | 사용법 | JSP 페이지에서 바로 사용함. 태그 앞에 jsp 접두어가 붙음 | 별도의 라이브러리 설치 필요함. 라이브러리 선언에 맞는 접두어가 붙음 |
    | 사용 예시 | `<jsp:include page="../sample.jsp"/>` | `<c:set var="count" value="0"/>` |

## 표준 액션 태그

- JSP에서 기본으로 제공하는 액션 태그로 별도의 라이브러리 설치 없이 바로 사용할 수 있다.
    
    | 태그 명 | 설명 |
    | --- | --- |
    | `jsp:include` | 현재 페이지에 특정 페이지를 포함할 때 사용 |
    | `jsp:forward` | 현재 페이지 접근 시 특정 페이지로 이동 (`pageContext.forward()`와 동일) |
    | `jsp:param` | <jsp:include>, <jsp:forward>의 하위 요소로 사용되며 해당 페이지에 전달할 값을 기록할 때 사용 |

### jsp:include 액션 태그

```jsp
<jsp:include page="포함할 페이지(경로)" /> <!-- 셀프 클로징된 태그 -->
```

- include 액션 태그는 다른 페이지를 포함 시킬 때 사용하는 액션 태그이다.

- include 지시자와 다르게 include 액션 태그는 런타임 시에 포함된다.
    - include 지시자는 다른 페이지를 포함하는 JSP 파일이 컴파일 되기 전에 페이지가 포함된다. (서블릿에 html태그, **선언한 지역변수**도 다 같이 딸려옴)  
        - 그래서 선언하지도 않았지만 기존 페이지에서 그 지역변수를 이용할 수 있어버린다. 같은 이름의 변수를 선언할 수도 없다.

    - include 액션 태그는 별도의 서블릿을 하나 만들어서 실행 흐름을 넘겼다가 다시 돌아오는 형태임
        - 포함페이지에서 지역변수를 설정해도, 기존 페이지에는 영향이 없다. (별도 페이지 안에서 만들어진 변수이므로) 동일 변수 이름 선언도 가능함

- 주로 액션 태그를 사용함. include 지시어에서 변수 설정이 너무 까다롭다 + 예전에는 포함페이지를 수정해도 반영이 안되는 경우가 있었다

### jsp:forward 액션 태그

```jsp
<jsp:forward page="이동할 페이지" />
```

- forward 액션 태그는 다른 페이지로 요청을 전달할 때 사용하는 액션 태그이다.
    - `pageContext.forward()`로 작업하던 것을 액션 태그로 쓸 수 있는 것

- 요청을 전달하는 페이지에서 request, response 객체가 함께 전달되며 URL은 변경되지 않는다.(포워드 특성)


### jsp:param 액션 태그

```jsp
<jsp:include page="includePage.jsp">
    <jsp:param name="userName" value="홍길동"/>
</jsp:include>
```

- include, fowrard 액션 태그 내부에 사용되며, 해당 페이지에 그 변수를 전달한다.
    - `includePage.jsp?userName=홍길동`쿼리스트링처럼 전달되며, `request.getParameter("userName")` 혹은 `${ param.userName }`처럼 꺼내 쓸 수 있다.

## JSTL(JSP Standard Tag Library)

- 표준 액션 태그와 달리, 커스텀 태그는 개발자가 직접 정의 가능한 태그이다.

- JSTL은 JSP Standard Tag Library의 약자로 JSP에서 자주 사용하는 코드들을 사용하기 쉽게 커스텀 태그로 만들어 표준으로 제공한다.

### JSTL 라이브러리 등록

1. 메이븐 저장소에서 JSTL API(Jakarta Standard Tag Library API) + 인터페이스(Jakarta Standard Tag Library Implementation)를 다운로드한다.
    
    [maven repository](https://mvnrepository.com/)
    
2. JSP 페이지에서 taglib 지시자로 JSTL 라이브러리를 선언해야 사용이 가능하다.
    
    ```jsp
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
    ```
    

### JSTL 태그 종류

- JSTL은 용도에 따라 다양한 종류의 태그들을 제공한다.
    
    
    | 태그 명 | 설명 | 선언 코드 |
    | --- | --- | --- |
    | Core Tags | 변수와 URL, 조건문, 반복문 등의 로직과 관련된 액션 태그를 제공한다. | `<%@ taglib uri="jakarta.tags.core" prefix="c" %>` |
    | Formatting Tags | 메시지 형식이나 숫자, 날짜 형식과 관련된 액션 태그를 제공한다. | `<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>` |
    | Function | trim, substring과 같은 여러 문자열 처리 함수를 제공한다. | `<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>` |
    | XML Tags | 데이터의 XML 파싱 처리 등 XML 문서를 화면으로 읽어오는데 필요한 라이브러리이다. | `<%@ taglib uri="jakarta.tags.xml" prefix="x" %>` |
    | SQL Tags | 페이지 내에서 Database를 연동하고 필요한 쿼리를 실행할 수 있는 라이브러리이다. | `<%@ taglib uri="jakarta.tags.sql" prefix="sql" %>` |

## JSTL Core Tags

- 변수와 URL, 조건문, 반복문 등의 로직과 관련된 액션 태그

### 변수 관련 태그

#### 변수 선언 및 초기화 - `c:set`

- 변수를 선언하고 초기 값을 대입하는 태그이다.
    - 변수의 자료형은 별도로 선언하지 않지만 초기 값은 반드시 기술해야 한다.

- 선언된 변수는 **EL 구문에서만 사용**이 가능하다.
    - 영역 객체에 attribute로 생성되기 때문임
    - scope 속성은 변수가 저장된 영역을 지정한다. (기본값은 page)
    
    ```jsp
    <% request.setAttribute("num", "100") %>
    <c:set var="num" value="100" scope="request"/>
    
    ${ num } <!-- 100 -->
    ```

- EL을 잘 이용하면 계산도 가능

    ```jsp
    <c:set var="num1" value="10"/>
    <c:set var="num2" value="3" scope="request"/>
    <c:set var="result" value="${ num1 + num2 }"/>
    
    result: ${ result }  <!-- 13 -->      
    ```
        
- `<c:set> ~ </c:set>` 사이에 ","를 이용해서 배열이나 Collection처럼 여러 개의 값을 지정할 수 있다.
    - EL을 이용해서 stream처럼 하나씩 출력할 수도 있다.

    ```jsp
    <c:set var="colors" scope="request">
        red, green, blue
    </c:set>

    ${ colors } <!-- red, green, blue -->
    ```
    
#### 변수 삭제 - `c:remove`

- `c:set` 태그로 선언한 변수를 삭제할 때 사용하는 태그이다.
- scope 속성을 지정하지 않으면 page, request, session, application 영역에 저장되어 있는 속성을 *모두* 찾아 제거한다.
    
    ```jsp
    <c:remove var="num1" scope="request">
    ```

### 출력 관련 태그

#### 출력 - `c:out`

- 클라이언트로 데이터를 출력할 때 사용하는 태그이다.
    
    ```jsp
    <c:out value="<h2>데이터 출력</h2>" escapeXml="true"/>  <!-- <h2>데이터 출력</h2> -->
    <c:out value="<h2>데이터 출력</h2>" escapeXml="false"/> <!-- 데이터 출력 -->
    ```
- 값을 태그로 읽게 할건지, 아니면 그냥 문자열로 읽게 할건지 정의할 수 있다.
    - `escapeXml="true"`면 그냥 문자열로 출력
    - `escapeXml`이 없으면 true로 인식(일반 문자열로)

- `default="기본값"`을 주면, 만약 value값이 없을 때 대신 기본값을 준다.


### 조건문 관련 태그

- 조건문을 JSTL이 아니라 표현식으로 출력하려고 하면, 중간중간 스크립트릿으로 끊어가면서 써야 한다
    - 매우 가독성이 떨어지고, 쓰기도 힘들다

#### if문 - `c:if`

- 자바의 if 구문과 같은 역할을 하는 태그이다.

- 조건식은 test 속성에 **EL 구문**으로 기술해야 한다.

- 조건식의 결과가 참일 때 `<c:if> ~ </c:if>` 사이에 있는 내용을 처리한다.
    - `c:if`구문에는 else 역할을 하는 태그가 없다.
    
    ```jsp
    <c:if test="${num1 > num2}">
        num1 > num2
    </c:if>
    ```

- 이 때 num1, num2를 문자열로 인식하면 조건식이 제대로 처리되지 않는 경우가 있으므로, <c:set var="num1" value="${10}"/>처럼 EL을 이용해 표시해 주는 것이 좋다.

#### switch문 - `c:choose`

- 자바의 if구문, 또는 switch 구문과 같은 역할을 하는 태그이다.

- 하위 태그인 `<c:when>`, `<c:otherwise>` 태그와 함께 사용되는데, 각각 switch 구문의 case, default 절과 비슷한 역할을 한다.
    - 위에서 아래로 `c:when`절을 확인하고, true인 `c:when`절이 있다면 실행 후 나머지는 실행하지 않는다.
    - 만약 모든 `c:when`이 false라면, `c:otherwise`구문을 실행한다.
    
    ```jsp
    <c:choose>
      <c:when test="${num == 0}">
      	...
      </c:when>
      <c:when test="${num == 1}">
      	...
      </c:when>
      <c:otherwise>
      	...
      </c:otherwise>
    </c:choose>
    ```
    
### 반복문 관련 태그

#### c:forEach 태그

- 자바의 for 구문에 해당하는 역할을 하는 태그이다.
    
    ```jsp
    <c:forEach begin="1" end="10" items="${list}" var="value">
      ...
    </c:forEach>
    ```
    
- 속성
    
    
    | 속성 | 설명 |
    | --- | --- |
    | items | 반복할 객체 명 (Collection 객체) |
    | begin | 반복이 시작할 요소 번호 |
    | end | 반복이 끝나는 요소 번호 |
    | step | 반복할 횟수 번호 |
    | var | 현재 반복 횟수에 해당하는 변수 이름 |
    | varStatus | 현재 반복 상태 정보를 담은 객체 |

#### c:forTokens 태그

- 문자열에 포함된 구분자를 통해 토큰을 분리해 반복을 수행하는 태그이다.
    
    ```jsp
    <c:forTokens var="color" items="yellow blue pink red green" delims=" ">
        ${color}
    </c:forTokens>
    ```
    
### URL 태그

#### c:url 태그

- URL을 생성하고 쿼리 스트링을 미리 설정하는 태그이다.
    
    ```jsp
    <c:url var="url" value="jstl.jsp">
      <c:param name="name" value="abc"/>
    </c:url>
    
    <a href="${url}">jstl.jsp</a>
    ```
    

### JSTL Formatting Tags

#### fmt:formatNumber 태그

- 숫자의 포맷을 통화 기호, ',' 표시, % 표시 등 원하는 쓰임에 맞게 지정할 수 있는 태그이다.
    
    ```jsp
    <c:set var="number" value="12300.12"/>
    
    <fmt:formatNumber  value="${number}" type="number" groupingUsed="true"/>
    <fmt:formatNumber value="${number}" type="currency"/>
    <fmt:formatNumber value="${number}" type="percent" />
    ```
    
- maxIntegerDigits, minIntegerDigits 속성으로 표시하고자 하는 정수의 자릿수를 지정할 수 있다.
    
    ```jsp
    <fmt:formatNumber type="number" maxIntegerDigits="4" value="${number}"/>
    ```
    
- maxFractionalDigits, minFractionalDigits 속성으로 소수의 자릿수를 지정할 수 있다.
    
    ```jsp
    <fmt:formatNumber type="number" pattern="000.00" maxFractionDigits="2" value="12300.125"/>
    <fmt:formatNumber type="number" pattern="###.###" minFractionDigits="3" value="12300.1"/>
    ```
    

#### fmt:formatDate 태그

- 날짜나 시간의 포맷 방식을 지정하기 위해 사용하는 태그이다.
- value 속성으로는 java.util.Date() 객체를 사용해야 한다.
    
    ```jsp
    <c:set var="date" value="<%= new java.util.Date() %>"/>
    
    <fmt:formatDate type="time" value="${date}"/>
    ```
    
- type 속성과 dateStyle, timeStyle 속성을 사용하여 다양한 형태로 표기 방식을 지정할 수 있다.
    
    ```jsp
    <fmt:formatDate type="time" value="${date}"/>
    <fmt:formatDate type="date" value="${date}"/>
    <fmt:formatDate type="both" value="${date}"/>
    <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${date}"/>
    <fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${date}"/>
    <fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${date}"/>
    ```
    

#### fmt:setLocale 태그

- 지역 설정을 통해 통화 기호나 시간 대역을 다르게 설정 가능하다.
    
    ```jsp
    <fmt:setLocale value="en_US"/>
    <fmt:setLocale value="ja_JP"/>
    ```
    

### JSTL Function

- 문자열 처리에 관련된 함수들을 EL 구문에서 사용할 수 있게 하는 라이브러리이다.
    
    
    | 함수명 | 설명 |
    | --- | --- |
    | fn:contains(str, 'text') | str에 두 번째 인자 값의 내용이 포함되어 있는지 확인 |
    | fn:containsIgnoreCase(str, 'text') | str에 대소문자 구분 없이 'text' 값이 포함되어 있는지 확인 |
    | fn:startsWith(str, 'text') | str이 'text'로 시작하는지 확인 |
    | fn:endsWith(str, 'text') | str이 'text'로 끝나는지 확인 |
    | fn:escapeXml(str) | str에 XML 태그가 포함되어 있다면 해당 태그까지 문자 그대로 화면에 출력 |
    | fn:indexOf(str, 'text') | str 내에 'text'의 첫 글자 시작 위치 반환 (0부터 시작) |
    | fn:length(str) | str 길이 반환 |
    | fn:replace(str, 'text1', 'text2') | str 내의 text1을 찾아 text2로 변경 |
    | fn:substring(str, index1, index2) | str에서 index1부터 index2까지의 문자열 반환 |
    | fn:split(str, ' ') | str을 공백(' ') 구분자를 기준으로 나눠 배열로 반환 |
    | fn:join(str, '-') | 배열 요소로 나누어진 str을 '-' 구분자로 연결해 반환 |
    | fn:trim(str) | str 값의 좌우 공백 제거 |  