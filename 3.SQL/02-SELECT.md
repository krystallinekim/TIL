# SELECT

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
    SELECT * FROM students WHERE age >= 20;

    SELECT * FROM students WHERE age < 40;

    SELECT * FROM students WHERE age BETWEEN 20 AND 40;
    ```

    ```sql
    -- BETWEEN
    column BETWEEN num1 AND num2
    column >= num1 AND column <= num2
    ```
    `BETWEEN`은 num1 이상 num1 이하 값을 반환함 
    
    그래서 위 두 구문은 같은 구문임

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

- `IS NULL` : 값이 NULL인 경우 반환

    ```sql
    SELECT * FROM userinfo WHERE email IS NULL;

    SELECT * FROM userinfo WHERE email IS NOT NULL;
    ```
    NOT을 이용하면 NULL이 아닌 경우도 반환 가능함

---

### 논리 연산자

- `AND` : 모든 조건이 참인 행 조회 (교집합)  

- `OR` : 하나라도 참인 행 조회 (합집합)  

- `NOT` : 조건이 반대 (여집합)

- 괄호 `()`를 이용해 우선순위 조절 가능  

    ```sql
    SELECT * FROM userinfo WHERE 
        (name LIKE 'e%' OR name LIKE 'g%') AND 
        email LIKE '%@gmail.com';
    -- 결과: 이름에 e 또는 g가 들어가면서 gmail을 쓰는 사람들만 보여줌
    ```

## `ORDER BY` 정렬

- 결과를 특정 컬럼 기준으로 정렬함

- ASC(오름차순, 기본 -> 안써도 됨), DESC(내림차순)  

- 기준을 여러 개 줄 수도 있다. 앞에있는게 먼저 정렬됨

- LIMIT로 조회 개수 제한, OFFSET으로 시작 위치 지정 가능

    ```sql
    -- 나이로 정렬
    SELECT * FROM userinfo WHERE email LIKE '%@gmail.com' ORDER BY age;

    -- 이름으로 정렬, 3개만 출력
    SELECT * FROM userinfo ORDER BY name LIMIT 3;

    -- 나이를 내림차순(가장 큰 값부터)으로 정렬 후 전화번호 순서대로(오름차순) 정렬, 3개만 출력
    SELECT name, phone, age FROM userinfo ORDER BY age DESC, phone LIMIT 3;

    -- 나이로 정렬, 가장 첫(오름차순이니 가장 작은) 줄을 제외하고 3개 출력
    SELECT * FROM userinfo ORDER BY age LIMIT 3 OFFSET 1;
    ```


## GROUP BY

### 기본 구조

특정 컬럼으로 묶어서 요약 통계를 계산

스프레드시트에서 피벗테이블과 비슷

```sql
SELECT
  category,
  COUNT(*) AS 카테고리별_건수,
  SUM(total_amount) AS 카테고리별_매출
FROM sales
GROUP BY category
ORDER BY 카테고리별_매출 DESC;
```
- GROUP BY는 지정된 컬럼 기준으로 집계를 나눔

- ORDER BY로 결과 정렬도 가능

### 다중 그룹핑

그룹핑은 2개 기준 이상으로도 가능함

```sql
SELECT
  region,
  category,
  SUM(total_amount) AS 지역별_카테고리별_매출
FROM sales
GROUP BY region, category;
```


### HAVING

GROUP BY 결과에 조건 필터를 적용할 때 사용

```sql
SELECT
  category,
  SUM(total_amount) AS 카테고리별_총매출
FROM sales
GROUP BY category
HAVING 카테고리별_총매출 >= 10000000;
-- 그룹으로 묶었을 때, 카테고리별 총매출이 천만원 이상인 값만 출력함
```


**WHERE vs HAVING**

| 항목 | WHERE | HAVING |
|------|-------|--------|
| 조건 대상 | 개별 행 | 그룹핑 결과 |
| 실행 시점 | GROUP BY 전에 작동 | GROUP BY 이후 작동 |

HAVING은 피벗 테이블의 결과에 필터를 걸고, WHERE은 전체 데이터에 필터를 걸어놓고 피벗 테이블을 만드는 과정이라고 보면 편함

**예제**

우수 영업사원(월평균 매출 50만원 이상) 찾기 
```sql
SELECT
  sales_rep,
  SUM(total_amount) AS 사원별_총매출,
  COUNT(DISTINCT MONTH(order_date)) AS 사원별_활동개월수,
  ROUND(SUM(total_amount) / COUNT(DISTINCT MONTH(order_date))) AS 사원별_월평균매출
FROM sales
GROUP BY sales_rep
HAVING 사원별_월평균매출 >= 500000
ORDER BY 사원별_월평균매출 DESC;
```

## UNION

- 위 코드와 아래 코드를 각각 실행 후, 한 테이블에 보여줌

- 위 아래 컬럼 숫자도 맞춰 줘야 한다

- 위아래 AS가 다르다면 순서 기준으로 들어감

    ```sql
    SELECT COUNT(*) AS 개수 FROM sales
    UNION
    SELECT COUNT(*) AS 개수 FROM customers;
    -- 결과: 개수 컬럼에 sales 줄수, customers 줄수가 이어서 나옴
    ```


## View

### Inline View (인라인 뷰)

FROM 절에 사용되는 서브쿼리 = 임시 테이블

```sql
-- 카테고리별 평균 매출을 구한 후, 50만원 이상만 필터링
SELECT *
FROM (
    SELECT
        category,
        AVG(total_amount) AS 평균매출,
        COUNT(*) AS 주문건수
    FROM sales s
    JOIN products p ON s.product_id = p.product_id
    GROUP BY category
) AS category_stats  -- 인라인 뷰
WHERE 평균매출 >= 500000;
```
매트릭스 서브쿼리와 같은 개념임


### View (뷰)

복잡한 쿼리를 재사용 가능한 가상 테이블로 저장

테이블 내용을 저장하는 것이 아닌, 쿼리를 저장해서 매크로를 돌리는 개념

원본 데이터가 수정되면 뷰도 같이 수정된다.

```sql
-- View 생성 : SELECT 앞에 구문 추가가 끝
CREATE VIEW customer_summary AS
SELECT
    c.customer_id,
    c.customer_name,
    c.customer_type,
    COUNT(s.id) AS 주문횟수,
    COALESCE(SUM(s.total_amount), 0) AS 총구매액,
    COALESCE(AVG(s.total_amount), 0) AS 평균주문액
FROM customers c
LEFT JOIN sales s ON c.customer_id = s.customer_id
GROUP BY c.customer_id, c.customer_name, c.customer_type;

-- View 사용 : 다른 테이블 자리에 똑같이 사용할 수 있다.
SELECT * FROM customer_summary WHERE 주문횟수 >= 5;
SELECT * FROM customer_summary WHERE customer_type = 'VIP';

-- View 삭제
DROP VIEW customer_summary;
```

### 비교
| 구분 | Inline View | View |
| --- | --- | --- |
| 저장 여부 | 일회용 | 데이터베이스에 저장
| 재사용 | 불가능 | 가능 |
| 성능| 매번 실행 | 한 번 정의 후 재사용 |
| 용도 | 복잡한 일회성 분석 | 자주 사용하는 쿼리