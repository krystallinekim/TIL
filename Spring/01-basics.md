# 스프링 프레임워크(Spring Framework)

- 자바 애플리케이션 개발을 위한 오픈 소스 프레임워크로 일반적으로 스프링(Spring)이라고 부른다.
- 엔터프라이즈급 애플리케이션 개발을 위한 다양한 기능을 제공하며, 대한민국 전자정부 표준프레임워크의 기반 기술로 사용되고 있다.
- 스프링은 `DI(Dependency Injection)`와 `AOP(Aspect Oriented Programming)`를 기반으로 객체의 결합도를 낮추고, `POJO(Plain Old Java Object)` 기반 개발을 통해 유지 보수와 테스트가 용이한 구조를 제공한다.

## 프레임워크(Framework)

- 애플리케이션의 기본 구조와 공통 기능이 **미리 구현**되어 있으며, 개발자는 정해진 구조 안에서 기능을 구현할 수 있도록 지원한다.
- 소프트웨어를 구현하는 개발 시간을 줄이고, 반복되는 부분을 최소화할 수 있도록 설계되어 있다.
- 일정 수준 이상의 품질을 보장하는 애플리케이션을 개발할 수 있는 환경을 제공한다.

### 프레임워크(Framework)의 특징

1. 장점: 생산성과 품질 향상

    - 공통 기능이 미리 구현되어 있어 개발 시간을 단축할 수 있다.
    - 검증된 구조와 설계 패턴을 기반으로 하기 때문에 일정 수준 이상의 품질을 확보할 수 있다.
    - 추상화가 잘 되어 있기 때문에 코드의 일관성이 유지되어 유지 보수가 용이하다.

2. 단점: 구조적 제약과 학습 난이도

    - 정해진 구조와 규칙에 따라 개발해야 한다.
    - 동작 원리를 이해하지 못하면 사용이 어렵기 때문에 초기 학습 난이도가 비교적 높은 편이다.
    - 또한 동작 원리를 이해하지 못하면 문제 발생 시 원인 분석이 어려울 수 있다.
    - 사용되지 않는 기능에 대해서도 라이브러리가 포함될 수 있다.

## IoC (Inversion of Control)

- IoC(Inversion of Control)란, 프로그램 실행에 필요한 객체의 생성과 의존 관계 설정 등의 제어권을 개발자가 아닌 컨테이너가 담당하는 것을 의미한다.
    - 개발자는 IoC 컨테이너에 어떤 객체를 어떻게 생성, 연결, 소멸시킬지만 알려주면 된다.
    - 스프링은 IoC 구조를 기반으로 객체의 생성부터 소멸까지 생명주기를 관리한다.    

### 스프링 IoC 컨테이너

- 스프링에서 관리하는 객체를 `빈(Bean)`이라고 한다.
- 스프링 IoC 컨테이너는 이러한 `빈(Bean)`의 의존 관계를 설정하고 생명주기를 관리한다.
- 대표적인 스프링 IoC 컨테이너에는 `BeanFactory`와 `ApplicationContext`가 있다.
    
### 스프링 IoC 컨테이너의 종류

- **빈 팩토리**(BeanFactory)
    - 스프링의 가장 기본적인 IoC 컨테이너이다.
    - 기본적인 객체(Bean) 관리 기능을 제공한다.

- **애플리케이션 컨텍스트**(Application Context)
    - 빈 팩토리를 확장한(상속하는) IoC 컨테이너이다.
    - AOP 지원, 메시지 처리, 이벤트 처리 등 다양한 부가 기능을 제공한다.
    - 대부분의 스프링 애플리케이션에서 사용된다.
    
    | 종류 | 설명 |
    | --- | --- |
    | AnnotationConfigApplicationContext | 자바 기반 설정 클래스에서 스프링 애플리케이션 컨텍스트를 로드한다. |
    | AnnotationConfigWebApplicationContext | 자바 기반 설정 클래스에서 스프링 웹 애플리케이션 컨텍스트를 로드한다. |
    | GenericXmlApplicationContext | XML 설정 파일에서 스프링 애플리케이션 컨텍스트를 로드한다. |
    | ClassPathXmlApplicationContext | 애플리케이션 클래스패스(classpath)에 있는 하나 이상의 XML 파일에서 스프링 애플리케이션 컨텍스트를 로드한다. |
    | XmlWebApplicationContext | 웹 애플리케이션에 포함된 XML 파일에서 스프링 애플리케이션 컨텍스트를 로드한다. |

## DI (Dependency Injection, 의존성 주입)

- DI(Dependency Injection)란 하나의 객체가 의존하는 다른 객체를 외부에서 생성하고 주입받아 사용하는 것을 말한다.
    - 스프링에서만 쓰는게 아니라, 소프트웨어공학적으로 원래 있던 것
    
- 기존의 자바 어플리케이션에서는 다형성을 통해 객체 간의 결합도를 낮춰 왔다.
    - 스프링을 적용하게 되면 실제 객체를 생성하는 부분까지 제외할 수 있다. 

- 스프링은 객체들의 의존 관계를 연결할 수 있는 3가지(XML, Java Config, Annotation)의 설정 방법을 제공한다.
    - 객체 간의 의존 관계를 개발자가 직접 생성하지 않고, 설정 파일(XML, Java Config)이나 어노테이션 설정을 기반으로 애플리케이션 컨텍스트가 객체를 주입한다.
    - 이를 통해 의존 관계에 있는 객체들 간의 결합도를 낮출 수 있다.

### XML 설정 파일 이용

- 애플리케이션 컨텍스트 구동 시 생성해야 하는 객체(Bean)들과 의존 관계를 XML 파일로 작성하는 방식이다.
- `<beans>` 요소는 최상위 요소로 하위 요소들로 다양한 스프링 설정을 할 수 있다. (DI, AOP, Transaction 등)
- `<bean>` 요소는 애플리케이션 컨텍스트가 관리할 빈을 선언하는 요소이다.
    
    ```xml
    <!-- Student student = new Student(); -->
    <beans>
        <bean id="student" class="com.ismoon.spring.person.model.vo.Student"/>
    </beans>
    ```
    
- Setter 메소드를 통해 의존 관계가 있는 빈을 주입하려면 `<property>` 요소를 사용한다. (단, 일치하는 Setter 메소드가 있어야 된다.)
    
    ```xml
    <beans>
        <!-- 
        Student student = new Student();

        student.setName("홍길동");
        student.setWallet(money);
        -->
        <bean id="student" class="com.ismoon.spring.person.model.vo.Student">
            <property name="name" value="홍길동"/>
            <!-- 다른 객체(Bean)을 주고 싶다면, value 대신 ref를 사용함 -->
            <property name="wallet" ref="money"/>
        </bean>
        
        <!-- Wallet money = new Wallet(); -->
        <bean id="money" class="com.ismoon.spring.wallet.model.vo.Wallet" />
    </beans>
    ```
    
- 생성자를 통해 의존 관계가 있는 빈을 주입하려면 `<constructor-arg>` 요소를 사용한다. (단, 일치하는 생성자가 있어야 된다.)
    
    ```xml
    <beans>
        <!-- Student student = new Student("홍길동", money) -->
        <bean id="student" class="com.ismoon.spring.person.model.vo.Student">
            <constructor-arg name="name" value="홍길동"/>
            <!-- name 대신에 순서를 의미하는 index를 쓸 수도 있다(잘 안쓴다) -->
            <constructor-arg index="1" ref="money"/>
        </bean>
        
        <!-- Wallet money = new Wallet(); -->
        <bean id="money" class="com.ismoon.spring.wallet.model.vo.Wallet" />
    </beans>
    ```

- `<import>` 요소를 사용하여 다른 XML 설정을 가져올 수 있다.
    - 실제로 운영할 때는 주제별로 XML 파일들을 따로따로 관리해야 하기 때문
    
    ```xml
    <beans>
        <import resource="config.xml"/>
    </beans>
    ```

- `<property>`, `<constructor-arg>`를 이용해 빈을 주입할 때, 직접 요소를 사용하는게 아니라 p-namespace, c-namespace를 이용해 더 간단하게 표현할 수도 있다.

- XML 방식은 현재 권장되는 방식은 아니다.

### Java 설정 파일

- 애플리케이션 컨텍스트 구동 시 생성해야 하는 객체(Bean)들과 의존 관계를 Java 파일로 작성하는 방식이다.
- 애플리케이션 컨텍스트가 자바 파일을 설정 파일로 식별하기 위해서는 `@Configuration` 어노테이션을 클래스에 작성해야 한다.
- 설정 파일에는 어떠한 비즈니스 로직도 작성하지 않는다.

    ```java
    @Configuration
    public class JavaConfig { ... }
    ```


- 자바 파일에서 빈을 선언하기 위해서는 객체를 리턴하는 메소드를 만들고 `@Bean` 어노테이션을 메소드에 작성해야 한다.
    - `@Bean` 어노테이션은 싱글턴 패턴을 따른다. 하나 만들고 나면 다음부터는 그 객체를 주입함.
    
    ```java
    @Configuration
    public class JavaConfig {
        @Bean
        public Student student() { return new Student(); }
    }
    ```
    
- Setter 메소드를 통해 의존 관계가 있는 빈을 주입할 수 있다.
    
    ```java
    @Configuration
    public class JavaConfig {
        // Bean 이름을 따로 설정할 수 있다.
        // 설정하지 않으면 클래스 이름(student)을 따라감.
        @Bean("hong")
        public Student student() {
            Student student = new Student();
        
            student.setName("홍길동");
            student.setWallet(wallet());
        
            return student;
        }
        
        @Bean
        public Wallet wallet() { return new Wallet(); }
    }
    ```
    
- 생성자를 통해 의존 관계가 있는 빈을 주입할 수 있다.
    
    ```java
    @Configuration
    public class JavaConfig {
        @Bean("hong")
        public Student student() { return new Student("홍길동", wallet()); }
        
        @Bean
        public Wallet wallet() { return new Wallet(); }
    }
    ```
    
- `@Import` 어노테이션을 사용하여 다른 Java 설정 파일을 가져올 수 있다.
    - value값으로 클래스의 배열을 주면 여러 설정 파일들을 가져올 수도 있다.
    
    ```java
    @Configuration
    // @Import(value = {Config.class, Config2.class, ...})
    @Import(Config.class)
    public class JavaConfig { ... }
    ```

- `@Autowired` 어노테이션을 사용하여 알맞은 타입의 빈을 찾아 자동으로 의존성 주입을 할 수 있다.

    ```java
    @Bean
    public Owner lee(@Autowired @Qualifier("dog") Pet pet) { return new Owner("이몽룡", 24, pet); }

    @Bean
    public Dog dog() { return new Dog(); }

    @Primary
    @Bean
    public Cat cat() { return new Cat("야옹이"); }

    // >> lee에는 dog가 pet으로 들어간다.
    ```
    - `@Autowired`에서 선언된 타입의 빈이 여러 개일 경우, 어플리케이션 컨텍스트가 어떤 빈을 가져와야 할 지 몰라 에러가 발생한다.
        - `@Primary`를 이용해 우선적으로 가져올 빈을 선언할 수 있다.
        - `@Qualifier(이름)`을 이용하면 특정 빈을 선택해서 선언할 수 있다.
    
    - `@Autowired` 로 받을 빈이 없다면, 기본적으로 에러를 반환한다.
        - required 속성의 기본값이 true이기 때문인데, false를 줄 경우 빈이 없으면 null을 반환한다. 
        - 보통 기본값 그대로 쓴다. null처리하기도 힘들고, 굳이 할 이유가 없기 때문

        ```java
        @Autowired(required = false)
        private Character character;

        @Test
        void create() { System.out.println(character); }
        // null을 반환함
        ```


- 어플리케이션 컨텍스트를 직접 생성하지 않고, 자동으로 생성하도록 할 수 있다.
    - `@ExtendWith`을 이용하면, Junit에서 Spring을 사용할 수 있도록 자동으로 확장(`SpringExtension.class` 기준)해준다.
    - `@ContextConfiguration`에서 설정파일을 주면 컨텍스트를 설정대로 생성한다.
        - XML 설정이면 `locations = classpath: ... `, 자바 설정이면 `classes = 설정 클래스`

    - 이 때 `@Autowired`를 이용해 원하는 타입의 빈을 자동으로 연결할 수 있다.
        - 역시 `@Qualifier`를 이용해 원하는 빈을 선언할 수 있다.
        ```java
        @ExtendWith(SpringExtension.class)
        @ContextConfiguration(locations = "classpath:spring/root-context.xml")
        class OwnerTest {

            @Autowired
            @Qualifier("lee")
            private Owner owner;
                    
            @Test
            void autowiredTest() {
                assertThat(owner).isNotNull();
                assertThat(owner.getPet()).isNotNull();
            }
        }
        // 이 때 owner에는 lee 빈이 들어간다.
        ```

- `@Autowired` 어노테이션은 필드, 메소드, 생성자에도 적용할 수 있다.

- 요즘은 보통 어노테이션 방식을 사용하고, 어노테이션을 붙이기 어려운 경우에 자바 방식을 사용한다.

### 어노테이션 방식

- 스프링 컨테이너 구동 시 생성해야 하는 객체(Bean)들과 의존 관계를 어노테이션을 사용하여 구성하는 방식이다.
- 어노테이션으로 빈을 선언하기 위해서는 빈으로 생성하고 싶은 클래스에 바로 `@Component`을 작성해야 한다.
    - 따로 ID를 지정 안하면 클래스명이 빈 이름이 된다.
    
    ```java
    @Component
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Student {
        private String name;
        
        private Wallet wallet;
    }
    ```
    
- 컴포넌트 스캐닝(Component Scanning)을 활성화해 `@Component` 어노테이션이 작성된 클래스가 빈으로 등록된다.
    - 컴포넌트 스캐닝 자체는 XML 혹은 자바 설정 파일에서 활성화한다고 선언해야 한다.
    - 컴포넌트 스캐닝의 기준 패키지를 주면, 그 하위의 `@Componenet` 어노테이션이 붙은 클래스를 전부 검색해 빈으로 등록

    ```xml
    <!-- XML 방식 -->
    <beans>
        <context:component-scan base-package="com.ismoon.spring" />
    </beans>
    ```
    
    ```java
    // Java 방식
    @Configuration
    @ComponentScan("com.ismoon.spring")
    public class JavaConfig { ... }
    ```

- `@Value` 어노테이션을 사용하면 빈이 아닌 값도 주입할 수 있다.
    
    ```java
    @Component
    public class Student {
        @Value("홍길동")
        private String name;

        @Autowired
        private Wallet wallet;
    }
    ```

- `@Autowired` 어노테이션을 사용하면 알맞은 타입의 빈을 찾아 자동으로 주입을 할 수 있다.
    1. 필드에 직접 빈을 주입(필드에 바로 어노테이션 사용), 
    2. Setter 메소드로 빈을 주입(직접 Setter 메소드를 생성하고 메소드 전체에 어노테이션 사용, lombok의 `@Setter(onMethod_ = @Autowired)` 사용)
    3. 생성자를 생성할 때 빈을 주입하면 초기화 시 자동으로 알맞은 빈을 주입하게 된다.(직접 생성자 생성, 필드를 final로 선언 후 lombok의 `@RequiredConstructor` 사용)
        - 가장 선호되는 방식이다. 의존하는 객체가 있어야만 전체 객체가 생성될 수 있기 때문임

    ```java
    @Component
    @RequiredConstructor
    public class Character {
        private final Weapon weapon;
    }
    ```

- `character.properties` 파일을 읽어오기

    1. 설정 파일에 `character.properties` 등록
        - XML: location 속성에 지정된 properties 파일의 값을 읽어오게 설정
            ```xml
            <beans>
                <context:property-placeholder location="경로"/>`
            </beans>
            ```
        - 자바: 자바 설정 파일에 정해진 타입의 빈을 추가
            ```java
            public class RootConfig{
                @Bean
                public PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
                    PropertySourcesPlaceholderConfigurer configurer
                            = new PropertySourcesPlaceholderConfigurer();

                    configurer.setLocation(new ClassPathResource("character.properties"));

                    return configurer;
                }
            }

    2. 읽어올 properties 파일이 여러 개인 경우
        - XML: location에 파일 여러개를 `,`로 구분해서 넣는다.
            ```xml
            <context:property-placeholder location="character.properties,driver.properties"/>
            ```
        - 자바: `PropertySourcesPlaceholderConfigurer`의 `.setLocations()` 메소드를 이용함.
        
            ```java
            configurer.setLocations(
                    new ClassPathResource("character.properties"),
                    new ClassPathResource("driver.properties"));
            ```
            ``` 

    3. spring property placeholder를 이용해 프로퍼티 파일의 값을 읽어온다.
        - 이 떄 플레이스홀더의 형태는 `${key:기본값}` 형태이다.
            
        ```java
        public class Character {

            @Value("${character.name:홍길동}")
            private String name;
        
        }
        ```

## AOP (Aspect Oriented Programming, 관점 지향 프로그래밍)

- 트랜잭션, 로깅, 보안과 같은 공통 관심사를 핵심 비즈니스 로직과 분리하여 모듈화한다.
- 이를 통해 공통 관심사와 비즈니스 로직 간의 결합도를 낮출 수 있다.

- 애플리케이션의 여러 부분에 걸쳐 있는 기능을 횡단 관심사(Cross-cutting concerns)라고 한다.
- AOP는 이러한 횡단 관심사를 분리하고 분리한 기능을 어디에 어떻게 적용할지 선언적으로 정의할 수 있다.
- AOP의 목적은 횡단 관심사와 이에 영향받는 객체 간 결합도를 낮추는데 있다.


### AOP 용어

- 애스펙트(Aspect)는 횡단 관심사를 분리하여 작성한 클래스이다. (어드바이스 + 포인트컷)
- 어드바이스(Advice)는 애스펙트가 해야 할 작업과 언제 그 작업을 수행해야 하는지 정의하는 것을 말한다.
- 조인포인트(JoinPoint)는 어드바이스가 적용될 수 있는 모든 곳을 의미한다. (메소드 호출 지점, 예외 발생 지점, 필드 등)
- 포인트컷(PointCut)은 여러 조인포인트 중에 실제 어드바이스가 적용될 조인 포인트를 정의하는 것을 말한다.
- 대상 객체(Target Object)는 애스펙트가 적용될 객체를 말한다.
- 위빙(Weaving)은 대상 객체에 애스펙트를 적용하는 것을 말한다.
    
    
    | 위빙 시점 | 설명 |
    | --- | --- |
    | 컴파일 시 위빙 | 대상 클래스가 컴파일될 때 위빙된다. |
    | 클래스 로딩 시 위빙 | 대상 클래스가 JVM에 로드될 때 위빙된다. |
    | 런타임 시 위빙 | 애플리케이션 실행 중에 위빙된다. (스프링) |

### Spring AOP

1. 메소드 조인 포인트만 지원한다.

    - 대상 객체의 메소드가 호출되는 런타임 시점에만 어드바이스을 적용할 수 있다.
    - AspectJ 같은 고급 AOP 프레임워크를 사용하면 객체의 생성, 필드 값의 조회와 조작, static 메소드 호출 및 초기화 등의 다양한 작업에 어드바이스를 적용할 수 있다.

2. 프록시(Proxy) 기반의 AOP를 지원한다.

    - 프록시(Proxy)는 대상 객체에 어드바이스가 적용된 후 생성되는 객체로 대상 객체에 직접 접근을 제한하는 역할을 하는 객체이다.
    - Spring AOP는 대상 객체에 대한 프록시를 만들어 제공하며, 대상 객체를 감싸는 프록시는 런타임 시에 생성된다.
    - 프록시는 대상 객체의 메소드 호출을 가로채어 어드바이스를 수행하고 대상 객체의 메소드를 호출하거나 대상 객체의 메소드를 호출 후 어드바이스를 수행한다.


### Spring AOP 구현 방법

- 스프링은 AspectJ의 어노테이션을 사용하여 애스펙트를 생성할 수 있다.
    
    ```java
    @Aspect
    @Component
    public class 클래스명 {
        @Before("포인트컷 지정자")
        public void before() {
            // 메소드 실행 전에 적용되는 어드바이스를 정의
        }
        
        @After("포인트컷 지정자")
        public void after() {
            //  메소드 실행 후에 적용되는 어드바이스를 정의
        }
        
        @AfterReturning("포인트컷 지정자")
        public void success() {
            // 메소드가 정상적으로 실행된 후에 적용되는 어드바이스를 정의
        }
        
        @AfterThrowing("포인트컷 지정자")
        public void fail() {
            // 메소드가 예외를 발생시킬 때 적용되는 어드바이스를 정의
        }
        
        @Around("포인트컷 지정자")
        public String around(ProceedingJoinPoint jp) {
            // 메소드 호출 이전, 이후, 예외 발생 등 모든 시점에 적용 가능한 어드바이스를 정의
        }
    }
    ```
    
- AsepctJ 어노테이션을 적용을 위해서는 설정 파일에 아래와 같이 프록시 설정을 해야한다.
    
    ```xml
    <!-- XML 설정 -->
    <beans>
        <aop:aspectj-autoproxy/>
    </beans>
    ```
    
    ```java
    // Java 설정
    @Configuration
    @EnableAspectJAutoProxy
    public class RootConfig {
    }
    ```
## POJO (Plain Old Java Object)

- 특정 프레임워크나 기술에 종속되지 않는 순수 자바 객체 기반으로 개발하는 방식을 의미한다.
- 별도의 상속이나 구현을 강제하지 않으며, 일반적인 자바 객체처럼 사용할 수 있다.
- 이를 통해 프레임워크에 대한 강한 의존 없이 개발 및 테스트가 용이하고, 유지보수가 쉬운 구조를 만들 수 있다.
