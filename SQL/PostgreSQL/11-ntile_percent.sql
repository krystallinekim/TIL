-- 상위 n%, 하위 n% 자동으로 하기
-- 이거도 전체 대비 각 레코드의 내용이므로 윈도우함수임

-- NTILE: N등분/균등하게 나누기, NTILE(4): 0%-25%-50%-75%-100% ->> 4등분
-- NTILE(4) OVER (ORDER BY 총구매금액) AS 4분위 ->> 1,2,3,4로 전체를 나눠줌

WITH 
totals AS (
	SELECT
		customer_id AS 고객ID,
		SUM(amount) AS 총구매금액,
		COUNT(order_id) AS 구매횟수
	FROM orders
	GROUP BY 고객ID
),
customer_grade AS(
	SELECT
		고객ID,
		총구매금액,
		NTILE(4) OVER(ORDER BY 총구매금액) AS 총구매금액_4분위,
		구매횟수
	FROM totals
)
SELECT
	c.customer_name AS 고객명,
	cg.총구매금액,
	cg.구매횟수,
	CASE
		WHEN cg.총구매금액_4분위 = 1 THEN '브론즈'
		WHEN cg.총구매금액_4분위 = 2 THEN '실버'
		WHEN cg.총구매금액_4분위 = 3 THEN '골드'
		WHEN cg.총구매금액_4분위 = 4 THEN '플래티넘'
	END AS 고객등급
FROM customer_grade cg
JOIN customers c ON cg.고객ID = c.customer_id

-- PERCENT_RANK(): 더 자유도가 높음

WITH ranking AS (
	SELECT
		product_id,
		product_name,
		category,
		price,
		RANK() OVER(ORDER BY price DESC) AS 가격순위,
		-- 0부터 1까지 비율
		PERCENT_RANK() OVER(ORDER BY price DESC) AS 백분위순위
	FROM products
)
SELECT
	product_name AS 상품명,
	category AS 카테고리,
	price AS 가격,
	CASE
		WHEN 백분위순위 >= 0.9 THEN '최저가(하위10%)'
		WHEN 백분위순위 >= 0.7 THEN '저가(하위30%)'
		WHEN 백분위순위 >= 0.3 THEN '일반가'
		WHEN 백분위순위 >= 0.1 THEN '고가(상위30%)'
		WHEN 백분위순위 < 0.1 THEN '최고가(상위10%)'
	END AS 등급
FROM ranking
ORDER BY product_id;



-- 파티션에서의 처음/마지막 값
SELECT
	category,
	product_name,
	price,
	FIRST_VALUE(product_name) OVER(
		PARTITION BY category
		ORDER BY price DESC
		ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING -- 딱히 지정 안한 이전 ~ 딱히 지정 안한 나중 >> 파티션 내부 전부 >> 없으면 지금현재값까지 최대/최저값을 가져옴
	)AS 최고가상품명,
	FIRST_VALUE(price) OVER(
		PARTITION BY category
		ORDER BY price DESC
		ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING
	)AS 최고가격,
	-- LAST_VALUE는 반대
	-- ORDER BY DESC를 ASC로 바꾸면 똑같음
	LAST_VALUE(product_name) OVER(
		PARTITION BY category
		ORDER BY price DESC
		ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING
	)AS 최저가상품명,
	LAST_VALUE(price) OVER(
		PARTITION BY category
		ORDER BY price DESC
		ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING
	)AS 최저가격
FROM products