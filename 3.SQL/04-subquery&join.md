# 서브쿼리 (Subquery)

## 개념

**서브쿼리**: 쿼리 안에 또 다른 쿼리를 넣어 작성.  

주로 `WHERE`, `SELECT`, `FROM` 절에서 사용하며 외부 쿼리에서 필요한 데이터를 미리 계산해 사용.

## 서브쿼리 결과의 형태

1. **단일 값 (Scalar)**  

   하나의 값 반환 (예: 평균, 최대값)

   ```sql
   SELECT AVG(total_amount) FROM sales;
   -- 결과: 612,862
   ```

1. **한 줄 데이터 (Vector)**  

   하나의 열(column)에 여러 값 반환

   ```sql
   SELECT customer_id FROM customers WHERE customer_type = 'VIP';
   -- 결과: C001, C005, C010
   ```

1. **표 형태 데이터 (Matrix)**  

   여러 행(row), 여러 열(column) 반환

   ```sql
   SELECT * FROM customers WHERE region = '서울';
   -- 결과: '서울'지역의 구매자에 관한 모든 구매자 데이터 테이블
   ```

## 왜 서브쿼리를 써야 하는가

```sql
-- ❌ 잘못된 예시 (집계함수 WHERE에서 바로 사용 불가)
SELECT *
FROM sales
WHERE total_amount > AVG(total_amount);
-- 오류: 집계함수는 테이블 전체 스캔 후 결과가 나오는데,
-- WHERE는 각 행에 대해 조건 검사 → 작동하지 않음
```

```sql
-- ✅ 올바른 예시 (집계값을 서브쿼리로 처리)
SELECT *
FROM sales
WHERE total_amount > (SELECT AVG(total_amount) FROM sales);
```

#### 1.4.2 최대값, 최근값 찾기

```sql
-- 최대 주문 찾기
SELECT *
FROM sales
WHERE total_amount = (SELECT MAX(total_amount) FROM sales);
```

```sql
-- 최근 주문 찾기
SELECT *
FROM sales
WHERE order_date = (SELECT MAX(order_date) FROM sales);
```

```sql
-- 서브쿼리 없이도 가능하지만...
SELECT *
FROM sales
ORDER BY total_amount DESC
LIMIT 1;
-- ⚠️ 차이점: 데이터가 많을 때 정렬 방식은 성능 저하 가능
```


#### 1.4.3 벡터 서브쿼리 (IN 사용)

```sql
-- VIP 고객들의 모든 주문
SELECT *
FROM sales
WHERE customer_id IN (
    SELECT customer_id
    FROM customers
    WHERE customer_type = 'VIP'
)
ORDER BY total_amount DESC;
```



### 서브쿼리를 써야 하는 경우

-  **집계값과 비교** (평균, 최대, 최소 등)

   - 예) 평균보다 높은 주문 찾기

- **특정 조건의 데이터 조회**

   - 예) VIP 고객들의 주문 내역


### 쓰면 안되는 경우

- 조건이 **단순하거나 고정된 값**일 때

- 여러 테이블 데이터 결합 시 **JOIN이 더 직관적**일 때


# JOIN

### 2.1 JOIN 개념

- 여러 테이블 데이터를 **연결**하여 하나의 결과로 합침.  
- `ON` 절에 테이블 연결 조건 지정.

### 2.2 서브쿼리 방식과 JOIN 방식 비교

#### 2.2.1 서브쿼리 방식 (복잡하고 비효율적)

```sql
-- ❌ 서브쿼리 방식: 코드 길고 비효율적
SELECT
    s.product_name,
    s.total_amount,
    (SELECT customer_name 
     FROM customers
     WHERE customers.customer_id = s.customer_id) AS customer_name
FROM sales s;
```

#### 2.2.2 JOIN 방식 (간단하고 효율적)

```sql
-- ✅ JOIN 방식: 간단하고 효율적
SELECT
    c.customer_name,
    c.customer_type,
    s.product_name,
    s.total_amount
FROM customers c
INNER JOIN sales s ON c.customer_id = s.customer_id;
```

### 2.3 LEFT JOIN으로 잠재고객 포함 분석

```sql
SELECT
    c.customer_name,
    c.customer_type,
    COUNT(s.id) AS 주문횟수,
    COALESCE(SUM(s.total_amount), 0) AS 총구매액,
    CASE 
        WHEN COUNT(s.id) = 0 THEN '잠재고객'
        WHEN COUNT(s.id) >= 5 THEN '충성고객'
        ELSE '일반고객'
    END AS 고객분류
FROM customers c
LEFT JOIN sales s ON c.customer_id = s.customer_id
GROUP BY c.customer_id, c.customer_name, c.customer_type;
```

### 2.4 JOIN의 종류와 집합 개념

| JOIN 종류            | 설명                                                   | SQL 예제                                                        | 집합 개념          |
|-----------------------|---------------------------------------------------------|------------------------------------------------------------------|---------------------|
| **INNER JOIN**        | 양쪽 테이블 모두 존재하는 데이터만 반환               | `INNER JOIN`                                                    | A ∩ B (교집합)     |
| **LEFT JOIN**         | 왼쪽 테이블(A)의 모든 데이터 + 오른쪽 매칭 데이터     | `LEFT JOIN`                                                     | A                  |
| **LEFT JOIN + NULL**  | 왼쪽 데이터 중 오른쪽에 매칭되지 않는 데이터만 반환   | `LEFT JOIN ... WHERE b.key IS NULL`                             | A ∩ Bᶜ (차집합)    |
| **RIGHT JOIN**        | 오른쪽 테이블(B)의 모든 데이터 + 왼쪽 매칭 데이터     | `RIGHT JOIN`                                                    | B                  |
| **FULL OUTER JOIN**   | 양쪽 테이블의 모든 데이터 반환                        | `FULL OUTER JOIN`                                               | A ∪ B (합집합)     |

### 2.5 오류와 올바른 JOIN 예제

```sql
-- ❌ 오류: LEFT JOIN 후 COUNT(*), 주문 없는 고객도 1로 카운트됨
SELECT c.customer_name, COUNT(*)
FROM customers c LEFT JOIN sales s ON c.customer_id = s.customer_id
GROUP BY c.customer_name;
```

```sql
-- ✅ 올바른 방법: COUNT(s.id), 주문 없는 고객은 0으로 카운트됨
SELECT c.customer_name, COUNT(s.id) AS 주문횟수
FROM customers c LEFT JOIN sales s ON c.customer_id = s.customer_id
GROUP BY c.customer_name;
```

```sql
-- ✅ NULL 처리: COALESCE 사용
SELECT
    c.customer_name,
    COALESCE(SUM(s.total_amount), 0) AS 총구매액,
    COALESCE(MAX(s.order_date), '주문없음') AS 최근주문일
FROM customers c
LEFT JOIN sales s ON c.customer_id = s.customer_id
GROUP BY c.customer_name;
```

## 3. GROUP BY + JOIN

### 3.1 고객 유형별 평균 구매금액

```sql
SELECT
    c.customer_type AS 고객유형,
    COUNT(*) AS 주문건수,
    AVG(s.total_amount) AS 평균구매금액,
    SUM(s.total_amount) AS 총매출액
FROM customers c
INNER JOIN sales s ON c.customer_id = s.customer_id
GROUP BY c.customer_type
ORDER BY 평균구매금액 DESC;
```

## 4. 자주하는 실수와 해결법

| 실수                                        | 올바른 방법                                                  |
|---------------------------------------------|---------------------------------------------------------------|
| **GROUP BY 누락**                          | SELECT의 일반 컬럼은 모두 GROUP BY에 포함                     |
| **LEFT JOIN 후 COUNT(*) 사용**              | COUNT(오른쪽테이블.id) 사용, NULL 값 제외                     |
| **NULL 값 처리 누락**                       | COALESCE로 NULL → 기본값 대체                                 |
