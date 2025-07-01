# SELECT 고급

## 기본 구조

```sql
SELECT 컬럼명 FROM 테이블명 WHERE 조건 ORDER BY 정렬기준 LIMIT 개수 OFFSET 개수;
```

## `WHERE` 

특정 조건의 값들만 반환함



### 비교 연산자

- `=` : 컬럼 값이 지정한 값과 정확히 일치  

- `!=` 또는 `<>` : 컬럼 값이 지정한 값과 일치하지 않음  

```sql
SELECT * FROM students WHERE name = '이도윤';
SELECT * FROM students WHERE id != 1;
```

- `>=`, `<=`, `>`, `<` : 각각 이상, 이하, 초과, 미만 

- `BETWEEN A AND B` : A와 B 사이 값 (포함)  
    ```sql
    column BETWEEN num1 AND num2
    column >= num1 AND column <= num2
    ```
    위 두 구문은 같은 구문임

```sql
SELECT * FROM students WHERE age >= 20;
SELECT * FROM students WHERE age < 40;
SELECT * FROM students WHERE age BETWEEN 20 AND 40;
```


---

### `IN`

- `IN (값1, 값2, ...)` : 값 리스트 중 하나에 해당  

```sql
SELECT * FROM students WHERE id IN (1, 3, 4, 7);
SELECT * FROM userinfo WHERE name IN ('alice', 'bruce');
```


---

### `LIKE`

- `%` : 0개 이상의 임의 문자  
- `_` : 정확히 한 글자  

```sql
SELECT * FROM students WHERE name LIKE '김%';    -- '김'으로 시작  
SELECT * FROM students WHERE name LIKE '%윤%';   -- 이름에 '윤' 포함  
SELECT * FROM students WHERE name LIKE '윤__';    -- '윤'으로 시작하고 총 3글자  
```

---

### NULL 검사

- `IS NULL` : 값이 NULL인 경우  
- `IS NOT NULL` : 값이 NULL이 아닌 경우  

```sql
SELECT * FROM userinfo WHERE email IS NULL;
SELECT * FROM userinfo WHERE email IS NOT NULL;
```

---

### 논리 연산자

- `AND` : 모든 조건이 참인 행 조회 (교집합)  
- `OR` : 하나라도 참인 행 조회 (합집합)  
- `NOT` : 조건이 반대 (여집합)
- 괄호 `()`를 이용해 우선순위 조절 가능  

    ```sql
    SELECT * FROM userinfo WHERE
        name LIKE '%d%' AND 
        phone LIKE '010%' AND 
        email LIKE '%@gmail.com';

    SELECT * FROM userinfo WHERE 
        (name LIKE 'e%' OR name LIKE 'g%') AND 
        email LIKE '%@gmail.com';
    ```

    위 둘은 결과가 다르게 나올 수 있다.

## ORDER BY 정렬

- 결과를 특정 컬럼 기준으로 정렬  
- ASC(오름차순, 기본), DESC(내림차순)  
- 다중 컬럼 정렬 가능 (우선순위에 따라 순서 지정)  
- LIMIT로 조회 개수 제한, OFFSET으로 시작 위치 지정 가능 (페이징 처리)

```sql
SELECT * FROM userinfo ORDER BY name LIMIT 3;
SELECT * FROM userinfo WHERE email LIKE '%@gmail.com' ORDER BY age;
SELECT name, phone, age FROM userinfo ORDER BY age DESC, phone LIMIT 3;
SELECT * FROM userinfo ORDER BY age LIMIT 3 OFFSET 1;
```

## 데이터 타입

### 문자열 타입

| 타입       | 크기       | 특징                | 사용 예시           |
|------------|------------|---------------------|---------------------|
| CHAR(n)    | 고정 길이  | 항상 n바이트 사용   | 주민번호, 우편번호   |
| VARCHAR(n) | 가변 길이  | 실제 길이만큼 사용  | 이름, 이메일         |
| TEXT       | 약 65KB    | 긴 문자열 저장      | 게시글 내용         |
| LONGTEXT   | 약 4GB     | 매우 긴 문자열 저장 | 소설, 대용량 텍스트 |

### 숫자 타입

| 타입         | 크기    | 범위                | 사용 예시        |
|--------------|---------|---------------------|-----------------|
| TINYINT      | 1바이트 | -128  127          | 나이, 등급       |
| INT          | 4바이트 | 약 -21억  21억     | ID, 개수         |
| BIGINT       | 8바이트 | 매우 큰 정수        | 조회수, 금액     |
| FLOAT        | 4바이트 | 소수점 약 7자리     | 점수, 비율       |
| DOUBLE       | 8바이트 | 소수점 약 15자리    | 정밀한 계산      |
| DECIMAL(m,d) | 가변    | 정확한 소수          | 돈, 정밀 계산    |

### 날짜/시간 타입

| 타입       | 형식                 | 사용 예시          |
|------------|----------------------|--------------------|
| DATE       | YYYY-MM-DD           | 생년월일, 가입일   |
| TIME       | HH:MM:SS             | 시간만             |
| DATETIME   | YYYY-MM-DD HH:MM:SS  | 정확한 시점        |
| TIMESTAMP  | YYYY-MM-DD HH:MM:SS  | 자동 갱신 가능     |


##  문자열 함수

- **CHAR_LENGTH(str)**  
  문자열의 길이를 반환합니다.  
  ```sql
  SELECT CHAR_LENGTH('hello sql');  -- 결과: 9
  SELECT name, CHAR_LENGTH(name) AS 길이 FROM dt_demo; -- 각 이름의 길이 조회
  ```

- **CONCAT(str1, str2, ...)**  
  여러 문자열을 이어붙입니다.  
  ```sql
  SELECT CONCAT('hello ', 'sql', ' !!');  -- 결과: 'hello sql !!'
  SELECT CONCAT(name, ' (', score, '점)') AS info FROM dt_demo; -- 이름과 점수 합쳐서 출력
  ```

- **UPPER(str)**  
  문자열을 모두 대문자로 변환합니다.  
  ```sql
  SELECT UPPER(nickname) AS 대문자닉네임 FROM dt_demo;
  ```

- **LOWER(str)**  
  문자열을 모두 소문자로 변환합니다.  
  ```sql
  SELECT LOWER(nickname) AS 소문자닉네임 FROM dt_demo;
  ```

- **SUBSTRING(str, pos, len)**  
  문자열의 특정 위치부터 지정한 길이만큼 부분 문자열을 추출합니다.  
  ```sql
  SELECT SUBSTRING('hello sql!', 2, 4);  -- 결과: 'ello'
  SELECT LEFT(description, 3) FROM dt_demo;  -- 설명의 앞 3글자만 추출
  SELECT RIGHT(description, 3) FROM dt_demo; -- 설명의 뒤 3글자만 추출
  ```

- **REPLACE(str, old, new)**  
  문자열 내 특정 부분을 다른 문자열로 치환합니다.  
  ```sql
  SELECT REPLACE('A@gmail.com', 'A', 'B');  -- 결과: 'B@gmail.com'
  SELECT REPLACE(description, '학생', '**') AS secret FROM dt_demo;  -- '학생'을 '**'로 대체
  ```

- **TRIM(str)**  
  문자열 앞뒤의 공백을 제거합니다.  
  ```sql
  SELECT TRIM('   what??   ') AS trimmed;  -- 결과: 'what??'
  ```

- **LEFT(str, len)**  
  문자열의 왼쪽부터 지정한 길이만큼 추출합니다.  
  ```sql
  SELECT LEFT('hello', 3);  -- 결과: 'hel'
  ```

- **RIGHT(str, len)**  
  문자열의 오른쪽부터 지정한 길이만큼 추출합니다.  
  ```sql
  SELECT RIGHT('hello', 3);  -- 결과: 'llo'
  ```

- **LOCATE(substr, str)**  
  특정 문자열이 전체 문자열 내에 위치한 시작 인덱스(1부터)를 반환합니다.  
  ```sql
  SELECT LOCATE('@', 'username@gmail.com');  -- 결과: 9 ('@' 위치)
  
  SELECT SUBSTRING(description, 1, LOCATE('학생', description) - 1) AS 학생전설명 FROM dt_demo;
  -- '학생' 앞까지 설명 일부 추출
  ```
