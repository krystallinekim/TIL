# SQL

## 데이터베이스 기초

### 데이터베이스 종류

- RDBMS ← 가장 널리 많이 쓰임
    - MySQL
    - PostgreSQL
    - Oracle
    - SQlite
    - Maria DB
- Document DB (A.K.A NoSQL)
- Key-Value DB
- Graph DB

### 스키마(Schema)

데이터베이스의 구조와 제약조건을 정의한 것 = 데이터가 어떻게 저장되고 관리될지 설계도

포함 요소: 테이블 구조, 데이터 타입, 제약조건, 관계 등

 - 테이블의 Schema를 모델링한다 = Column의 구조를 계획한다

-> 테이블 생성부터 미리 계획하고 작성하는게 좋음

## SQL 문법 기초

- **`;`**
    
    현재 줄의 명령어를 실행, 코드 하나에 하나씩 무조건 있어야 함

    하나의 작업과 다음 작업의 구분을 무조건 ;로 하기 때문

- `--`

    주석처리

    ```sql
    -- 버전 확인
    SELECT VERSION();
    ```


- **대문자/소문자**

    보통 정해진 명령어(SELECT, FROM, ...)는 대문자, 내가 선택한 건(테이블 이름, 컬럼 이름, ...) 소문자 사용 (국룰임)
    ```sql
    SELECT * FROM table_name;
    ```

- **데이터 타입**

    | 타입         | 설명     | 예시                    |
    | ---------- | ------ | --------------------- |
    | INT        | 정수     | `age INT`             |
    | VARCHAR(n) | 가변 문자열 | `name VARCHAR(50)`    |
    | TEXT       | 긴 문자열  | `content TEXT`        |
    | DATE       | 날짜     | `birth_date DATE`     |
    | DATETIME   | 날짜+시간  | `created_at DATETIME` |

    `' '` : 텍스트/가변 문자열 처리 -> ***글자는 무조건 감싸야 함!!!***



## DDL vs DML

| 구분 | DDL (Data Definition Language) | DML (Data Manipulation Language) |
| --- | --- | --- |
| 목적 | 데이터베이스 구조 정의/변경 | 데이터 조작 |
| 대상 | 테이블, 데이터베이스, 스키마 | 데이터 (행) |
| 주요 명령어 | CREATE, ALTER, DROP | INSERT, SELECT, UPDATE, DELETE |
| 실행 결과 | 구조 변경 | 데이터 변경 |

### DDL(데이터 정의)

1. 데이터베이스

    ```sql
    -- 데이터베이스 생성
    CREATE DATABASE database_name;

    -- 데이터베이스 목록 조회
    SHOW DATABASES;

    -- 데이터베이스 선택(사용)
    USE database_name;

    -- 데이터베이스 삭제
    DROP DATABASE IF EXISTS database_name;
    ```

1. **`CREATE`** : 테이블 생성 

    ```sql
    CREATE TABLE table_name (
        id          INT         AUTO_INCREMENT PRIMARY KEY,
        name        VARCHAR(30) NOT NULL,
        email       VARCHAR(50) UNIQUE,
        join_date   DATE        DEFAULT CURRENT_DATE 
    );
    ```

    - **주요 제약조건**

        - **`PRIMARY KEY`**

            각 행(가로줄)의 고유 식별자
            
            중복 불가, NULL 불가, 테이블당 1개 / 필수는 아닌데 어지간하면 있어야 함
            
        - **`AUTO_INCREMENT`**

            숫자가 자동으로 증가함

            주로 ID 등에서 PRIMARY KEY와 함께 사용

            `id INT AUTO_INCREMENT PRIMARY KEY`

        - **`NOT NULL`**
        
            입력을 강제함(빈 값 입력 불가, 에러남) / 중복값은 가능함
            
            `name VARCHAR(30) NOT NULL`

        - **`UNIQUE`**

            중복값 방지용(중복시 아예 입력 불가) / 아예 입력하지 않는 건 가능(NULL이 여러개 있어도 중복으로 치지 않음)

            `email VARCHAR(50) UNIQUE`


        - **`DEFAULT`**
        
            값 미입력 시 기본값 자동 입력
            
            NOT NULL과 대체되는 개념임(빈칸일때 특정값을 보여줄지 / 에러를 반환할지)

            `join_date DATE DEFAULT(CURRENT_DATE)`


1. **`SHOW / DESC`** : 테이블 구조 확인 

    ```sql
    -- 테이블 목록 보기
    SHOW TABLES;

    -- 테이블 구조 보기
    DESC table_name;
    ```

1. **`ALTER`** : 테이블 구조 변경 

    ```sql
    -- 컬럼 추가
    ALTER TABLE table_name ADD COLUMN column_name datatype;

    -- 컬럼 이름 + 데이터 타입(+제약조건) 수정
    ALTER TABLE members CHANGE COLUMN column_name new_name datatype;

    -- 컬럼 데이터 타입(+제약조건) 수정
    ALTER TABLE members MODIFY COLUMN column_name datatype;

    -- 컬럼 삭제
    ALTER TABLE table_name DROP COLUMN column_name;
    ```

1. **`DROP`** : 테이블 삭제 
    
    ```sql
    DROP TABLE table_name;
    ```

    `DROP` 뒤에 `IF EXISTS`를 붙이면 있는 경우에만 삭제해줌 (+ 에러가 안남 / - 문제가 있어도 에러를 못봄)


### DML(데이터 조작)

1. **`INSERT`** : 데이터 입력

    ```sql
    -- 단일 행 입력
    INSERT INTO table_name (column1, column2) VALUES (value1, value2);

    -- 다중 행 입력
    INSERT INTO table_name (column1, column2) VALUES
    (value1, value2),
    (value3, value4);
    ```

1. **`SELECT`** : 데이터 조회

    ```sql
    -- 전체 조회
    SELECT * FROM table_name;

    -- 특정 컬럼 조회
    SELECT column1, column2 FROM table_name;

    -- 조건부 조회
    SELECT * FROM table_name WHERE condition;
    ```
    이 때 condition에는 PK인 `id=2` 말고도 `name='이름'` 등의 일반 키도 들어갈 수 있다.
    
1. **`UPDATE`** : 데이터 수정
    
    ```sql
    UPDATE table_name SET column1 = value1 WHERE condition;
    ```

1. **`DELETE`** : 데이터 삭제
    
    ```sql
    DELETE FROM table_name WHERE condition;
    ```

    `SELECT`, `UPDATE`, `DELETE` 뒤에는 항상 `WHERE condition`이 들어간다고 생각하면 됨


## 참고사항

### 데이터 지정 범위의 위험성

```sql
UPDATE users SET status = 'inactive';
-- 모든 유저를 비활성으로 처리
DELETE FROM users;
-- users의 모든 데이터를 삭제
```
데이터 범위를 지정하지 않게 되면 테이블 안의 모든 데이터를 수정/삭제해 버릴 수 있다.

```sql
UPDATE users SET status = 'inactive' WHERE id = 1;

DELETE FROM users WHERE status = 'deleted';
```
꼭 필요한 경우를 제외하면 WHERE 구문을 이용해 수정/삭제할 범위를 지정해 주도록 하자.


### CRUD

| | 의미 | 예시
| --- | --- | ---|
| Create | 생성 | DB CREATE, 테이블 CREATE, 데이터 INSERT 등|
|Read/Retrieve| 조회 | DB와 테이블 SHOW, DESC, 데이터 SELECT 등|
|Update |  변경 | 테이블 구조 변경(ALTER), 데이터 UPDATE 등|
| Delete | 삭제 | DB, 테이블 DROP, 데이터 DELETE 등|

데이터베이스에서 할 수 있는 모든 작업은 크게 4개의 operation중 하나로 이루어져 있다

이 중 Create/Delete는 한번만 하게 되고, Update는 필요할 때만 하게 되지만, Read는 정말 매 순간 하게 될 것


