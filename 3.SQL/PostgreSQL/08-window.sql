-- WINDOW FUNCTION
-- 핵심은 "OVER()" 구문의 사용
-- WINDOW = 창문, OVER -> 너머
-- 즉, 해당 레코드 밖의 정보까지 함께 보는 것

-- 전체 구매액의 평균
SELECT AVG(amount) FROM orders;
-- 고객별 구매액의 평균
SELECT 
	customer_id,
	AVG(amount)
FROM orders
GROUP BY customer_id
-- 고객별로 구매액 평균을 보면서 동시에 전체 구매액 평균을 볼 수 없다

-- 동시에 전체 데이터와 판매액 평균을 볼 수도 없음
SELECT *,AVG(amount) FROM orders LIMIT 10;

-- CROSS JOIN을 통해 전체 데이터 뒤에 억지로 판매액 평균을 모두 JOIN 해버리지 않는 이상 볼 수 없다.

-- 전체 데이터와 집계함수를 동시에 보려면 어떻게 해야 하나요
SELECT
	order_id,
	customer_id,
	amount,
	AVG(amount) OVER() AS 전체평균
FROM orders LIMIT 10;
-- 어쨌든 다 붙였음


-- ROW_NUMBER() : 순서대로 줄세우기 (줄번호 매겨줌) >> ROW_NUMBER() OVER(ORDER BY <정렬기준>)

-- 그냥 줄세우기는 쉽다.
SELECT * FROM orders ORDER BY amount LIMIT 20;
-- 결과창에 왼쪽에 보이는 숫자는 프로그램이 보기 편하게 붙여준 것
SELECT * FROM orders ORDER BY amount LIMIT 20 OFFSET 20;
-- OFFSET을 이용하거나 하면 이 데이터가 몇번째인지 모른다

-- 데이터 레코드 하나에는 데이터 하나의 의미가 있지만, 순서는 결국 데이터 전체에 대한 정보이기 때문에 윈도우 함수임
SELECT
	order_id,
	customer_id,
	amount,
	ROW_NUMBER() OVER (ORDER BY amount DESC) AS 호구번호
FROM orders
ORDER BY 호구번호
LIMIT 20 OFFSET 20;
-- 전체에 대한 상대적인 데이터를 보여주는 것


-- RANK()
SELECT
	order_id,
	customer_id,
	amount,
	order_date,
	-- 무조건 위에서부터 무지성으로 숫자를 붙이는 ROW_NUMBER
	ROW_NUMBER() OVER (ORDER BY order_date DESC) AS 최신주문순서,
	-- 동점자를 처리해서 12명이 1등, 13번째는 13등
	RANK() OVER (ORDER BY order_date DESC) AS RANK,
	-- 동점자는 한명으로 처리해서 12명이 1등, 13번째는 2등, ...
	DENSE_RANK() OVER (ORDER BY order_date DESC) AS DENSE_RANK
FROM orders
ORDER BY 최신주문순서
LIMIT 20

-- 7월 구매액 TOP 3 고객 찾기

-- 1. 고객별 7월 구매액 구하기
WITH
july_sales AS (
	SELECT 
		c.customer_name AS 고객명,
		SUM(o.amount) AS 고객별_7월_구매액
	FROM customers c
	JOIN orders o ON o.customer_id = c.customer_id
	-- 함수처리 - MONTH(order_date)하면 인덱스를 안먹는다
	-- BETWEEN을 쓰도록 하자
	WHERE order_date BETWEEN '2024-07-01' AND '2024-07-31'
	GROUP BY o.customer_id, c.customer_name
)
-- 2. 기존 컬럼에 번호 붙이기
SELECT
	고객명,
	고객별_7월_구매액,
	RANK() OVER (ORDER BY 고객별_7월_구매액 DESC) AS 순위
FROM july_sales
ORDER BY 순위 LIMIT 3;



-- 각 지역에서 매출 1위 고객(ROW_NUMBER에서 값이 1인 사람) 뽑기 [지역, 고객이름, 총구매액]
WITH 
customer_amount AS (
	SELECT
		c.customer_name AS 고객명,
		o.region AS 지역,
		SUM(o.amount) AS 총구매액
	FROM orders o
	JOIN customers c ON o.customer_id = c.customer_id
	GROUP BY o.customer_id, c.customer_name, o.region
),
ranking AS (
SELECT
	지역,
	고객명,
	총구매액,
	RANK() OVER (PARTITION BY 지역 ORDER BY 총구매액 DESC) AS 순위
FROM customer_amount
)
SELECT
	지역,
	고객명,
	총구매액
FROM ranking
WHERE 순위 = 1
ORDER BY 총구매액 DESC;