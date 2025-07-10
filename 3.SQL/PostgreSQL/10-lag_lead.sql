-- LAG() / LEAD() : n개 전 데이터 / m개 뒤 데이터를 보러 갈 수 있다.ABORT

-- LAG(): 이전 값을 가져온다
-- >> 이전 값을 볼 수 있다면 증감률을 계산할 수 있다

-- 전월 대비 매출 분석
-- 매달 매출 확인
WITH monthly_sales AS (
	SELECT
		-- DATA_TRUNC는 특정 기준 밑으로 다 날려버리는 것
		DATE_TRUNC('month', order_date) AS 월,
		SUM(amount) AS 월매출
	FROM orders
	GROUP BY 월
),
former_month AS (
	SELECT
		TO_CHAR(월, 'YYYY-MM') AS 년월,
		월매출,
		-- 월 단위로 정렬했을 때 하나 위 데이터의 월매출을 보겠다.
		LAG(월매출,1) OVER(ORDER BY 월) AS 전월매출
	FROM monthly_sales
)
SELECT
	년월,
	월매출,
	COALESCE(월매출 - 전월매출,0) AS 증감액,
	CASE
		WHEN 전월매출 IS NULL THEN '0%'
		-- 100 * 매출변동 / 전월매출 = 증감률(%)
		ELSE ROUND((월매출 - 전월매출) * 100 / 전월매출 , 2)::TEXT || '%'
	END AS 증감률
FROM former_month;

-- LEAD(): 다음 값을 가져온다

-- 고객별 다음 구매 예측
-- 고객ID, 주문일, 구매액, 다음 주문일, 구매간격(일), 다음 구매액
WITH customer_next AS (
	SELECT
		customer_id AS 고객ID,
		order_date AS 주문일,
		LEAD(order_date,1) OVER(PARTITION BY customer_id ORDER BY order_date) AS 다음_주문일,
		amount AS 구매액,
		LEAD(amount,1) OVER(PARTITION BY customer_id ORDER BY order_date) AS 다음_구매액		
	FROM orders
)
SELECT
	고객ID,
	주문일,
	다음_주문일,
	다음_주문일 - 주문일 AS 주문간격,
	구매액,
	다음_구매액,
	다음_구매액 - 구매액 AS 구매금액차이
FROM customer_next;

-- 고객id, 주문일, 금액, 구매 순서(ROW_NUMBER)
WITH customer_orders AS (
	SELECT
		customer_id AS 고객ID,
		LAG(order_date,1) OVER(
			PARTITION BY customer_id 
			ORDER BY order_date	
			) AS 이전주문일,
		order_date AS 주문일,
		LEAD(order_date,1) OVER(
			PARTITION BY customer_id 
			ORDER BY order_date
		) AS 다음주문일,
		LAG(amount,1) OVER(
			PARTITION BY customer_id 
			ORDER BY order_date
		) AS 이전구매액,
		amount AS 구매액,
		LEAD(amount,1) OVER(
			PARTITION BY customer_id 
			ORDER BY order_date) 
		AS 다음구매액,
		ROW_NUMBER() OVER(
			PARTITION BY customer_id 
			ORDER BY order_date
			) AS 구매순서,
		SUM(amount) OVER(
			PARTITION BY customer_id 
			ORDER BY order_date
		) AS 누적구매금액,
		ROUND(AVG(amount) OVER(
			PARTITION BY customer_id 
			ORDER BY order_date 
			-- 현대 확인중인 ROW부터 맨 앞까지(기본값) ->> 다른걸로 선택하면 다른 평균도 구할 수 있다.
			ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
		),2) AS 누적평균구매금액,
		COUNT(order_id) OVER(PARTITION BY customer_id) AS 총구매횟수
	FROM orders
)
SELECT
	구매순서,
	고객ID,
	-- 구매 간격
	주문일 - 이전주문일 AS 이전주문간격,
	주문일,
	다음주문일 - 주문일 AS 다음주문간격,
	-- 구매액 변화
	ROUND((구매액 - 이전구매액) * 100 / 이전구매액, 2)::TEXT || '%' AS 이전구매액비율,
	구매액,
	ROUND((다음구매액 - 구매액) * 100 / 구매액, 2)::TEXT || '%' AS 다음구매액비율,
	-- 누적구매 통계
	누적구매금액,
	누적평균구매금액,
	CASE
		WHEN 구매순서 = 1 THEN '첫구매'
		WHEN 구매순서 <= 4 THEN '초기고객'
		WHEN 구매순서 <= 10 THEN '일반고객'
		WHEN 구매순서 > 10 THEN 'VIP'
		ELSE '오류'
	END AS 등급
FROM customer_orders
-- WHERE 구매순서 = 총구매횟수