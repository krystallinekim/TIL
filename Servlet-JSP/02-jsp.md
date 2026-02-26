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


## 5. JSP 내장 객체

- JSP에서 기본적으로 제공하는 객체들로 request, response, out 등 스크립트릿 태그와 표현식 태그에서 사용할 수 있게 선언된 객체이다.

### 5.1. JSP 내장 객체 종류

- JSP에서 기본적으로 제공하는 객체들은 아래와 같다.
    
    
    | 내장 객체 명 | 설명 |
    | --- | --- |
    | request | HttpServletRequest 객체 참조 변수 |
    | response | HttpServletResponse 객체 참조 변수 |
    | out | JspWriter 객체 참조 변수 |
    | session | HttpSession 객체 참조 변수 |
    | application | ServletContext 객체 참조 변수 |
    | page | 현재 JSP 페이지에 대한 참조 변수 |
    | pageContext | JSP 페이지와 관련된 다른 내장 객체를 얻어내는 객체 |
    | config | JSP 페이지에 대한 설정 정보를 저장하고 있는 객체 |
    | exception | 발생한 Throwable 객체에 대한 참조 변수 |

## 6. JSP 내장 객체 영역

- Page < Request < Session < Application 영역 순으로 범위가 넓다.
    
    | 영역 | 설명 |
    | --- | --- |
    | Page | 하나의 JSP 페이지를 처리할 때 사용되는 영역
    pageContext 내장 객체를 통해 접근할 수 있는 영역 |
    | Request | 하나의 요청을 처리할 때 사용되는 영역
    request 내장 객체를 통해 접근할 수 있는 영역 |
    | Session | 하나의 브라우저와 관련된 영역
    session 내장 객체를 통해 접근할 수 있는 영역 |
    | Application | 하나의 웹 애플리케이션과 관련된 영역
    application 내장 객체를 통해 접근할 수 있는 영역 |