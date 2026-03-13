# 타임리프(Thymeleaf)

## 1. 타임리프(Thymeleaf)

- 타임리프는 템플릿 엔진으로 HTML5와 완전히 호환되며 스프링 부트는 타임리프를 사용하는 것을 권장하고 있다.
- 스프링 부트에서 타임리프를 사용하려면 `pom.xml`에 의존성을 추가해야 한다.
    
    ```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    ```
    
- 또한 HTML 문서에서 타임리프 속성들을 사용하려면 HTML 문서에 `xmlns`를 설정해야 한다.
    
    ```html
    <!DOCTYPE html>
    <html xmlns:th="http://www.thymeleaf.org">
      ...
    </html>
    ```
    

## 2. 타임리프 속성

- 태그 안에 텍스트를 설정하려면 `th:text` 속성을 사용한다.
    
    ```html
    <!-- 컨트롤러로 전달받은 Model에 접근할 때는 ${..} 표현법을 사용한다. -->
    <span th:text="${message}"></span>
    ```
    
- 태그에 링크를 설정하려면 `th:href` 속성을 사용한다.
    
    ```html
    <!-- 링크를 지정할 때는 @{..} 표현법을 사용한다. -->
    <a th:href="@{/home}">Home</a>
    ```
    
- form 태그에서 데이터를 전송할 서버의 URL을 지정할 때는 `th:action` 속성을 사용한다.
    
    ```html
    <form th:action="@{/submit}" method="post">
      ...
    </form>
    ```
    
- input 태그에 값을 설정하려면 `th:value` 속성을 사용한다.
    
    ```html
    <input type="text" th:value="${address}"></span>
    ```
    
- 조건을 만족하는 경우 HTML 요소를 표시하려면 `th:if` 속성을 사용한다.
    
    ```html
    <span th:if="${name != null}" th:text="${name}"></span>
    ```
    
- 배열이나 컬렉션 요소를 반복해 요소를 표시하려면 `th:each`를 사용한다.
    
    ```html
    <ul>
      <li th:each="item : ${items}" th:text="${item}"></li>
    </ul>
    ```
    

## 3. 프래그먼트(Fragment)

- 타임리프에서는 `th:fragment` 속성을 이용해 공통 부분을 조각화하고 재사용할 수 있다.
    
    ```html
    <!-- fragments.html -->
    
    <!DOCTYPE html>
    <html xmlns:th="<http://www.thymeleaf.org>">
    <head>
        <title>Fragments</title>
    </head>
    <body>
        <header th:fragment="header">
            <h1>Site Header</h1>
        </header>
        <div th:fragment="footer">
            <p>Site Footer</p>
        </div>
    </body>
    </html>
    ```
    
- 프래그먼트를 사용할 때는 `~{ fragment 경로 :: fragment 이름 }` 표현법을 사용한다.
    - `th:replace`는 해당 요소를 프래그먼트로 지정한 요소로 대체한다.
    - `th:insert`는 해당 요소 내부에 프래그먼트로 지정한 요소를 삽입한다.
    
    ```html
    <!-- index.html -->
    
    <!DOCTYPE html>
    <html xmlns:th="<http://www.thymeleaf.org>">
    <head>
        <title>제목</title>
    </head>
    <body>
        <header th:replace="~{fragments :: header}"></header>
    
        <main>
          ...
        </main>
    
        <footer th:insert="~{fragments :: footer}"></footer>
    </body>
    </html>
    ```