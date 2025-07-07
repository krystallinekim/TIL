# Functions

##  문자열 함수

- **CHAR_LENGTH(str)** 

  문자열의 길이를 반환 

  ```sql
  SELECT CHAR_LENGTH('hello sql');  -- 결과: 9

  SELECT name, CHAR_LENGTH(name) AS 길이 FROM dt_demo; -- 이름과 각 이름의 길이
  ```

- **CONCAT(str1, str2, ...)**  

  여러 문자열을 이어붙임 

  ```sql
  SELECT CONCAT('hello ', 'sql', ' !!');  -- 결과: 'hello sql !!'

  SELECT CONCAT(name, '(', score, '점)') AS info FROM dt_demo; -- 이름과 점수 합쳐서 출력, 형태는 김김김(90점) 처럼
  ```

- **UPPER(str)**  

  문자열을 모두 대문자로 변환  

  ```sql
  SELECT UPPER(nickname) AS 대문자닉네임 FROM dt_demo;
  ```

- **LOWER(str)**  

  문자열을 모두 소문자로 변환

  ```sql
  SELECT LOWER(nickname) AS 소문자닉네임 FROM dt_demo;
  ```

- **SUBSTRING(str, pos, len) / LEFT,RIGHT(str,len)**  

  부분 문자열 추출  

  ```sql
  SELECT SUBSTRING('hello sql!', 2, 4);  -- 결과: 'ello'

  SELECT LEFT(description, 3) FROM dt_demo;  -- 설명의 앞 3글자만 추출

  SELECT RIGHT(description, 3) FROM dt_demo; -- 설명의 뒤 3글자만 추출
  ```

- **REPLACE(str, old, new)**  

  문자열 내 특정 부분을 다른 문자열로 치환  

  ```sql
  SELECT REPLACE('A@gmail.com', 'A', 'B');  -- 결과: 'B@gmail.com'
  
  SELECT REPLACE(description, '학생', '**') AS secret FROM dt_demo;  -- '학생'을 '**'로 대체
  ```

- **TRIM(str)**  

  문자열 앞뒤의 공백을 제거  

  ```sql
  SELECT TRIM('   what??   ') AS trimmed;  -- 결과: 'what??'
  ```

- **LOCATE(substr, str)**  

  특정 문자열이 전체 문자열 내에 위치한 시작 인덱스(1부터)를 반환  

  ```sql
  SELECT LOCATE('@', 'username@gmail.com');  -- 결과: 9 ('@' 위치)
  
  SELECT SUBSTRING(description, 1, LOCATE('학생', description) - 1) AS 학생설명 FROM dt_demo; -- 첫번째 글자부터 '학생' 앞까지 설명 추출
  ```

  
## 날짜/시간 함수

| 함수명 | 용도 | 예시 | 결과 |
|--------|------|------|------------|
| `NOW()` | 현재 날짜 + 시간 | `SELECT NOW();` | 2025-07-02 14:30:21 |
| `CURDATE()` | 현재 날짜만 | `SELECT CURDATE();` | 2025-07-02 |
| `CURTIME()` | 현재 시간만 | `SELECT CURTIME();` | 14:30:21 |
| `DATE_FORMAT(date, format)` | 날짜 포맷 변경 | `DATE_FORMAT(birth, '%Y년 %m월')` | 2010년 05월 |
| `DATEDIFF(a, b)` | 두 날짜 간 일수 차이 | `DATEDIFF(CURDATE(), birth)` | 5521 |
| `TIMESTAMPDIFF(unit, a, b)` | 단위별 날짜 차이 | `TIMESTAMPDIFF(YEAR, birth, CURDATE())` | 15 |
| `DATE_ADD(date, INTERVAL n unit)` | 날짜 더하기 | `DATE_ADD(birth, INTERVAL 100 DAY)` | 2010-08-29 |
| `DATE_SUB(date, INTERVAL n unit)` | 날짜 빼기 | `DATE_SUB(CURDATE(), INTERVAL 1 MONTH)` | 2025-06-02 |
| `YEAR(date)` | 연도 추출 | `YEAR(birth)` | 2010 |
| `MONTH(date)` | 월 추출 | `MONTH(birth)` | 5 |
| `DAY(date)` | 일 추출 | `DAY(birth)` | 21 |
| `DAYNAME(date)` | 요일 이름 | `DAYNAME(birth)` | Friday |
| `QUARTER(date)` | 분기 추출 | `QUARTER(birth)` | 2 |


 **주요 포맷 문자**

- `%Y`: 4자리 연도 / `%y`: 2자리 연도  

- `%m`: 월 (숫자) / `%M`: 월 (영문)  

- `%d`: 일  

- `%W`: 요일 이름 / `%w`: 요일 번호  

- `%H`: 시(24시) / `%h`: 시(12시) / `%i`: 분 / `%p`: AM/PM

---

## 숫자 함수

| 함수명 | 용도 | 예시 | 결과 |
|--------|------|------|------------|
| `ROUND(n)` | 반올림 | `ROUND(87.5)` | 88 |
| `ROUND(n, 1)` | 소수 첫째자리 반올림 | `ROUND(87.56, 1)` | 87.6 |
| `CEIL(n)` | 올림 | `CEIL(87.1)` | 88 |
| `FLOOR(n)` | 내림 | `FLOOR(87.9)` | 87 |
| `TRUNCATE(n, 1)` | 소수점 버림 | `TRUNCATE(87.56, 1)` | 87.5 |
| `ABS(n)` | 절댓값 | `ABS(-10)` | 10 |
| `MOD(a, b)` 또는 `a % b` | 나머지 | `MOD(10, 3)` / `10 % 3` | 1 |
| `DIV` | 몫 | `10 DIV 3` | 3 |
| `POWER(a, b)` | 제곱 | `POWER(2, 3)` | 8 |
| `SQRT(n)` | 제곱근 | `SQRT(16)` | 4 |

---

## 조건 함수

조건에 따라 다른 값을 반환

### **IF()**  

단순 조건 분기: `IF(조건, 참값, 거짓값)`

```sql
SELECT
  name,
  score,
  IF(score >= 80, '우수', '보통') AS 평가
FROM dt_demo;
-- 점수가 80점 이상이면 우수, 나머지는 보통
```

### **CASE WHEN**  

다양한 조건 분기 처리 가능

```sql
SELECT
  score,
  CASE
    WHEN score >= 90 THEN 'A'
    WHEN score >= 80 THEN 'B'
    WHEN score >= 70 THEN 'C'
    ELSE 'D'
  END AS 등급
FROM dt_demo;
-- 90점 이상은 A, 나머지 중 80점 이상은 B, 나머지 중 70점 이상은 C, 나머지는 D
```

항상 가장 좁은 조건이 위에 가게 해야함. 위에서부터 조건에 맞으면 바로 값을 할당하기 때문.

### **NULL 처리**

- `IFNULL(score, 0)`: score가 NULL일 경우 0을 반환

- `COALESCE(a, b, ...)`: 가장 먼저 NULL이 아닌 값을 반환

  COALESCE는 a에 NULL이 될수도 있는 값을 넣고, 만약 NULL이면 b에 있는 값을, b도 NULL이면 c에 있는 값을 반환하는 식으로 쓰임

  IFNULL과 달리 조건을 여러 개 걸 수 있다.

## 집계 함수 (Aggregate)

다수의 행에 대해 요약된 결과를 계산

### 숫자 관련 집계함수

| 함수 | 설명 |
|------|------|
| `COUNT()` | 행 개수 |
| `SUM()` | 총합 |
| `AVG()` | 평균 |
| `MIN()` / `MAX()` | 최소 / 최대값 |

```sql
SELECT
  COUNT(*) AS 총주문건수,
  SUM(total_amount) AS 총매출,
  AVG(total_amount) AS 평균주문액,
  MIN(order_date) AS 첫주문,
  MAX(order_date) AS 마지막주문
FROM sales;
```
결과는 보통 보던 테이블이 아닌, 결과값만 보여주는 다른 테이블로 나옴

위 예시의 경우, 총주문건수, 총매출, ..., 마지막 주문 날짜를 컬럼명으로 가지고, 컬럼 아래에 값만 나오는 식

### Str 관련 집계함수
```sql
SELECT
  GROUP_CONCAT(product_name) AS 주문제품들
FROM sales;
```

데이터를 전부 `,`로 구분해서 한 셀에 적어줌