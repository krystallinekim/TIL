# Functions

##  문자열 함수

### **CHAR_LENGTH(str)** 

문자열의 길이를 반환 

```sql
SELECT CHAR_LENGTH('hello sql');  -- 결과: 9

SELECT name, CHAR_LENGTH(name) AS 길이 FROM dt_demo; -- 이름과 각 이름의 길이
```

### **CONCAT(str1, str2, ...)**  

여러 문자열을 이어붙임 

```sql
SELECT CONCAT('hello ', 'sql', ' !!');  -- 결과: 'hello sql !!'

SELECT CONCAT(name, '(', score, '점)') AS info FROM dt_demo; -- 이름과 점수 합쳐서 출력, 형태는 김김김(90점) 처럼
```

### **UPPER(str)**  

문자열을 모두 대문자로 변환  

```sql
SELECT UPPER(nickname) AS 대문자닉네임 FROM dt_demo;
```

### **LOWER(str)**  

문자열을 모두 소문자로 변환

```sql
SELECT LOWER(nickname) AS 소문자닉네임 FROM dt_demo;
```

### **SUBSTRING(str, pos, len) / LEFT,RIGHT(str,len)**  

부분 문자열 추출  

```sql
SELECT SUBSTRING('hello sql!', 2, 4);  -- 결과: 'ello'

SELECT LEFT(description, 3) FROM dt_demo;  -- 설명의 앞 3글자만 추출

SELECT RIGHT(description, 3) FROM dt_demo; -- 설명의 뒤 3글자만 추출
```

### **REPLACE(str, old, new)**  

문자열 내 특정 부분을 다른 문자열로 치환  

```sql
SELECT REPLACE('A@gmail.com', 'A', 'B');  -- 결과: 'B@gmail.com'

SELECT REPLACE(description, '학생', '**') AS secret FROM dt_demo;  -- '학생'을 '**'로 대체
```

### **TRIM(str)**  

문자열 앞뒤의 공백을 제거  

```sql
SELECT TRIM('   what??   ') AS trimmed;  -- 결과: 'what??'
```

### **LOCATE(substr, str)**  

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

주로 집계함수는 `GROUP_BY`를 이용해 특정 그룹에 대한 통계치를 볼 때 많이 쓰인다.

### Str 관련 집계함수
```sql
SELECT
  GROUP_CONCAT(product_name) AS 주문제품들
FROM sales;
```

데이터를 전부 `,`로 구분해서 한 셀에 적어줌

## Window 함수

집에서 창문 너머로는 전체 주택가를 볼 수 있다.

이처럼, 레코드 안의 데이터만 보는 게 아니라 레코드 밖의 정보를 창문 너머로 보듯이 보는 것이 Window 함수의 기본

Window 함수의 공통점은 `OVER()`구문을 활용한다는 것이다.

### **`OVER()`**

```sql
-- 전체 평균값
SELECT AVG(amount) FROM orders;
-- 전체 데이터
SELECT customer_id, amount FROM orders;
```
전체 평균값은 스칼라, 전체 데이터는 테이블이기 때문에 이 둘을 한 테이블에서 볼 방법은 없었다.

어떻게든 보고 싶다면, CROSS JOIN을 이용해 강제로 모든 데이터 옆에 평균값을 붙여야 했다. 

Window함수가 있으면, 한번에 이걸 편하게 할 수 있음

```sql
SELECT
  customer_id,
  amount,
  AVG(amount) OVER()
FROM orders
```
이 쿼리를 실행하면 각 구매에 대해 | 고객ID | 구매액 | 전체 구매액 평균 | 을 보게 된다.

---

**`OVER()` 안에 들어갈 수 있는 것들**

- 안에 아무것도 없으면, 전체 데이터가 기준이 된다.

- `ORDER BY`
  
  윈도우 함수를 어떤 기준으로 적용할 것인지 알려주는 것

  ```sql
  -- 구매액에 대해 정렬해 순위를 구함
  RANK() OVER(ORDER BY amount)
  -- 구매일 기준으로 누적합을 구함
  SUM() OVER(ORDER BY order_date)
  ```

- `PARTITION BY`

  윈도우 함수를 그룹화하는데 사용

  `GROUP BY`를 쓰면 개별 레코드가 묻히고 전체 통계만 구할 수 있지만,

  `PARTITION BY`를 쓰면 개별 레코드를 살리면서 그룹화를 할 수 있음
  ```sql
  -- 지역별 매출 순위를 구함
  RANK() OVER(PARTITION BY region ORDER BY amount)
  ```
  **한번에** 그룹에 대한 정보와 전체에 대한 정보를 같이 볼 수 있다.

- `ROWS BETWEEN`

  결과를 구할 행을 지정한다.

  ```sql
  FIRST_VALUE(name) OVER(
    ORDER BY amount
    ROWS BETWEEN 3 PRECEDING AND 4 FOLLOWING
  )
  ```
  특정 행 기준으로 3행 전 ~ 4행 뒤까지의 데이터 중 가장 amount가 작은 값을 가진 이름을 가져온다.

  기준 1에는 주로 `<n> PRECEDING`, 기준 2에는 `<n> FOLLOWING`이 들어간다.

  n에 값을 주면 n행 전 데이터, UNBOUNDING을 주면 제한 없이 가장 끝 값을 기준으로 한다.

  `CURRENT_ROW`를 쓰면 현재 행이 기준이 된다.

### **`ROW_NUMBER() OVER()`**

순서대로 줄을 세우는 함수

원래, 그냥 모든 데이터를 줄세우려면 `ORDER BY`를 사용하면 됐다.

```sql
SELECT * FROM orders ORDER BY amount LIMIT 20 OFFSET 20;
```
그런데, 정확하게 n번째 데이터를 찾고 싶을 때는 `LIMIT 1 OFFSET n-1`같은 복잡미묘한 방법을 사용해야 했다.

여기서 윈도우 함수를 이용하면

```sql
SELECT
  *,
  ROW_NUMBER() OVER (ORDER BY amount DESC)
FROM orders;
```
와 같이, 데이터 옆에 이 데이터가 어떤 순서의 몇 번째인지를 붙여서 사용할 수 있다.

### **`RANK()/DENSE_RANK() OVER()`**

`ROW_NUMBER()`와 비슷하지만, 동점자 처리 방법이 다르다.

```sql
SELECT
  ROW_NUMBER() OVER (ORDER BY amount DESC),
  RANK() OVER (ORDER BY amount DESC),
  DENSE_RANK() OVER (ORDER BY amount DESC)
FROM orders;
```
이 쿼리를 돌리면,

|함수|결과|동점자 처리 방식|
|---|---|---|
|`ROW_NUMBER()`|1-2-3-4-5-6-7-8-9|동점자 무시하고 위에서부터 순서대로|
|`RANK()`|1-1-1-1-5-5-7-8-8|동점자 숫자를 고려해서 동점자 처리|
|`DENSE_RANK()`|1-1-1-1-2-2-3-4-4|동점자 숫자는 무시하고 넘버링|

각각 사용처가 다르다.

### **`SUM() OVER()`**

```sql
SUM(일매출) OVER(ORDER BY order_date) AS 누적매출
```
일반적으로 `SUM()`은 전체 총합, 혹은 집계함수에서 카테고리별 총합을 보는 함수이다.

윈도우 함수에서의 `SUM()`은 그 행까지의 **누적 합계**를 보는 함수이다.

`PARTITION BY`를 이용하면, 특정 카테고리별 누적 합계도 볼 수 있음.

### **`AVG() OVER()`**
```sql
AVG(일매출) OVER(ORDER BY order_date) AS 누적평균매출
```
`AVG()`도 똑같이 그 행까지의 **누적 평균**을 보여준다.

이 때, `OVER` 안에 `ROWS BETWEEN <n-1> PRECEDING AND CURRENT ROW`를 적용해 주면

n일동안의 **이동평균**을 볼 수 있게 된다.

모든 행에 대해 n-1일 전부터 현재 행까지 총 n일 간의 평균을 보게 된다는 뜻

### **`LAG()/LEAD() OVER()`**

`LAG()`와 `LEAD()`는 의미만 반대고, 사실상 같은 함수

```sql
LAG(월매출,1) OVER(ORDER BY 월) AS 전월매출
```
`LAG`는 특정 행 기준으로 n열만큼 이전 열 값을 가져오고, `LEAD` 는 반대로 n열 뒤 값을 가져온다.

가장 잘 쓰는 곳은 **증감율**을 볼 때

### **`NTILE()/PERCENT_RANK() OVER()`**

이 두 함수는 전체 데이터 중 이 값이 몇 번쨰에 있는지 보는 함수이다.(윈도우 함수로 분류되는 이유)

```sql
NTILE(4) OVER(ORDER BY 총구매금액) AS 총구매금액_4분위
```
`NTILE(n)`은 전체 데이터를 조건 기준으로 n등분해 지금 행의 데이터가 몇 번째에 있는지를 반환한다. 

순서는 먼저 나온 데이터가 1, 마지막에 나온 데이터가 n을 받는다.

예시의 경우, 총구매금액 기준으로 4등분해 1분위는 가장 구매금액이 낮은 경우, 4분위가 가장 구매금액이 높은 데이터 기준임

```sql
PERCENT_RANK() OVER(ORDER BY price DESC) AS 가격순위
```

`PERCENT_RANK()`는 `NTILE()`보다 자유도가 더 높고, 자세하게 볼 수 있다.

전체 데이터에 0부터 1 사이의 값을 할당하는데, 가장 먼저 나온 데이터에 0, 마지막 데이터에 1을 할당하고 그 사이는 0.2, 0.5, 0.99 등을 준다.

데이터의 **백분위 순위**를 알 수 있다.

원본 데이터가 수정되더라도 다시 백분위 순위를 수정해 줘 계속 최신화된 결과를 받아 볼 수 있음


### **`FIRST_VALUE()/LAST_VALUE()`**

처음/마지막 값을 가져온다.

```sql
FIRST_VALUE(product_name) OVER(
  PARTITION BY 카테고리
  ORDER BY price DESC
  ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING
)
```

주로 `PARTITION BY`와 같이 써서, 어느 구역에서의 최대/최소값, 가장 빠른/늦은 날짜 등을 구할 수 있다.

FIRST와 LAST는 중간에 ORDER BY의 순서를 반대로 주면 똑같은 함수임

ROWS BETWEEN으로 가져올 범위를 전체 데이터로 지정해 줘서 전체 데이터를 기준으로 하는 것이 좋다.

PostgreSQL에서는 뒤의 `UNBOUND FOLLOWING`을 `CURRENT ROW`로 가져왔음

###