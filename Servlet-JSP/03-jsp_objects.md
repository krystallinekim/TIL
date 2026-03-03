# JSP 내장 객체

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

## request

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

## response

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

## pageContext

- JSP와 관련된 다른 내장 객체를 얻어오거나, 포워드(Forward)하는데 사용하는 객체

1. Forward
    - 매개값으로 지정한 경로로 **서버 내부**에서 요청을 전달한다.
    - `pageContext.forward("forward_target.jsp");`
        - 원래 페이지로 이동한 뒤, 포워드 코드를 보고 서버 내부에서 이동할 페이지로 이동하고, 처리함
        - 클라이언트에서는 내부 처리가 끝난 페이지를 보여준다.
    - 내부에서 요청을 전달하기 때문에 동일한 요청, 응답 객체를 사용해 요청, 응답 정보가 유지된다.
    - 서버 내에서만 전달해 그냥 컨텍스트 루트 기준으로만 경로를 지정하면 된다.
        - 대신 다른 서버로 전달은 불가

## session

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

### 쿠키

- 클라이언트 쪽에서 관리되는 정보
    - 클라이언트의 PC 어딘가에 물리적으로 저장된다.
    - 보안에 취약해서 민감한 정보를 저장하진 않는다.

- 클라이언트와 서버 간의 상태를 유지시켜주는 역할을 한다.

- 서버에서 만들어서 클라이언트로 전달함

    ```jsp
    <%
        // 1. 쿠키 생성
        Cookie cookie = new Cookie("user-name", "길동");

        // 2. 쿠키 만료 시간 설정
        cookie.setMaxAge(100);
        // cookie.setMaxAge(-1);  // 세션 쿠키

        // 2-*. 쿠키를 어디서나 사용하도록 설정
        cookie.setPath("/jsp");

        // 3. 쿠키를 클라이언트로 전송
        response.addCookie(cookie);
    %>
    ```

- 클라이언트는 자신이 가진 쿠키를 request 헤더에 넣어서 서버에 전달한다. 서버는 이를 받아서 이용할 수 있음
    ```jsp
    <%
        // 1, 클라이언트가 전송한 모든 쿠키를 배열로 받아온다.
        Cookie[] cookies = request.getCookies();

        // 쿠키 배열 내용 확인
        for (Cookie c: cookies) {
            out.print(c.getName() + " = " + c.getValue());
            out.print("<br>");
        }
    %>
    ```

    
- 개발자 도구의 Application - Storage - Cookies의 해당 프로그램 부분에서 확인할 수 있다. 

- 만료 시간이 지나가면 알아서 사라진다. 대신 브라우저가 종료되어도 남아있다.
    - `.setMaxAge()` 안에 -1을 주면 세션쿠키가 되어 브라우저가 종료될 때 까지 유지되는 쿠키가 된다.

- 쿠키는 유효한 경로로 접근할 때만 유효하기 때문에, 경로가 다르면 쿠키를 사용할 수 없다.
    - 쿠키 경로를 `.setPath(URL)`로 설정해주면 된다.

## application

- 웹 애플리케이션의 실행 환경을 제공하는 서버의 정보와, 서버 측 자원에 대한 정보를 저장하는 객체이다.
- 서버 정보(`.getServerInfo()`), 웹 모듈 버전(`.getMajorVersion()`, `.getMinorVersion()`) 등의 여러 정보를 볼 수 있다.


## JSP 내장 객체 영역(Scope)

- JSP 내장 객체 중 pageContext, request, session, application은 데이터를 공유할 수 있는 각각의 유효 범위(Scope)를 가진다.

- 공유되는 데이터를 속성(Attribute)이라고 하며, 속성이 유지되는 범위를 영역(Scope)이라고 한다.

- Page < Request < Session < Application 영역 순으로 범위가 넓다.
    
    | 영역 | 설명 | 범위 |
    | --- | --- | --- |
    | Page | 하나의 JSP 페이지를 처리할 때 사용되는 영역 | 특정 JSP 페이지 내에서만 유지된다. |
    | Request | 하나의 요청을 처리할 때 사용되는 영역 | 요청을 처리하고 응답을 받기 전까지 유지된다. 포워드 시에는 같은 요청을 사용하므로 유지된다. |
    | Session | 하나의 브라우저(클라이언트)와 관련된 영역 | 클라이언트가 브라우저를 유지하는 동안은 유지된다. |
    | Application | 하나의 웹 애플리케이션과 관련된 영역 | 서버에서 돌아가는 애플리케이션이 종료될 때까지 유지된다. |

    - request같은 경우, 포워드 시에는 요청이 유지되고, 리다이렉트 시에는 요청이 사라져 영역의 데이터도 사라진다.

- 일회용으로 사용될 데이터들은 주로 request 영역에 얹어서 보낸다. 그 요청에 한해서 사용되는 데이터이기 때문
- 로그인 정보와 같은 유지되어야 하는 데이터들은 session에 같이 보내게 된다.