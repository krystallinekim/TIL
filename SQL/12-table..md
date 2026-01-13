# 테이블(Table)

## 1. 테이블(Table)

- 테이블은 데이터베이스에서 데이터를 저장할 수 있는 핵심이 되는 개체로 행과 열로 구성되어 있다.

## 2. 테이블 생성

- 테이블을 생성할 때는 `CREATE TABLE` 구문을 사용한다.
- NULL 값을 허용하려면 NULL을(생략 가능), 허용하지 않으려면 NOT NULL을 사용하면 된다.
- DEFAULT는 값을 입력하지 않았을 때 자동으로 입력되는 기본값을 정의하는 방법이다.
- 같은 이름의 열은 존재할 수 없다.

    ```sql
    CREATE TABLE member(
      mem_no INT NOT NULL,
      ...
      enroll_date DATE DEFAULT CURDATE()
    );
    ```

- 서브쿼리를 이용해서 기존 테이블의 데이터를 가져오는 것도 가능

## 3. 제약 조건

- 제약 조건이란 데이터의 무결성을 지키기 위한 제한된 조건을 의미한다.
- 즉, 특정 데이터를 입력할 때 무조건적으로 입력되는 것이 아닌, 어떠한 조건을 만족했을 때 입력되도록 제약할 수 있다. -> 부적절한 데이터의 입력을 막음
- 제약 조건은 테이블 생성 시 제약조건을 설정할 수 있고 ALTER 구문을 통해서 제약조건을 설정하고 삭제할 수 있다.

### 3.1. PRIMARY KEY 제약 조건

- 테이블에 존재하는 많은 행의 데이터를 구분할 수 있는 주식별자를 기본 키(Primary Key)라고 부른다.
- `PRIMARY KEY` 제약 조건은 기본 키(Primary Key)로 사용할 열에 부여하는 제약 조건이다.
- 기본 키(Primary Key)에 입력되는 값은 중복될 수 없으며(UNIQUE), NULL 값이 입력될 수 없다(NOT NULL).


    ```sql
    CREATE TABLE member (
      mem_no INT PRIMARY KEY,
      ...
    );
    ```
    
- `AUTO_INCREMENT`를 사용하면 PK를 자동으로 부여 가능

- PK는 테이블당 하나지만, 꼭 PK가 열 하나로 이루어지라는 규칙은 없음. 여러 열을 합쳐서 PK로 지정하는 것도 가능하다.
  - 단, 이때는 `CONSTRAINTS`를 써서 한번에 2개 열을 PK로 지정해야 함
  
```sql
CREATE TABLE member (
    mem_no INT AUTO_INCREMENT,
    ...,
    /*CONSTRAINT*/ PRIMARY KEY(mem_no, mem_id)
);
```



### 3.2. UNIQUE 제약 조건

- DB 모델링에서 나오던 보조 식별자(주식별자 대체 가능)
- `UNIQUE` 제약 조건은 열에 중복되지 않는 유일한 값을 입력해야 하는 조건이다.

    ```sql
    CREATE TABLE member (
      mem_no INT PRIMARY KEY,
      mem_id VARCHAR(20) UNIQUE,
      ...
    );
    ```

- UNIQUE도 `CONSTRAINTS`를 써서 따로 지정하는것이 가능.
  - 따로 인덱스 이름을 지정하는 것도 가능하고, 여러 열을 UNIQUE로 묶는 것도 가능하다.



### 3.3. FOREIGN KEY 제약 조건

- 두 테이블 사이의 관계를 설정하는 키를 외래 키(Foreign Key)라고 부른다.
- `FOREIGN KEY` 제약조건은 외래 키(Foreign Key)로 사용할 열에 부여하는 제약 조건이다.
- 외래 키(Foreign Key)로 두 테이블의 관계를 설정하면 하나의 테이블이 다른 테이블에 의존하게 된다.
- 외래 키(Foreign Key)가 있는 테이블에 데이터를 입력할 때는 **부모 테이블에 이미 데이터가 존재해야 한다**.
  - 참조하는 열과 데이터 타입이 같아야 함
  - 참조하는 열은 주로 PK나 UNIQUE 걸린 열을 사용함

    ```sql
    -- 기준 테이블/상위 테이블/부모 테이블
    CREATE TABLE member_grade (
      grade_code VARCHAR(10) PRIMARY KEY,  
      grade_name VARCHAR(10) NOT NULL
    );
    
    -- 외래키 테이블/하위 테이블/자식 테이블
    CREATE TABLE member (
      mem_no INT PRIMARY KEY,
      mem_id VARCHAR(20) UNIQUE,
      grade_code VARCHAR(10) REFERENCES member_grade(grade_code),  -- 괄호 내부 생략시 기본키 참조
      ...
    );
    ```

#### 수정/삭제 제한

- 외래키로 연결되어 있으면(참조 중이면) 부모 테이블의 수정(`UPDATE`)/삭제(`DELETE`)는 불가능해진다(무결성 문제)
  - 참조 중이 아니면 수정/삭제 가능하긴 함

- 기본옵션은 `RESTRICT`, 오류가 나면서 수정/삭제 불가
- `SET NULL`이면 부모 테이블이 변경되었을 때 자동으로 NULL값으로 바뀜
- `CASCADE`는 자식 테이블의 값이 같이 수정/삭제됨
  - 수정될 때는 부모에 따라서 같이 수정되고, 삭제는 아예 데이터를 날림
- `NO ACTION`은 아무것도 안하는 것

- 그냥 기본으로 두고 쓰자

### 3.4. CHECK 제약 조건

- CHECK 제약 조건은 열에 입력되는 데이터를 점검하는 기능을 한다.
- CHECK 제약 조건을 설정한 후에는 제약 조건에 위배되는 값은 열에 입력되지 않는다.
- 역시 테이블 단위로 마지막에 CHECK 제약조건을 걸 수 있다.(CONSTRAINT로 이름 지정도 가능하다)

    ```sql
    CREATE TABLE member (
      mem_no INT PRIMARY KEY,
      mem_id VARCHAR(20) UNIQUE,
      gender CHAR(2) CHECK(gender IN ('남자', '여자')),
      grade_code VARCHAR(10) REFERENCES member_grade(grade_code),
      ...
    );
    ```

## 4. 테이블 수정

- 테이블을 수정할 때는 `ALTER TABLE` 구문을 사용한다.
- 테이블에서 정의된 열과 제약 조건의 추가, 수정, 삭제, 이름 변경 등을 할 수 있다.

### 4.1. 열의 추가 / 수정 / 삭제

- `ALTER`와 `ADD` 구문을 이용해서 열을 추가할 수 있다.
  - 열을 추가하면 기본적으로 가장 뒤에 추가가 되는데 열을 추가하면서 순서를 지정하려면 제일 뒤에 `FIRST` 또는 `AFTER 열 이름`을 지정하면 된다.
    
    ```sql
    ALTER TABLE member 
    ADD age TINYINT DEFAULT 0 
    AFTER gender;
    ```
    
- `ALTER`와 `MODIFY` 구문을 이용해서 열의 데이터 타입, DEFAULT 값을 변경할 수 있다.
  - 이미 데이터가 존재하는 열의 데이터 타입을 수정할 경우, 충돌이 일어나 변경이 안될 수도 있다.

    ```sql
    ALTER TABLE member 
    MODIFY gender CHAR(2) 
      DEFAULT '남자' 
      NOT NULL;
    ```
    
- `ALTER`와 `RENAME COLUMN` 구문을 이용해서 열의 이름을 변경할 수 있다.
    
    ```sql
    ALTER TABLE member 
    RENAME COLUMN mem_no TO memNo;
    ```
    
- `MODIFY`와 `RENAME COLUMN`의 역할을 합친 `CHANGE COLUMN`구문도 있다.

    ```sql
    ALTER TABLE usertbl
    CHANGE COLUMN u_name name VARCHAR(20) NOT NULL;
    ```


- `ALTER`와 `DROP COLUMN` 구문을 이용해서 열을 삭제할 수 있다.
  - 열 안의 데이터도 전부 삭제됨
  - 참조되는 열이 있다면 삭제 불가
  - 테이블의 모든 열을 삭제하는것도 불가


    ```sql
    ALTER TABLE member 
    DROP COLUMN mem_no;
    ```
    

### 4.2. 열의 제약 조건 추가 / 삭제

- `ALTER`와 `ADD CONSTRAINT` 구문을 이용해서 제약조건을 추가할 수 있다.
    
    ```sql
    ALTER TABLE member 
    ADD CONSTRAINT PRIMARY KEY(mem_no);
    
    ALTER TABLE location
    ADD CONSTRAINT FOREIGN KEY(national_code) REFERENCES national(national_code);
    ```
    
- `ALTER`와 `DROP CONSTRAINT` 구문을 이용해서 제약조건을 삭제할 수 있다.
  - PK는 그냥 `PRIMARY KEY`라고 지칭할 수 있지만, 외래키나 제약조건들은 각각 이름을 선언해서 삭제해 주면 된다.  

    ```sql
    ALTER TABLE member 
    DROP CONSTRAINT PRIMARY KEY;
    ```
    
- 제약 조건의 수정은 불가능하기 때문에 삭제 후 다시 제약 조건을 추가해야 한다.

## 5. 테이블 삭제

- 테이블을 삭제할 때는 `DROP TABLE` 구문을 사용한다.
  - 테이블도 참조 중이라면 삭제가 불가능함 -> 제약조건을 삭제하던가, 자식테이블부터 삭제하면 된다.
    
    ```sql
    DROP TABLE member, member_grade;
    ```