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

1. **스칼라 서브쿼리**

    서브쿼리 결과가 값으로 나오는 경우

    **평균과 비교**

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

    **최대/최소값 찾기**
    
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

1. **벡터 서브쿼리**

    서브쿼리 결과가 데이터 리스트로 나오는 경우 (원래 쿼리에 IN을 이용함)

    **특정 조건 하의 모든 값 찾기**
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

1. **매트릭스 서브쿼리**

    서브쿼리 결과가 표로 나오는 경우 (원래 쿼리에 JOIN을 통해 붙임)


## 서브쿼리를 써야 하는 경우

-  **집계값과 비교** (평균, 최대, 최소 등)

   - 예) 평균보다 높은 주문 찾기

- **특정 조건의 데이터 조회**

   - 예) VIP 고객들의 주문 내역


## 쓰면 안되는 경우

- 조건이 **단순하거나 고정된 값**일 때

- 여러 테이블 데이터 결합 시 **JOIN이 더 직관적**일 때