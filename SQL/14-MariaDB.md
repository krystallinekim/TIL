# MariaDB

## 변수

1. 전역변수
DBMS 전체에서 선언된 변수

```sql
SET @gender = 'M';
SELECT @gender;
```

1. 지역변수

프로시저 내부에서만 선언된 지역변수

```sql
BEGIN
    DECLARE hyear INT;
END$$
```


## 스토어드 프로시저

- 스토어드 프로시저란 MariaDB에서 제공되는 프로그래밍 기능이다.
- 복잡한 쿼리문을 다시 입력할 필요 없이 간단하게 호출해서 실행 결과를 얻을 수 있다.

### 1. 스토어드 프로시저 생성

- 프로시저를 생성하려면 `CREATE PROCEDURE` 구문을 사용해야 한다.
    
    ```sql
    DELIMITER $$  -- 임시로 종료문자를 $$로 바꿈
    CREATE PROCEDURE {프로시저이름}
    BEGIN
      -- SQL 프로그래밍 코드 작성
      -- SELECT * FROM employees;  -- 여기에 ;를 써야해서
    END $$
    DELIMITER ;  -- 다시 ;로 종료문자를 돌림
    
    CALL {프로시저이름}();
    ```
    

#### 1.1. 매개 변수의 사용

- 스토어드 프로시저에는 매개 변수를 지정할 수 있다.
    - `IN`은 외부에서 값을 받는 매개변수
    - `OUT`은 외부로 내보낼 매개변수


    ```sql
    DELIMITER $$
    CREATE PROCEDURE {프로시저이름} (
      IN {매개변수} 데이터타입,
      OUT {매개변수} 데이터타입
      ...
    )
    BEGIN
      -- SQL 프로그래밍 코드 작성(매개변수 사용한)
    END $$
    DELIMITER ;
    
    CALL {프로시저이름}([{매개값}, ...]);
    ```
    

#### 2.2. 조건문


- IF
    
    ```sql
    IF 조건식 THEN
      실행 문장;
    ELSE
      실행 문장;
    END IF;
    ```
    
- CASE
    
    ```sql
    CASE
      WHEN 조건식 1 THEN 실행 문장 1;
      WHEN 조건식 2 THEN 실행 문장 2;
      ...
      [ELSE 실행 문장]
    END CASE;
    ```
    

#### 2.3. 반복문

- WHILE
    
    ```sql
    WHILE 조건식 DO
      실행 문장;
    END WHILE;
    ```
    

#### 3.4. 오류 처리

- MariaDB는 오류가 발생할 경우 직접 오류를 처리하는 방법을 제공한다.
    
    ```sql
    DELIMITER $$
    CREATE PROCEDURE errorProc()
    BEGIN
        DECLARE CONTINUE HANDLER FOR 1146 SELECT '테이블이 없어요ㅠㅠ' AS '메시지';
        SELECT * FROM noTable;
    END $$
    DELIMITER ;
    
    CALL errorProc();
    ```
    

### 4. 프로시저 삭제

- 프로시저를 삭제하려면 `DROP PROCEDURE` 구문을 사용해야 한다.
    
    ```sql
    DROP PROCEDURE 프로시저이름;
    ```