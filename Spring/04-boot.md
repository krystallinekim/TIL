# 스프링 부트(Spring Boot)

## 1. 스프링 부트(Spring Boot)

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

## 2. 스프링 부트 스타터

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

## 3. 스프링 부트 코드 이해하기

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

## 4. 프로파일(Profile) 설정

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