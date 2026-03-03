# JSTL

## JSP 액션 태그

- JSP 페이지에서 자바 코드를 직접 입력하지 않고 특정 작업을 수행하는데 사용하는 태그이다.

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

- include 액션 태그는 다른 페이지를 포함 시킬 때 사용하는 액션 태그이다.

- include 지시자와 다르게 include 액션 태그는 런타임 시에 포함된다.
    
    ```jsp
    <jsp:include page="포함할 페이지(경로)" />
    ```
    

### jsp:forward 액션 태그

- forward 액션 태그는 다른 페이지로 요청을 전달할 때 사용하는 액션 태그이다.
    - `pageContext.forward()`로 작업하던 것을 액션 태그로 쓸 수 있는 것

- 요청을 전달하는 페이지에서 request, response 객체가 함께 전달되며 URL은 변경되지 않는다.(포워드 특성)
    
    ```jsp
    <jsp:forward page="이동할 페이지" />
    ```
    

## JSTL(JSP Standard Tag Library)

- JSP Standard Tag Library의 약자로 JSP에서 사용하는 커스텀 태그이다.
- JSP 페이지에서 자주 사용하는 코드들을 사용하기 쉽게 태그로 만들어 표준으로 제공한다.

### 4.1. JSTL 라이브러리 등록

- 메이븐 저장소에서 JSTL 라이브러리를 다운로드한다.
    
    [](https://mvnrepository.com/)
    
- JSTL 라이브러리는 JSP 페이지에서 taglib 지시자로 선언해야 사용이 가능하다.
    
    ```jsp
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
    ```
    

### 4.2. JSTL 태그 종류

- JSTL은 용도에 따라 다양한 종류의 태그들을 제공한다.
    
    
    | 태그 명 | 설명 |
    | --- | --- |
    | Core Tags | 변수와 URL, 조건문, 반복문 등의 로직과 관련된 액션 태그를 제공한다.
    <%@ taglib uri="jakarta.tags.core" prefix="c" %> |
    | Formatting Tags | 메시지 형식이나 숫자, 날짜 형식과 관련된 액션 태그를 제공한다.
    <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %> |
    | Function | trim, substring과 같은 여러 문자열 처리 함수를 제공한다.
    <%@ taglib uri="jakarta.tags.functions" prefix="fn" %> |
    | XML Tags | 데이터의 XML 파싱 처리 등 XML 문서를 화면으로 읽어오는데 필요한 라이브러리이다.
    <%@ taglib uri="jakarta.tags.xml" prefix="x" %> |
    | SQL Tags | 페이지 내에서 Database를 연동하고 필요한 쿼리를 실행할 수 있는 라이브러리이다.
    <%@ taglib uri="jakarta.tags.sql" prefix="sql" %> |

## 5. JSTL Core Tags

### 5.1 c:set 태그

- 변수를 선언하고 초기 값을 대입하는 태그이다.
- 변수의 자료형은 별도로 선언하지 않지만 초기 값은 반드시 기술해야 한다.
- 선언된 변수는 EL 구문에서 사용이 가능하다.
    
    ```jsp
    <c:set var="num" value="100" />
    
    ${num}
    ```
    
- scope 속성은 변수가 저장된 영역을 지정한다. (기본값은 page)
    
    ```jsp
    <c:set var="num" value="100" scope="request" />
    ```
    
- `<c:set> ~ </c:set>` 사이에 ","를 이용해서 배열이나 Collection처럼 여러 개의 값 지정할 수 있다.
    
    ```jsp
    <c:set var="array" scope="request">
      yellow, blue, pink, red, green
    </c:set>
    ```
    

### 5.2 c:remove 태그

- c:set 태그로 선언한 변수를 삭제할 때 사용하는 태그이다.
- scope 속성을 지정하지 않으면 page, request, session, application 영역에 저장되어 있는 속성을 모두 찾아 제거한다.
    
    ```jsp
    <c:remove var="num1" scope="request">
    ```
    

### 5.3. c:out 태그

- 클라이언트로 데이터 출력할 때 사용하는 태그이다.
    
    ```jsp
    <c:out value="<h2>데이터 출력</h2>" escapeXml="false"/>
    <c:out value="<h2>데이터 출력</h2>" escapeXml="true"/>
    ```
    

### 5.4. c:if 태그

- 자바의 if 구문과 같은 역할을 하는 태그이다.
- 조건식은 test 속성에 EL 구문으로 기술해야 한다.
- 조건식의 결과가 참일 때 `<c:if> ~ </c:if>` 사이에 있는 내용을 처리한다.
    
    ```jsp
    <c:if test="${num1 > num2}">
      ...
    </c:if>
    ```
    

### 5.5. c:choose 태그

- 자바의 switch 구문과 같은 역할을 하는 태그이다.
- 하위 태그인 `<c:when>`, `<c:otherwise>` 태그와 함께 사용되는데, 각각 switch 구문의 case, default 절과 비슷한 역할을 한다.
    
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
    

### 5.6. c:forEach 태그

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

### 5.7. c:forTokens 태그

- 문자열에 포함된 구분자를 통해 토큰을 분리해 반복을 수행하는 태그이다.
    
    ```jsp
    <c:forTokens var="color" items="yellow blue pink red green" delims=" ">
        ${color}
    </c:forTokens>
    ```
    

### 5.8. c:url 태그

- URL을 생성하고 쿼리 스트링을 미리 설정하는 태그이다.
    
    ```jsp
    <c:url var="url" value="jstl.jsp">
      <c:param name="name" value="abc"/>
    </c:url>
    
    <a href="${url}">jstl.jsp</a>
    ```
    

## 6. JSTL Formatting Tags

### 6.1. fmt:formatNumber 태그

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
    

### 6.2. fmt:formatDate 태그

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
    

### 6.3. fmt:setLocale 태그

- 지역 설정을 통해 통화 기호나 시간 대역을 다르게 설정 가능하다.
    
    ```jsp
    <fmt:setLocale value="en_US"/>
    <fmt:setLocale value="ja_JP"/>
    ```
    

## 7. JSTL Function

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