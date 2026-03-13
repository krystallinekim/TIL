# 스프링 부트(Spring Boot)

- 스프링은 장점이 많은 개발도구이지만 설정이 매우 복잡하다는 단점이 있다.
- 스프링 부트는 스프링 프레임워크를 더 쉽고 빠르게 이용할 수 있도록 만들어주는 도구이다.
    - 빠르게 스프링 프로젝트를 설정할 수 있다.
    - 스타터를 사용해 간편하게 의존성을 사용할 수 있다.
    - 웹 애플리케이션 서버(WAS)를 내장하고 있어서 따로 설치하지 않아도 독립적으로 실행할 수 있다.
- 스프링과 스프링 부트의 차이
    - 스프링은 애플리케이션을 개발할 때 필요한 환경을 수동으로 구성해야 한다.
    - 스프링 부트는 필요한 환경을 자동으로 구성하기 때문에 수동으로 개발 환경을 구성할 필요가 없다.
    - 스프링 부트는 WAS를 내장하고 있어서 jar 파일만 만들면 별도의 WAS 설정을 하지 않아도 애플리케이션을 실행할 수 있다.
    
    | 구분 | 스프링(Spring) | 스프링 부트(Spring Boot) |
    | --- | --- | --- |
    | 설정 방식 | 개발자가 직접 설정 | 자동 구성(Auto Configuration) |
    | 설정 파일 | 일부 설정을 XML로 관리 | XML 설정 사용하지 않음 |
    | 데이터베이스 | 인메모리 DB 자동 지원 없음 | 인메모리 DB 자동 설정 지원 |
    | WAS | 별도의 WAS 필요 | 내장 WAS 제공 |

## 스프링 부트 스타터

- pom에 의존성으로 작성됨
- 스프링 부트 스타터는 의존성이 모여있는 그룹이다.
- 스타터를 사용하면 필요한 기능을 간편하게 설정할 수 있다.
- 스타터는 `spring-boot-starter-{작업유형}`으로 작성한다.
    - spring-boot-starter-web
        - Spring MVC를 사용해서 웹 서비스를 개발할 때 필요한 의존성 모음
    - spring-boot-starter-test
        - 스프링 애플리케이션을 테스트하기 위해 필요한 의존성 모음
    - spring-boot-starter-validation
        - 유효성 검사를 위해 필요한 의존성 모음
    - spring-boot-starter-actuator
        - 모니터링을 위해 애플리케이션에서 제공하는 다양한 정보를 제공하기 쉽게하는 의존성 모음
    - spring-boot-starter-jpa
        - ORM을 사용하기 위한 인터페이스의 모음인 JPA를 더 쉽게 사용하기 위한 의존성 모음

## 코드

- `@SpringBootApplication`은 스프링 부트의 자동 설정과 컴포넌트 스캔을 활성화하는 어노테이션이다.
- `@SpringBootApplication`은 `@Configuration`, `@EnableAutoConfiguration`, `@ComponentScan` 어노테이션을 포함하고 있다.
    - @SpringBootConfiguration
        - 스프링 부트 관련 설정을 나타내는 어노테이션이다.
        - @Configuration을 상속해서 만든 어노테이션이다.
    - @EnableAutoConfiguration
        - 스프링 부트 자동 구성을 활성화하는 어노테이션이다.
        - 스프링 부트 애플리케이션이 실행될 때 스프링 부트의 메타 파일(spring.factories)을 읽고 정의된 설정들을 자동으로 구성하는 역할을 한다.
        - spring.factories안에 자동 구성해야하는 목록을 가지고 있다.
    - @ComponentScan
        - 사용자가 등록한 빈을 읽고 등록하는 어노테이션이다.
- `SpringApplication.run()`은 스프링 부트 애플리케이션을 실행하고 애플리케이션 컨텍스트를 생성한다.
    - 첫 번째 매개값은 스프링 부트 애플리케이션의 메인 클래스로 사용할 클래스를 지정한다.
    - 두 번째 매개값은 커맨드 라인의 인수들을 전달한다.

## 프로파일(Profile) 설정

- 스프링 부트는 기본적으로 `application.properties` 또는 `application.yml` 파일을 사용하여 설정을 관리한다.
- 스프링 부트는 프로파일을 설정해서 환경별로 다른 설정을 적용할 수 있는데 `application-{profile}.properties` 또는 `application-{profile}.yml`로 환경별로 설정 파일을 만들 수 있다.
    
    ```yaml
    # application.yml
    server:
        port: 8080
    ```
    
    ```yaml
    # application-dev.yml
    spring:
        profiles: dev
    server:
        port: 8088
    logging:
        level:
        '[org.springframework.web]': debug
    ```
    
    ```yaml
    # application-live.yml
    spring:
        profiles: live
    server:
        port: 8089
    ```
    
- 스프링 부트 애플리케이션을 실행할 때 명령줄 인수로 프로파일을 설정할 수 있다.
    
    ```bash
    java -jar myapp.jar --spring.profiles.active=dev
    ```
    
- 운영체제의 환경 변수를 설정하여 프로파일을 지정할 수도 있다.
    
    ```bash
    # Linux / Unix
    export SPRING_PROFILES_ACTIVE=dev
    
    # Windows
    set SPRING_PROFILES_ACTIVE=dev
    ```
    
- 스프링 부트 애플리케이션 코드 내에서 프로파일을 설정할 수도 있다.
    
    ```java
    // Application.java
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).profiles("dev").run(args);
    }
    
    // 특정 프로파일에서만 활성화되는 빈을 설정
    @Bean
    @Profile("dev")
    public TestBean testBean() {
        return new TestBean();
    }
    ```
    
- 테스트 코드에서 프로파일을 설정할 수도 있다.
    
    ```java
    @SpringBootTest
    @ActiveProfiles("dev")
    public class ApplicationTest {
        @Test
        void contextLoads() {
        }
    }
    ```

## 타임리프(Thymeleaf)

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
    

### 타임리프 속성

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
    

### 프래그먼트(Fragment)

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