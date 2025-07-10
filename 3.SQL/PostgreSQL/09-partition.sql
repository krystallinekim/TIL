-- OVER랑 같이 쓰는 것
-- 특정 무언가를 그룹으로 거기서 윈도우 함수를 쓰고 싶을 때
-- OO 기준으로 1등 << 이런거 찾을 때 쓴다

-- 1, 2, 3학년 체육대회에서 **한번에** 학년 순위 컬럼과 전체 순위 컬럼을 같이 볼 수 있다!!

SELECT 
	region,
	customer_id,
	amount,
	-- 이건 전체 기준으로 순위를 뽑은 것
	ROW_NUMBER() OVER(ORDER BY amount DESC) AS 전체순위,
	-- 지역별로 순위를 따로 나누는 것
	ROW_NUMBER() OVER(PARTITION BY region ORDER BY amount DESC) AS 지역순위
FROM orders LIMIT 10;

-- GROUP BY를 쓰면 개별 레코드가 묻히고, 지역별로 WHERE로 나눠서 UNION으로 합치면 귀찮아짐

-- SUM() OVER()
-- 일별 누적 매출액
WITH daily_sales AS (
	SELECT
		order_date,
		-- GROUP BY를 쓴 이건 일별 매출의 합
		SUM(amount) AS 일매출
	FROM orders
--	WHERE order_date BETWEEN '2024-07-01' AND '2024-07-31'
	GROUP BY order_date
	ORDER BY order_date
)
SELECT
	order_date,
	일매출,
	-- 해당 일자까지 팔린 누적 매출(1월 1일부터)
	-- 윈도우 함수로 SUM()을 쓰면 누적합계
	SUM(일매출) OVER(ORDER BY order_date) AS 총누적매출,
	-- 해당 일자까지 팔린 누적매출(각 달 1일부터)
	-- SUM()과 PARTITION을 같이 쓰면 월별 누적합계를 보여준다
	-- 범위 내에서 파티션이 바뀌면 초기화임
	SUM(일매출) OVER(
		PARTITION BY DATE_TRUNC('MONTH',order_date)
		ORDER BY order_date
	) AS 월누적매출
FROM daily_sales;

-- AVG() OVER()

-- 누적평균 (첫날부터 특정일까지의 평균)
SELECT
	region,
	order_date,
	amount,
	-- 지역별로 자르고 / 일별 누적평균 
	-- >> 7/1에는 7/1에 판 매출의 평균, 7/2에는 7/1부터 7/2까지 판 매출의 평균
	AVG(amount) OVER (PARTITION BY region ORDER BY order_date) AS 지역별_매출누적평균
FROM orders
WHERE order_date BETWEEN '2024-07-01' AND '2024-07-02';

-- 이동평균 (특정 기간으로 자른 누적평균)
WITH daily_sales AS (
	SELECT
		order_date,
		SUM(amount) AS 일매출,
		COUNT(*) AS 주문수
	FROM orders
	GROUP BY order_date
	ORDER BY order_date
)
SELECT
	order_date,
	일매출,
	주문수,
	ROUND(AVG(일매출) OVER(
		ORDER BY order_date
		-- 오늘부터 6일 전 + 오늘까지의 행의 평균(7개)
		ROWS BETWEEN 6 PRECEDING AND CURRENT ROW
	)) AS 이동평균_7일,
	ROUND(AVG(일매출) OVER(
		ORDER BY order_date
		-- 오늘부터 이틀 전 + 오늘까지의 행의 평균(3개)
		ROWS BETWEEN 2 PRECEDING AND CURRENT ROW
	)) AS 이동평균_3일
FROM daily_sales;

-- AVG() OVER()에도 PARTITION BY를 넣으면 그룹별로 자를 수 있다



-- 각 지역에서 매출 1위 고객(ROW_NUMBER에서 값이 1인 사람) 뽑기 [지역, 고객이름, 총구매액]

-- 지역-사람별 매출 데이터 생성
WITH 
customer_amount AS (
	SELECT
		c.customer_name AS 고객이름,
		o.region AS 지역,
		SUM(o.amount) AS 총구매액
	FROM orders o
	JOIN customers c ON o.customer_id = c.customer_id
	GROUP BY o.customer_id, c.customer_name, o.region
),
-- 매출데이터에 새로운 열(순서) 추가
ranking AS (
SELECT
	지역,
	고객이름,
	총구매액,
	RANK() OVER (PARTITION BY 지역 ORDER BY 총구매액 DESC) AS 순위
FROM customer_amount
)
-- 최종 데이터 표시
SELECT
	지역,
	고객이름,
	총구매액
FROM ranking
WHERE 순위 = 1
ORDER BY 총구매액 DESC;


-- 카테고리별 인기상품(매출순위) TOP 5
-- 카테고리, 상품id, 상품이름, 상품가격, 주문건수, 판매개수, 총매출
WITH 
product_stat AS (
	SELECT
		p.category AS 카테고리,
		p.product_id AS 상품ID,
		p.product_name AS 상품이름,
		p.price AS 상품가격,
		COUNT(o.order_id) AS 상품별_주문건수,
		SUM(o.quantity) AS 상품별_판매개수,
		SUM(o.amount) AS 상품별_총매출
	FROM products p
	LEFT JOIN orders o ON o.product_id = p.product_id
	GROUP BY p.category, p.product_id, p.product_name, p.price
),
-- 윈도우함수 + 매출순위, 판매량순위
ranking AS (
	SELECT
		카테고리,
		상품이름,
		상품가격,
		상품별_주문건수,
		상품별_판매개수,
		DENSE_RANK() OVER(PARTITION BY 카테고리 ORDER BY 상품별_판매개수 DESC) AS 판매량순위,
		상품별_총매출,
		DENSE_RANK() OVER(PARTITION BY 카테고리 ORDER BY 상품별_총매출 DESC) AS 매출순위
	FROM product_stat
)
-- 매출순위 1~5위
-- SELECT
-- 	카테고리, 상품이름, 상품가격, 상품별_주문건수, 상품별_판매개수, 상품별_총매출, 매출순위
-- FROM ranking
-- WHERE 매출순위 <= 5
-- ORDER BY 카테고리, 매출순위;
-- 판매량 순위
SELECT
	카테고리, 상품이름, 상품가격, 상품별_주문건수, 상품별_판매개수, 상품별_총매출, 판매량순위
FROM ranking
WHERE 판매량순위 <= 5
ORDER BY 카테고리, 판매량순위;