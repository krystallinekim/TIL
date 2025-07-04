# 서브쿼리 (Subquery)

## 개념

**서브쿼리**: 쿼리 안에 또 다른 쿼리를 넣어 작성.  

주로 `WHERE`, `SELECT`, `FROM` 절에서 사용하며 외부 쿼리에서 필요한 데이터를 미리 계산해 사용.

괄호 안에 다른 쿼리(SELECT ***)를 이용하면 그 안의 쿼리를 먼저 계산 후, 그 결과를 하나의 데이터로 사용해 외부 쿼리를 계산한다.

## 결과의 형태

1. **단일 값 (Scalar)**  

    하나의 값 반환 (예: 평균, 최대값)

    보통 키 값 계산에 씀

    ```sql
    SELECT AVG(total_amount) FROM sales;
    -- 결과: 612,862
    ```

1. **한 줄 데이터 (Vector)**  

    하나의 열(column)에 여러 값 반환

    보통 WHERE IN 뒤에 조건으로 리스트 처럼 쓸 수 있다.

    ```sql
    SELECT customer_id FROM customers WHERE customer_type = 'VIP';
    -- 결과: C001, C005, C010
    ```

1. **표 형태 데이터 (Matrix)**  

    여러 행(row), 여러 열(column) 반환

    FROM 뒤에 새로운 테이블 처럼 쓸 수 있다.

    주로 사용되는건 원하는 범위를 잘라서 일부분에 대해 분석할 때 사용

    이걸 코드 안에 있는 뷰라는 뜻에서 **Inline View**라고 함.

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
    -- 가장 최근 주문들
    SELECT * FROM sales
    WHERE order_date = (SELECT MAX(order_date) FROM sales);
    -- 서브쿼리는 가장 최근인 주문의 날짜를 반환
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
    -- 서브쿼리는 VIP인 고객들을 리스트로 반환함
    -- 결과: customer_type이 VIP인 고객들의 모든 판매 데이터
    ```

1. **매트릭스 서브쿼리**

    서브쿼리 결과가 표로 나오는 경우 (FROM 뒤에 새로운 테이블 처럼 쓸 수 있음)

    ```sql
    -- 평균 매출이 500000 이상인 카테고리 분석
    SELECT * FROM (
        SELECT
            category AS 카테고리,
            ROUND(AVG(total_amount)) AS 평균매출
        FROM sales
        GROUP BY category
    ) AS category_summary
    WHERE 평균매출 >= 500000;
    -- 서브쿼리는 카테고리 별 평균 매출을 테이블로 반환
    -- 결과: 50만원보다 평균매출이 큰 경우들
    ```
    사실 위 경우는 GROUP BY를 썼기 때문에 HAVING이 더 편하긴 하지만, 서브쿼리는 그룹핑하지 않은 경우에서도 쓸 수 있다. 

    HAVING을 쓰지 못하는 경우는?
    ```sql
    -- 카테고리별 매출 필터링 [카테고리명, 평균매출, 단가구분(평균매출: 0<저단가<400000<중단가<800000<고단가)] 
    SELECT
        category AS 카테고리명,
        ROUND(AVG(total_amount)) AS 평균매출,
        CASE
            WHEN ROUND(AVG(total_amount)) > 800000 THEN '고단가'
            WHEN ROUND(AVG(total_amount)) > 400000 THEN '중단가'
            WHEN ROUND(AVG(total_amount)) > 0      THEN '저단가'
            ELSE '0'
        END AS 단가구분
    FROM sales
    GROUP BY category;
    ```
    위 방식은 이미 한 평균 매출을 매 줄마다 반복해서 계산해 줘야함. --> 매우매우 비효율적임.
    ```sql
    SELECT
        *,
        CASE
            WHEN 평균매출 > 800000 THEN '고단가'
            WHEN 평균매출 > 400000 THEN '중단가'
            WHEN 평균매출 > 0 THEN '저단가'
            ELSE '0'
        END AS 단가구분
    FROM(
        SELECT
            category AS 카테고리명,
            ROUND(AVG(total_amount)) AS 평균매출
        FROM sales
        GROUP BY category
    )AS category_analysis;
    ```
    이러면 미리 한번 평균값을 계산해 놓고, 그 값을 기준으로 바로 비교할 수 있다.
    
    또, 이럼 '평균매출'이라는 컬럼명을 계산에 넣을 수 있다. 지금처럼 평균매출 계산이 간단하면 모르지만, 계산이 매우매우 복잡해질수록 복사해서 붙여넣기 힘들어짐.

    **주의: 서브쿼리를 테이블로 가져올 때는, AS 뒤에 가져온 테이블 이름을 지정해 줘야함** 


## 서브쿼리를 써야 하는 경우

-  **집계값과 비교** (평균, 최대, 최소 등)

   - 예) 평균보다 높은 주문 찾기

- **특정 조건의 데이터 조회**

   - 예) VIP 고객들의 주문 내역


## 쓰면 안되는 경우

- 조건이 **단순하거나 고정된 값**일 때

- 여러 테이블 데이터 결합 시 **JOIN이 더 직관적**일 때