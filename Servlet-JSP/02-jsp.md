# JSP(Java Server Page)

- 서블릿은 자바 언어로 동적인 페이지를 구현해야 한다.
    - 서블릿은 비즈니스 로직 처리에 적합함

- JSP는 HTML 코드 안에서 스크립트 형태로 자바 언어를 사용하여 동적인 페이지를 구현한다.
        
    | 구분 | Servlet | JSP |
    | --- | --- | --- |
    | 형태 | Java 코드에 HTML 코드 삽입 | HTML 코드에 Java 코드 삽입 |
    | 예시 | `out.println("<HTML>");` | `<% for(int k = 0; k < 10; k++){ %>` |
    | 특징 | 비즈니스 로직 처리에 적합 | 화면 로직 처리에 적합 |


- JSP의 목표는 서블릿의 비즈니스 로직으로부터 화면 로직을 분리하는 것을 목표로 한다.

- JSP는 그냥 HTML 형태로 쓸 수 있는 서블릿이다.
    - 생성 시점도 페이지에 요청이 들어왔을 때 생성됨(서블릿처럼)
    - 변환과정이 필요해서 처음 생성될 때는 일반 서블릿보다 느리지만, 결국 서블릿을 생성해서 저장하므로 이후부터는 속도가 똑같다.


## JSP 실행 과정

JSP는 자바 언어인데, 웹 브라우저에는 JVM이 없어 자바 언어를 처리할 수 없다.

1. 웹 컨테이너는 클라이언트로부터의 요청이 JSP에 대한 요청일 경우 먼저 JSP 파일을 자바 코드(서블릿)로 변환한다.

2. 변환된 자바 코드(서블릿)를 컴파일하고 서블릿 인스턴스를 생성한다. 

3. 서블릿 인스턴스가 생성되면 서블릿의 라이프 사이클을 거치면서 클라이언트의 요청을 처리해 준다.


## JSP 특징

Java와 특징을 많이 공유한다.

- 자바 언어를 이용하기 때문에 대부분의 운영체제에서 사용이 가능하다.(JVM만 있다면)

- 서버 자원을 효율적으로 사용한다. (요청을 스레드 단위로 처리)

- MVC 패턴을 쉽게 적용할 수 있다.

- JSP 파일의 배포 위치는 HTML 파일과 동일하다.
    - 일단 java로 작성하지만, java가 아님


## JSP 요소

- JSP는 동적인 페이지를 만들기 위해 다양한 스크립팅 요소를 제공한다.

- JSP 스크립팅 요소들은 `<%`로 시작해서 `%>`로 끝나는 것이 특징이다.

### 주석(Annotation) 태그

- `<%-- ... --%>`

- HTML 주석 태그처럼, JSP에도 주석 태그가 있다.

    ```jsp
    <!-- HTML 주석 -->
    <%-- JSP 주석 --%>
    ```

- JSP 문서 내에서 HTML 주석을 사용할 경우, 서블릿 생성 시 HTML 주석이 그대로 들어가게 된다.
    - 페이시 소스 보기나 개발자 도구에서 주석 내용을 확인 가능함

- JSP 주석은 아예 서블릿 생성 시 무시된다.
    - 웹에서 확인할 방법이 없음

### 지시자(Directive) 태그

- JSP 페이지 전체에 영향을 미치는 정보(import같은거)를 기술할 때 쓰인다.

    ```jsp
    <%@ 지시자 [속성 명="value"] … %>
    ```
    
#### `page`

`page` 지시자는 JSP 페이지에 대한 속성을 지정한다.

- JSP 파일 어느 위치에 와도 상관없으나 보통 가장 첫 부분에 작성한다.
    
    ```jsp
    <%@ page import="java.io.*"%>
    <%@ page contentType="text/html; charset=UTF-8"%>
    <%@ page errorPage="/views/error/error500.jsp" %>
    <%@ page isErrorPage="true" %>
    ```

- `import`는 서블릿에서 받아올 라이브러리를 명시한다.

- `contentType`에서는 페이지 종류 선언, 인코딩 선언 등을 할 수 있다.

- `errorPage`는 예외 발생 시 원하는 페이지로 넘어갈지 정할 수 있다.
    - 넘어갈 페이지는 폴더 구조가 복잡할 경우 상대 경로로 작성할 수도 있다.
        ```jsp
        <%@ page errorPage="../error/error500.jsp" %>
        ```
    - 다만, 이 방법으로는 모든 페이지에 에러페이지를 설정해줘야 함
    - 이때는 전체 웹페이지를 관리하는 `web.xml`에서 에러설정을 넣어주면 된다.
        ```xml
        <web-app>
            <error-page>
                <error-code>500</error-code>
                <location>/views/error/error500.jsp</location>
            </error-page>
        </web-app>
        ```

        
- `isErrorPage`는 에러페이지에서 에러 객체 `exception`을 이용할 수 있게 한다. 
    - `exception` 객체에는 `.getMessage()`, `.getCLass()` 등의 메소드가 있어 상세정보를 이용할 수 있음.


#### `include`

- `include` 지시자는 다른 페이지(JSP, HTML)를 포함할 때 사용하는 지시자이다.
    
    ```jsp
    <%@ include file="/views/common/header.jsp"%>
    <main...>
    <%@ include file="footer.html" %>
    ```

- 이 떄 사용된 페이지는 현재 페이지에 끼워넣어서 하나의 페이지처럼 서블릿이 만들어진다.

#### `taglib`

- `taglib` 지시자는 JSP에서 사용할 태그 라이브러리를 지정한다.
    
    ```jsp
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
    ```
    

### 선언문(Declaration) 태그

- `<%! ... %>`

- 서블릿 클래스의 필드와 메소드를 선언할 때 사용한다.
    
    ```jsp
    <%! public static final String DEFAULT_NAME = "홍길동"; %>
    <%! int counter = 0; %>
    
    <%!
        public String getName(HttpServletRequest request){
            return request.getParameter("name");
        }
    %>
    ```

- 코드 안에 자바 주석(`//`)을 작성하면 서블릿 안에 같이 들어온다.

### 스크립트릿(Scriptlet) 태그

- `<% ... %>`

- `_jspService()` 메소드의 로컬(지역) 변수와 자바 코드를 작성할 때 사용한다.
    
    ```jsp
    <%
        int sum = 0;

        for (int i = 1; i <= 10 ; i++) {
            sum += i;
        }
        
        System.out.println(sum);
    %>
    ```

- 지역변수이기 때문에 `private`, `public` 등의 접근제한자를 붙일 수 없다.

- 

### 표현식(Expression) 태그

- `<%= ... &>`

- 서블릿 코드에서 `out.print()`의 역할을 수행하는 태그로 클라이언트로 데이터를 출력하는 코드를 작성할 때 사용한다.
    
    ```jsp
    현재 시간은 <% out.print(new java.util.Date()) %> 입니다.
    <br>
    현재 시간은 <%= new java.util.Date() %> 입니다.
    ```

- 표현식 태그 안의 내용을 그대로 `out.print()` 안에 집어넣기 때문에 `;`를 넣지 않는다.

- 필드, 메소드 호출 결과, 지역변수 등등 다양하게 들어갈 수 있음


## JSP 내장 객체

- JSP가 서블릿으로 변환될 때 자바 파일에 자동으로 추가되는 객체들로, 따로 선언하지 않아도 바로 사용이 가능함
    - 스크립트릿 태그와 표현식 태그 등에서 사용할 수 있다.

- JSP에서 기본적으로 제공하는 객체들은 아래와 같다.
    
    | 내장 객체 명 | 설명 |
    | --- | --- |
    | request | `HttpServletRequest` 객체 참조 변수, 사용자의 요청 처리 |
    | response | `HttpServletResponse` 객체 참조 변수, 서버의 응답 처리 |
    | out | `JspWriter` 객체 참조 변수, 웹에 출력할 때 사용 |
    | session | `HttpSession` 객체 참조 변수 |
    | application | `ServletContext` 객체 참조 변수 |
    | page | 현재 JSP 페이지(this)에 대한 참조 변수 |
    | pageContext | JSP 페이지와 관련된 다른 내장 객체를 얻어내는 객체 |
    | config | JSP 페이지에 대한 설정 정보를 저장하고 있는 객체 |
    | exception | 발생한 Throwable 객체(예외)에 대한 참조 변수 |

### request

- 웹 브라우저의 요청 정보를 가지는 객체

- 주로 클라이언트가 보낸 요청에 대해 정보를 받아오는 기능을 한다.

1. 요청 헤더
    - 클라이언트 정보의 메타데이터를 담는 부분
    - `.getHeader(헤더이름)`
        - 특정 헤더의 값을 가져올 수 있다.
    - `.getHeaderNames()`
        - 전체 헤더 목록을 `Enumeration` 객체로 받아온다.

2. 프로토콜
    - `.getProtocol()`
        - 보통 HTTP

3. 요청 방식
    - `.getMethod()`
        - GET, POST 등등

4. URL(Uniform Resource Location)
    - 네트워크 기준으로 현재 페이지의 위치
    - `.getRequestURL()`

5. URI(Uniform Resource Identifier)
    - 현재 서버 기준, 현재 페이지의 식별자
        - URL에서 서버 관련 내용을 빼면 URI가 된다.
    - `.getRequestURI()`

6. Query String
    - 현재 페이지에서 보낸 요청사항들
        - URL에서 `?` 뒷부분 데이터를 말한다.
    - `.getQueryString()`

7. Context Path
    - 서버에서 웹 애플리케이션의 경로
        - 서버에 애플리케이션 하나만 돌리면 생략해도 된다.
    - `.getContextPath()`
    - 애플리케이션을 여러개 이용하거나 / context path가 없을 때나 / context path 이름을 바꿔줄 때, context path를 하드코딩하면 문제가 생길 수 있다.
        - 실행 시점에 얻어와서 동적으로 동작하도록 할 수 있음
            ```jsp
            <a href="<%=request.getContextPath()%>/views/elements/">기타 태그</a>
            ```
    - 요즘 코드에서는 주로 SpringBoot를 써서 볼 일 없지만, 레거시 코드에서는 가끔 보인다

### response

- 웹 브라우저의 요청에 대한 응답 객체

- 주로 응답을 커스터마이징 할 때 쓰인다.

1. 응답 헤더
    - 서버에서 처리된 정보에 대한 메타데이터
    - `.setContentType()`
        - 응답 페이지를 어떤 종류로(html, css, ...) 보낼지 결정할 수 있다.
    - `.setHeader(헤더 이름, 헤더 값)`
        - 응답에 대해 직접 헤더를 설정할 수도 있다.

2. 응답 상태
    - 응답 상태를 커스터마이징할 수 있다.
    - `.setStatus(상태코드)`
        - 상태코드만 넘겨준다. 실제 화면은 그대로 나옴
        - `HttpServletResponse.상수` 상수를 이용해 코드값을 몰라도 응답을 만들 수 있다.
    - `.sendError(404)`
        - 아예 에러를 보내서 에러페이지로 이동하게 할 수 있다.

3. 리다이렉트
    - 매개값으로 지정한 경로로 **클라이언트가 다시 요청**하도록 응답한다.
    - `response.sendRedirect("redirect_target.jsp");`    
        - 먼저 원래 페이지로 이동한 뒤, 리다이렉트 코드를 보면 `302 Found`를 반환
        - 그럼 클라이언트가 응답 헤더에서 새로운 경로를 확인해 다시 요청을 보내게 된다.
    - 브라우저(클라이언트)가 새로운 요청을 다시 보내기 때문에(request, response 객체를 새로 만든다) 이전 요청과 응답 정보가 유지되지 않는다.
        - 포워드와 구분되는 특징
    - 같은 서버 내의 페이지만이 아니라 아예 다른 서버로 보낼 수도 있다.
        - 대신 프로토콜, 도메인, 포트, 컨텍스트 경로도 다 지정해 줘야 함

### pageContext

- JSP와 관련된 다른 내장 객체를 얻어오거나, 포워드(Forward)하는데 사용하는 객체

1. Forward
    - 매개값으로 지정한 경로로 **서버 내부**에서 요청을 전달한다.
    - `pageContext.forward("forward_target.jsp");`
        - 원래 페이지로 이동한 뒤, 포워드 코드를 보고 서버 내부에서 이동할 페이지로 이동하고, 처리함
        - 클라이언트에서는 내부 처리가 끝난 페이지를 보여준다.
    - 내부에서 요청을 전달하기 때문에 동일한 요청, 응답 객체를 사용해 요청, 응답 정보가 유지된다.
    - 서버 내에서만 전달해 그냥 컨텍스트 루트 기준으로만 경로를 지정하면 된다.
        - 대신 다른 서버로 전달은 불가

### session

- 웹 브라우저의 정보를 유지하기 위한 세션 정보를 저장하는 객체

- 세션은 서버 측의 컨테이너에서 관리되는 정보이다.
    - 즉, 클라이언트의 정보를 서버에서 저장하는 것
    - 보안적으로도 좋다

- 세션은 클라이언트와 서버 간의 상태를 유지시켜주는 역할을 한다.
    - 세션의 정보는 클라이언트에서 접속해서 해당 브라우저를 종료할 때까지 유지됨

- `.isNew()`
    - 새로 열린 세션은 true, 기존에 접속했던 세션이면 false

- `.getId()`
    - 현재 세션의 ID를 알 수 있다.
- `.getCreationTime()`
    - 세션이 열린 시간을 알려준다.
    - 결과를 ms단위(Long 타입)로 주기 때문에, `new Date(session.getCreationTime())`처럼 변환해 주어야 한다.
- `.getLastAccessedTime()`
    - 세션에 마지막으로 접속한 시간을 알려준다.
    - 역시 데이터를 Long 타입으로 준다.
- `.getMaxInactiveInterval()`
    - 세션이 유지되는 시간을 초 단위로 반환한다.
    - `session.setMaxInactiveInterval(초)`를 통해 유지시간을 바꿀 수도 있음

- `.setAttribute(키, 값)`
    - 세션에 특정 키와 그 값을 담아준다.
        - `.getAttribute(키)`로 값을 다시 가져올 수 있다.
    - 세션에 값을 저장했으므로, 다른 페이지에서도 그 값을 꺼내올 수 있다.


### JSP 내장 객체 영역

- Page < Request < Session < Application 영역 순으로 범위가 넓다.
    
    | 영역 | 설명 |
    | --- | --- |
    | Page | 하나의 JSP 페이지를 처리할 때 사용되는 영역 <br> pageContext 내장 객체를 통해 접근할 수 있는 영역 |
    | Request | 하나의 요청을 처리할 때 사용되는 영역 <br> request 내장 객체를 통해 접근할 수 있는 영역 |
    | Session | 하나의 브라우저와 관련된 영역 <br> session 내장 객체를 통해 접근할 수 있는 영역 |
    | Application | 하나의 웹 애플리케이션과 관련된 영역 <br> application 내장 객체를 통해 접근할 수 있는 영역 |