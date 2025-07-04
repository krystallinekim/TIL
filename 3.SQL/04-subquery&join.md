# 서브쿼리 (Subquery)

## 개념

**서브쿼리**: 쿼리 안에 또 다른 쿼리를 넣어 작성.  

주로 `WHERE`, `SELECT`, `FROM` 절에서 사용하며 외부 쿼리에서 필요한 데이터를 미리 계산해 사용.

괄호 안에 다른 쿼리(SELECT ***)를 이용하면 그 안의 쿼리를 먼저 계산 후, 그 결과를 하나의 데이터로 사용해 외부 쿼리를 계산한다.

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

예시로, 평균보다 매출이 높은 주문들을 보고 싶을 때,

```sql
SELECT * FROM sales
WHERE total_amount > AVG(total_amount);
```
AVG로 바로 비교하려 하면 오류가 나온다.

집계함수는 테이블 전체 스캔 후 결과가 나오는데, WHERE는 각 행에 대해 조건 검사를 하려 해서 작동하지 않음

```sql
SELECT AVG(total_amounts) FROM sales;

SELECT * FROM sales
WHERE total_amount > 288375;
```

이걸 평균값을 직접 구한 뒤 그 값을 이용해 계산하는 건 오류도 안나오고, 답도 제대로 나온다

다만, 이러면 평균값이 바뀔 때(데이터가 변경될 때) 마다 계산을 다시 돌리고, 코드도 수정해 줘야 함

```sql
SELECT * FROM sales
WHERE total_amount > (SELECT AVG(total_amount) FROM sales);
```

위 2줄짜리 코드를 한 줄에 적어 놓은게 서브쿼리


## 서브쿼리로 할 수 있는 것들

1. 평균과 비교

    ```sql
    -- 평균보다 높은 주문들
    SELECT
        product_name,
        total_amount,
        total_amount - (SELECT AVG(total_amount) FROM sales) AS 평균차이
    FROM sales
    WHERE total_amount > (SELECT AVG(total_amount) FROM sales);
    -- 결과: 평균보다 높은 주문들의 이름, 판매액, 편차로 이루어진 표
    ```

1. 최대/최소값 찾기
    ```sql
    -- 가장 비싼 주문
    SELECT * FROM sales
    WHERE total_amount = (SELECT MAX(total_amount) FROM sales);
    -- 결과: total_amount의 최댓값을 가진 주문들의 전체 판매 데이터

    -- 가장 최근 주문들
    SELECT * FROM sales
    WHERE order_date = (SELECT MAX(order_date) FROM sales);
    -- 결과: order_date가 가장 최근인 주문들의 전체 판매 데이터
    ```
    위 경우는 결과 데이터가 여러 개 나올 수도 있다. 가장 최근에 거래된 주문이 여러 개일 수도 있기 때문

    같은 작업을 서브쿼리 없이도 할 수 있지만(order_date로 정렬 후 LIMIT 1로 숫자를 제한), 결과에 해당하는 주문이 여러 개인 경우를 검색하지 못한다.


1. 목록에 포함된 것들 (IN 사용)

    ```sql
    -- VIP 고객들의 모든 주문
    SELECT * FROM sales
    WHERE customer_id IN (
        SELECT customer_id FROM customers
        WHERE customer_type = 'VIP'
    )
    ORDER BY total_amount DESC;
    -- 결과: customer_type이 VIP인 고객들의 모든 판매 데이터
    ```

    ```sql
    -- 전자제품을 구매한 적 있는 고객들의 모든 주문
    SELECT * FROM sales
    WHERE customer_id IN (
        SELECT DISTINCT customer_id
        FROM sales
        WHERE category = '전자제품'
    );
    -- 결과: category가 전자제품인 제품들의 모든 판매 데이터
    ```
    위 경우는 서브쿼리를 돌리면 결과가 한 줄로 나온다(Vector). 벡터 데이터를 이용하려면 IN을 써야 한다.


## 서브쿼리를 써야 하는 경우

-  **집계값과 비교** (평균, 최대, 최소 등)

   - 예) 평균보다 높은 주문 찾기

- **특정 조건의 데이터 조회**

   - 예) VIP 고객들의 주문 내역


## 쓰면 안되는 경우

- 조건이 **단순하거나 고정된 값**일 때

- 여러 테이블 데이터 결합 시 **JOIN이 더 직관적**일 때

---

# JOIN

## JOIN 개념

여러 테이블 데이터를 **연결**하여 하나의 결과로 합침.  

`ON` 절 뒤에 테이블 연결 조건 지정.

### JOIN의 형식

```sql
SELECT
    t1.column1,
    t2.column2,
    ...
FROM table1 t1
JOIN table2 t2
ON JOIN_연결조건;
```
JOIN 연결조건에는 보통 두 테이블 간의 공통 키이면서 고유키를 사용함


## 왜 JOIN을 써야 하는가

테이블이 2개 있고, 테이블 c(customer)에 고객정보 / 테이블 s(sale)에 판매 데이터가 존재할 때, 한 테이블에 고객정보와 판매데이터를 같이 보고 싶을 때가 있을 수 있다.

```sql
SELECT
    customer_id,
    product_name,
    total_amount,
    (SELECT customer_name FROM customers WHERE customer_id = sales.customer_id) AS customer_name,
    (SELECT customer_type FROM customers WHERE customer_id = sales.customer_id) AS customer_type
FROM sales;
```
이걸 서브쿼리를 이용한다면, 매우 길고 현학적이 된다.

보면, 내가 보고싶은 데이터 하나마다 서브쿼리를 하나씩 짜서 넣는 걸 알 수 있음. 매우 비효율적임

```sql
SELECT
    c.customer_name,
    c.customer_type,
    s.product_name,
    s.total_amount
FROM customers c
INNER JOIN sales s ON c.customer_id = s.customer_id;
```
이걸 JOIN을 이용한다면 짧고 효율적으로 이 코드를 만들 수 있다.

JOIN을 통해 두 테이블을 customer_id를 기준으로 하나로 합치고, 각각 테이블에서 원하는 컬럼을 뽑아오는 것.

## JOIN의 종류

[참고하면 좋은 그림](https://i.stack.imgur.com/1UKp7.png)

| JOIN 종류            | 설명                                                   | 코드                                                        | 집합 개념          |
|-----------------------|---------------------------------------------------------|------------------------------------------------------------------|---------------------|
| **INNER JOIN**        | 양쪽 테이블 모두 존재하는 데이터만 반환               | `(INNER) JOIN`                                                    | A ∩ B (교집합)     |
| **LEFT JOIN**         | 왼쪽 테이블(A)의 모든 데이터 + 오른쪽 매칭 데이터     | `LEFT JOIN`                                                     | A                  |
| **LEFT JOIN + NULL**  | 왼쪽 데이터 중 오른쪽에 매칭되지 않는 데이터만 반환   | `LEFT JOIN ... WHERE b.key IS NULL`                             | A ∩ Bᶜ (차집합)    |
| **RIGHT JOIN**        | 오른쪽 테이블(B)의 모든 데이터 + 왼쪽 매칭 데이터     | `RIGHT JOIN`                                                    | B                  |
| **FULL OUTER JOIN**   | 양쪽 테이블의 모든 데이터 반환                        | `FULL OUTER JOIN`                                               | A ∪ B (합집합)     |

보통 INNER JOIN, LEFT JOIN을 주로 사용한다. RIGHT JOIN은 테이블 순서를 바꾸면 LEFT JOIN과 같기 때문.

## GROUP BY + JOIN

항상 유형별로 데이터를 보고싶은 경우가 생기기 때문


고객정보와 판매정보가 다른 테이블에 존재할 때, 고객유형별로 판매 데이터를 보고 싶을 경우

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

## 자주하는 실수와 해결법

| 실수 | 해석 | 해결법 |
| --- | --- | --- |
| **GROUP BY 누락** | 일반 컬럼을 카테고리화 하지 않았을 경우 |SELECT의 집계함수를 제외한 일반 컬럼은 모두 GROUP BY에 포함해야 함|
| **LEFT JOIN 후 COUNT(*) 사용** | LEFT JOIN하면 교집합이 아닌 경우도 데이터로 들어가는데, NULL이라 데이터가 없어야 하는 상황에서도 *는 데이터를 세어버림 |COUNT(오른쪽테이블.id) 사용, NULL 값 제외 |
| **컬럼 앞에 테이블명 안 쓰기** | 어디서 참조해 올지 몰라 에러가 생김 | 테이블명을 꼭 써주자 |
