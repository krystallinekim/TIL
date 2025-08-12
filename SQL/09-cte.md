# CTE

## 기초

**C**ommon **T**able **E**xpression

쿼리 안의 *이름이 있는* 임시 테이블을 말한다.

복잡한 쿼리를 단계별로 나눠서 이해하기 쉬워진다.

CTE는 여러 개 붙여서 만들 수도 있다(보통 여러 개 필요할 때 쓴다)

## CTE, 서브쿼리, View
| 항목               | CTE                         | 서브쿼리            | VIEW                              |
|--------------------|-------------------------------------|-------------------------------------|---------------------------------------|
| **정의 방식**       | `WITH <name> AS (SELECT ...)`                | 쿼리 안에 `(SELECT ...)` 바로 삽입     | `CREATE VIEW name AS SELECT ...`             |
| **지속성**         | ✖ 일회성 (한 쿼리 내)               | ✖ 일회성 (해당 구문 내)              | ✔ 영구 (DB에 저장됨)                  |
| **재사용 가능성**   | △ 같은 쿼리 안에서만 재사용 가능     | ✖ 재사용 불가                        | ✔ 여러 쿼리에서 재사용 가능           |
| **가독성**         | ✔ 좋음 (이름 붙인 쿼리 블록)       | ✖ 복잡해지기 쉬움                   | ✔ 깔끔함 (외부에서 보기 쉬움)         |
| **계층적/재귀 쿼리**| ✔ 가능 (`WITH RECURSIVE`)          | ✖ 불가능                             | ✖ 불가능                               |
| **인덱스 사용**     | ✔ 가능 (인라인 최적화)              | ✔ 가능                               | △ 가능은 함, 실행계획에 매우 주의해야 함            |
| **파라미터 전달**   | ✖ 불가능                           | ✖ 불가능                             | △ 불가능 (함수 사용 필요)           |
| **권한 제어**       | ✖ 불가능                           | ✖ 불가능                             | ✔ 가능 (`SELECT` 권한 부여 가능)      |
| **작성 목적**       | 쿼리 구조 나누기 / 가독성 향상      | 쿼리 내 즉시 처리용                 | 공용 쿼리 저장 / 반복 사용 목적       |


- CTE
    ```sql
    WITH high_salary AS (
        SELECT * FROM employees WHERE salary > 50000
    )
    SELECT name FROM high_salary WHERE department = 'Sales';
    ```
- 서브쿼리
    ```sql
    SELECT name
    FROM (
        SELECT * FROM employees WHERE salary > 50000
    ) AS high_salary
    WHERE department = 'Sales';
    ```
- View

    ```sql
    -- 미리 정의
    CREATE VIEW high_salary AS
    SELECT * FROM employees WHERE salary > 50000;
    -- 나중에 재사용
    SELECT name FROM high_salary WHERE department = 'Sales';
    ```
위 셋은 완벽하게 똑같은 결과를 보여주지만, 서로 용도와 목적이 다르다.

상황에 따라 위 셋을 잘 바꿔가면서 쓰는 게 좋다.

매우 단순한 쿼리일 때 CTE를 쓰는 것도 웃기고, 같은 로직을 이 쿼리 밖에서도 써야 한다면 View가 더 낫다.


## CTE를 사용하는 이유

1. 가독성 향상
    ```sql
    -- 복잡한 서브쿼리 (이해하기 어려움)
    SELECT * FROM orders WHERE amount > (
        SELECT AVG(amount) FROM orders WHERE region = (
            SELECT region FROM customers WHERE customer_id = 'CUST-001'
        )
    );

    -- CTE 사용 (단계별로 명확)
    WITH 
    customer_region AS (
        SELECT region FROM customers WHERE customer_id = 'CUST-001'
    ),
    region_avg AS (
        SELECT AVG(amount) as avg_amount
        FROM orders o JOIN customer_region cr ON o.region = cr.region
    )
    SELECT * FROM orders o JOIN region_avg ra ON o.amount > ra.avg_amount;
    ```

    코드 자체는 서브쿼리가 더 간단해 보일 수 있으나, 실제로 코드의 어떤 부분에서 어떤 작용이 일어나는지 보는 데는 CTE가 더 유리하다.

1. 성능 향상(최적화)

    ```sql
    -- 중복 계산 (비효율)
    SELECT
        customer_id,
        (SELECT AVG(amount) FROM orders),  -- 계산 1회
        amount - (SELECT AVG(amount) FROM orders)  -- 계산 2회
    FROM orders;

    -- CTE 사용 (한 번만 계산)
    WITH avg_amount AS (
        SELECT AVG(amount) as avg_val FROM orders
    )
    SELECT customer_id, avg_val, amount - avg_val FROM orders, avg_amount;
    ```

    서브쿼리는 한 코드 안에 여러개의 서브쿼리가 들어가면, 같은 내용의 서브쿼리라도 그냥 계산하고 넘어가게 된다.

    CTE는 처음 한번 계산해 그 결과를 저장하고, 저장된 결과값에서 꺼내 쓰는 형태이기 때문에 계산횟수가 줄어든다.


1. 재사용성
    ```sql
    WITH 
    monthly_sales AS (
        SELECT DATE_TRUNC('month', order_date) as month, SUM(amount) as sales
        FROM orders GROUP BY month
    )
    SELECT month, sales FROM monthly_sales WHERE sales > 1000000
    UNION ALL
    SELECT '평균' as month, AVG(sales) FROM monthly_sales;
    ```

    한 쿼리 안에 있기만 하면(`;`으로 구분되는), CTE는 몇 번이고 재사용 가능하다.

    CTE에 여러 값을 저장해 놓고, SELECT에서 다양한 값들을 뽑아 쓰는 것도 가능

## 예시

Q. 고객의 구매금액에 따라 등급을 나누고, 등급별 통계를 내보자
```sql
WITH 
-- 고객별 총구매액, 주문수
customer_amount AS (
	SELECT
		c.customer_id,
		COUNT(*) AS 고객별_주문수,
		SUM(o.amount) AS 고객별_구매액
	FROM customers c
	LEFT JOIN orders o ON o.customer_id = c.customer_id
	GROUP BY c.customer_id
	ORDER BY 고객별_구매액
),
-- 등급 구분 기준을 만드는 CTE
purchase_threshold AS(
	SELECT 
		-- 상위 20% 자르기
		PERCENTILE_CONT(0.8) WITHIN GROUP(ORDER BY 고객별_구매액) AS 상위20기준,
		AVG(고객별_구매액) AS 평균기준
	FROM customer_amount
),
-- 등급구분 - VVIP(상위 20%)/VIP(전체평균보다 높음)/일반(나머지)
vips AS (
	SELECT
		ca.customer_id,
		ca.고객별_주문수,
		ca.고객별_구매액,
		CASE
			WHEN ca.고객별_구매액 >= pt.상위20기준 THEN 'VVIP'
			WHEN ca.고객별_구매액 >= pt.평균기준 THEN 'VIP'
			ELSE '일반'
		END AS 등급
	FROM customer_amount ca
	CROSS JOIN purchase_threshold pt
)
-- 데이터 집계
SELECT
	등급,
	COUNT(*) AS 등급별_회원수,
	SUM(고객별_구매액) AS 등급별_구매액_총합,
	ROUND(AVG(고객별_주문수),2) AS 등급별_평균_주문수
FROM vips
GROUP BY 등급;
```

CTE를 사용하는 이유인 가독성 향상, 최적화, 재사용성 모두 보이는 것을 알 수 있다.

## 재귀 CTE

### 기초

재귀 = 자기 자신을 참조해 자신을 정의하는 것

수학에서, Factorial을 정의할 때를 생각해 보면 편하다.

f(n) = n!일 때,

f(n) =  n * f(n-1) (n>1)
                 1 (n=1)

즉, f(3) = 3 * f(2) = 3 * 2 * f(1) = 3 * 2 * 1 = 6


### 구조

```sql
WITH RECURSIVE <쿼리이름> AS(
    ...
    UNION ALL
    ...
    FROM <쿼리이름>
)
SELECT * FROM <쿼리이름>
```
CTE 내부에 자기 자신이 들어가 있는 것을 볼 수 있다.(재귀)

재귀 CTE 안에 **종료 조건**을 명확히 설정하는 것이 가장 중요

### 사용처

- 계층 구조 데이터

    회사 내 조직도, 메뉴 구조, 카테고리 트리 등

    ```sql
    WITH RECURSIVE org_tree AS (
        SELECT employee_id, employee_name, manager_id, 1 as level
        -- 상사ID가 없을 경우(CEO)
        FROM employees WHERE manager_id IS NULL
        -- 중복 무시하고 더해줌
        UNION ALL
        -- 상사 ID가 org_tree에 있는 경우 레벨을 하나 더해서 org_tree에 더함
        SELECT e.employee_id, e.employee_name, e.manager_id, ot.level + 1
        -- org_tree라는 CTE 안에 org_tree가 또 들어있는 것을 알 수 있다.
        FROM employees e JOIN org_tree ot ON e.manager_id = ot.employee_id
    )
    -- ||는 CONCAT
    SELECT REPEAT('  ', level-1) || employee_name as org_chart FROM org_tree;
    ```
    
- 연속 데이터 생성
    
    주로 날짜 범위나 숫자 시퀀스를 생성할 떄 씀
    
    ```sql
    WITH RECURSIVE date_range AS (
        SELECT '2024-01-01'::date as date_val
        UNION ALL
        SELECT date_val + 1 FROM date_range WHERE date_val < '2024-01-31'
    )
    SELECT d.date_val, COALESCE(SUM(o.amount), 0) as daily_sales
    FROM date_range d LEFT JOIN orders o ON d.date_val = o.order_date
    GROUP BY d.date_val ORDER BY d.date_val;

    ```