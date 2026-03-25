# 스프링 클라우드(Spring Cloud)

- 스프링 클라우드는 최소한의 구성으로 마이크로 서비스 아키텍처를 빠르게 구축할 수 있는 기능들을 제공한다.
- 스프링 클라우드는 프로젝트 설정 및 구성을 단순화하고 가장 흔히 접할 수 있는 패턴의 해결안을 스프링 애플리케이션에 제공한다.

## 스프링 클라우드 컨피그(Spring Cloud Config)

- 스프링 클라우드 컨피그 서버는 스프링 부트로 만든 REST 기반의 애플리케이션이다.
- 스프링 클라우드 컨피그는 애플리케이션의 설정 데이터를 애플리케이션에서 분리시키고 관리하는 역할을 한다.
    - 많은 마이크로 서비스 인스턴스를 실행하더라도 항상 동일한 구성을 보장할 수 있다.
    - 설정 데이터가 실행 중 전달되어 동일한 서버와 애플리케이션이 모든 환경에 일관된 방식으로 동작시킬 수 있다

### 스프링 클라우드 컨피그 서버 구축

- 스프링 클라우드 컨피그 서버를 구축하려면 `pom.xml`에 의존성을 추가해야 한다.
    
    ```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-config-server</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-bootstrap</artifactId>
    </dependency>
    
    ```
    
- `bootstrap.yml` 파일에 스프링 클라우드 컨피그 서버 실행에 필요한 설정들을 해야한다.
    
    ```yaml
    server:
      port: 8888
    spring:
      application:
        name: config-server
      profiles:
        active:
        - git
      cloud:
        config:
          server:
            git:
              uri: # git 저장소 uri(SSH)
              search-paths:
              - # 설정 파일 경로
              ignore-local-ssh-settings: true  # PC의 로컬 SSH 세팅을 무시하고, Spring Cloud의 SSH 세팅을 사용
              host-key: # 호스트 키 지정(github.com)
              host-key-algorithm: # 공개 키 알고리즘 지정
              private-key: | # 비공개 키 지정(.ssh\id_ed5519 파일 내용 붙여넣기.)
                -----BEGIN OPENSSH PRIVATE KEY-----
                ...
                -----END OPENSSH PRIVATE KEY-----
    
    ```
    
- 실행 클래스에서 `@EnableConfigServer` 어노테이션으로 스프링 클라우드 컨피그 서버를 활성화한다.
    
    ```java
    @SpringBootApplication
    @EnableConfigServer
    public class Application {
      public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
      }
    }
    
    ```
    
- `bootstrap.yml` 파일에 지정한 Git 저장소에 애플리케이션 이름과 환경별로 구성할 설정 파일을 생성한다.
    - `app-name.properties` 또는 `app-name.yml`
    - `app-name-env.properties` 또는 `app-name-env.yml`
- 생성한 설정 파일을 읽어오려면 브라우저 주소창에 `localhost:8888/app-name/default` 또는 `localhost:8888/app-name/env`와 같이 입력하면 된다.

### 스프링 클라우드 컨피그 클라이언트 구축

- 스프링 클라우드 컨피그 클라이언트를 구축하려면 `pom.xml`에 의존성을 추가해야 한다.
    
    ```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-bootstrap</artifactId>
    </dependency>
    
    ```
    
- `bootstrap.yml` 파일에 스프링 클라우드 컨피그 서버의 위치를 알려주어야 한다.
    
    ```yaml
    spring:
      application:
        name: config-client
    cloud:
      config:
        uri:
        - http://localhost:8888
    
    ```
    
- 위의 설정 후 클라이언트 애플리케이션을 실행하면 스프링 클라우드 컨피그 서버에서 설정 정보를 받아온 후 클라이언트 애플리케이션을 실행한다.

## 스프링 서비스 디스커버리(Spring Cloud Discovery)

### 서비스 디스커버리(Service Discovery)

- 서비스 디스커버리를 사용하면 서비스를 소비하는 클라이언트에서 서버가 배포된 물리적 위치(IP 및 서버 이름)를 추상화할 수 있다.
- 서비스 디스커버리의 주요 목표는 서비스의 물리적 위치를 수동으로 구성할 필요 없이 위치를 알려줄 수 있는 아키텍처를 구축하는 것이다.
- 즉, 서비스 소비하는 클라이언트에서 물리적 위치가 아닌 논리적 이름을 사용하여 서버의 비즈니스 로직을 호출한다.

### 넷플릭스 유레카(Netflix Eureka) 서버 구축

- 스프링 클라우드와 넷플릭스 유레카의 서비스 디스커버리 엔진을 사용하여 서비스 디스커버리 패턴을 구현할 수 있다.
- 넷플릭스 유레카 서버를 구축하려면 `pom.xml`에 의존성을 추가해야 한다.
    
    ```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-bootstrap</artifactId>
    </dependency>
    
    ```
    
- `bootstrap.yml` 파일에 스프링 클라우드 컨피그 서버의 위치를 알려주어야 한다.
    
    ```yaml
    spring:
      application:
        name: eureka-server
      cloud:
        config:
          uri:
          - http://localhost:8888
    
    ```
    
- 스프링 클라우드 컨피그에서 지정한 Git 저장소에 설정 파일을 생성한다.
    
    ```yaml
    # eureka-server.yml
    server:
      port: 8761
    eureka:
      instance:
        # 유레카 인스턴스에 호스트 이름을 설정한다.
        hostname: localhost
      client:
        # 유레카 서버가 유레카 서비스에 등록되지 않도록 한다.
        register-with-eureka: false
        # 유레카 서버가 캐시 레지스트리 정보를 로컬에 캐시하지 않도록 지시한다.
        fetch-registry: false
        # 서비스 URL을 설정한다.
        service-url:
          defalut-zone: "http://${eureka.instance.hostname}:${server.port}/eureka/"
    
    ```
    
- 실행 클래스에서 `@EnableEurekaServer` 어노테이션으로 유레카 서버를 활성화한다.
    
    ```java
    @SpringBootApplication
    @EnableEurekaServer
    public class Application {
      public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
      }
    }
    
    ```
    

### 넷플릭스 유레카(Netflix Eureka) 클라이언트 구축

- 넷플릭스 유레카 클라이언트를 구축하려면 `pom.xml`에 의존성을 추가해야 한다.
    
    ```xml
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-bootstrap</artifactId>
    </dependency>
    
    ```
    
- `bootstrap.yml` 파일에 `spring.application.name` 프로퍼티가 설정되었는지 확인한다.
    
    ```yaml
    spring:
      application:
        name: eureka-client
    ...
    
    ```
    
- 클라이언트를 유레카에 등록할 수 있도록 Git 저장소의 설정 파일에 아래의 내용을 추가한다.
    
    ```yaml
    # eureka-client.yml
    eureka:
      instance:
        # 서비스 이름 대신 서비스 IP 주소 등록
        prefer-ip-address: true
      client:
        # 유레카 서비스 등록 여부
        register-with-eureka: true
        # 레지스트리 사본을 로컬에 내려받기
        fetch-registry: true
        # 유레카 서비스 위치 설정
        service-url:
          defalue-zone: http://localhost:8761/eureka/
    
    ```

## 스프링 클라우드 게이트웨이(Spring Cloud Gateway)

### 서비스 게이트웨이(Service Gateway)

- 서비스 게이트웨이는 클라이언트와 호출되는 서비스 사이에서 중개 역할을 한다.
- 클라이언트는 각 서비스 URL을 직접 호출하지 않고 서비스 게이트웨이를 통해 서비스와 통신한다.
- 서비스 호출의 중앙 집중화로 보안 인가, 인증, 콘텐츠 필터링과 라우팅 규칙 등 표준 서비스 정책을 시행할 수 있다.

### 스프링 클라우드 게이트웨이(Spring Cloud Gateway) 구축

- 스프링 클라우드 게이트웨이를 구축하려면 `pom.xml`에 의존성을 추가해야 한다.
    
    ```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-bootstrap</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-gateway-server-webflux</artifactId>
    </dependency>
    
    ```
    
- `bootstrap.yml` 파일에 스프링 클라우드 컨피그 서버의 위치를 알려주어야 한다.
    
    ```yaml
    spring:
      application:
        name: gateway-server
      cloud:
        config:
          uri:
          - http://localhost:8888
    
    ```
    
- 스프링 클라우드 컨피그에서 지정한 Git 저장소에 설정 파일을 생성한다.
    
    ```yaml
    # gateway-server.yml
    server:
      port: 8000
    eureka:
      instance:
        prefer-ip-address: true
      client:
        register-with-eureka: true
        fetch-registry: true
        service-url:
          default-zone: http://localhost:8761/eureka/
    
    ```
    
- 게이트웨이에 유입된 호출이 해당 서비스로 매핑하려면 설정 파일에 추가로 설정을 작성해야 한다.
    
    ```yaml
    spring:
      cloud:
        gateway:
          server:
            webflux:
              routes:
              - id: department-service
                uri: lb://department-service
                predicates:
                - Path=/department/**
                filters:
                - RewritePath=/department/(?<path>.*), /$\\{path}
    
    ```