# 아파치 메이븐(Apache Maven)

- 메이븐(Maven)은 자바 기반의 프로젝트 관리 도구로 프로젝트의 빌드 과정을 자동화한다.
    - 빌드: 개발자가 만든 코드가 실행되기 위한 과정으로 컴파일, 패키징 등이 그 과정에 포함된다.

- POM(Project Object Model)이라는 설정 파일를 통해 프로젝트의 정보, 의존성(라이브러리), 빌드 설정(일련의 과정) 등을 **통합 관리**한다.

## `pom.xml`

- 메이븐 프로젝트를 생성하면 프로젝트 최상위 디렉터리에 `pom.xml` 파일이 생성된다.(한 프로젝트에 하나 생성)
- pom.xml 파일에서는 메이븐 프로젝트의 정보, 의존성, 빌드 설정 및 플러그인을 XML(Extended Markup Language, 태그를 이용해 문서를 표현) 형식으로 정의한다.
    
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
        <!-- 프로젝트 정보 설정 -->
        <modelVersion>4.0.0</modelVersion>
    
        <groupId>com.beyond</groupId>
        <artifactId>servlet</artifactId>
        <version>1.0-SNAPSHOT</version>
        <name>01_Servlet</name>
        <packaging>war</packaging>
    
        <!-- 프로젝트에서 공통으로 사용할 값들을 정의 ****-->
        <properties>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            <maven.compiler.target>21</maven.compiler.target>
            <maven.compiler.source>21</maven.compiler.source>
            <junit.version>5.11.0</junit.version>
        </properties>
    
        <!-- 의존성 설정 -->
        <dependencies>
            <dependency>
                <groupId>jakarta.servlet</groupId>
                <artifactId>jakarta.servlet-api</artifactId>
                <version>6.1.0</version>
                <scope>provided</scope>
            </dependency>
    
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-api</artifactId>
                <version>${junit.version}</version>
                <scope>test</scope>
            </dependency>
    
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-engine</artifactId>
                <version>${junit.version}</version>
                <scope>test</scope>
            </dependency>
        </dependencies>
    
        <!-- 빌드 플러그인 설정 -->
        <build>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-war-plugin</artifactId>
                    <version>3.4.0</version>
                </plugin>
            </plugins>
        </build>
    </project>
    ```
    

## 메이븐 저장소

- 메이븐에서 저장소는 아티팩트(artifact, 라이브러리)를 저장하는 공간이다.

- 의존성 설정과 연관이 있다. `pom.xml`의 `dependency` 파트에 적혀 있음
    - `maven`, `gradle` 같은 빌드 툴에서는 원하는 아티팩트와, 그 아티팩트가 의존하는 다른 아티팩트까지 가져와 준다.

- 메이븐은 아티팩트 관리를 직접 하지 않아도 되게 편하게 해준다. 원래는 직접 의존성 체크하면서 관리해 줘야 함

- 직접 메이븐 레포지토리(`https://mvnrepository.com/`)에서 원하는 아티팩트를 검색해 다운로드 할 수도 있다.
    - `pom.xml`의 `<dependency>`에 복사한 메이븐 코드를 붙여넣고, 동기화하면 아티팩트를 사용할 수 있다.

### 원격 저장소

- 원격 저장소는 메이븐이 필요한 아티팩트(artifact)를 다운로드하는 저장소이다.

- 기본적으로 `Maven Central` 공개 저장소(`https://repo.maven.apache.org/maven2/`)에서 다운로드한다.

- 가끔 사내에서 직접 메이븐 저장소를 구축하고, 거기서 다운로드하는 경우도 있다.

### 로컬 저장소(.m2)

- 로컬 저장소는 원격 저장소에서 다운로드한 아티팩트를 저장하는 공간이다.
    - 운영체제 상관없이, 다운로드한 아티팩트는 `~/.m2/repository`에 저장된다.

- 메이븐은 먼저 로컬 저장소에서 필요한 아티팩트를 확인하고, 없을 경우 원격 저장소에서 다운로드한다.



## 메이븐 빌드 생명 주기(Maven Build Life Cycle)

- 메이븐은 미리 정의된 빌드 순서에 따라 동작한다.

- 미리 정의된 빌드 순서를 빌드 생명 주기(Lifecycle)라 한다.

- 빌드 생명 주기는 여러 개의 단계(Phase)로 구성되며, 순차적으로 실행된다.

- 각 단계(Phase)는 직접 작업을 수행하지 않고, 연결된 플러그인의 Goal을 실행한다.