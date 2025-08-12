-- CTE (Common Table Expression) = 쿼리 속의 "이름이 있는" 임시 테이블
-- >> 복잡한 쿼리를 단계별로 나눠서 이해하기 쉽다

-- WITH <name> AS(SELECT ... )

-- SELECT * FROM <name>;

-- 평균주문금액보다 큰 주문들의 고객정보

-- 원래 하던 방식
-- 1. 평균주문금액 구하기
SELECT AVG(amount) FROM orders;
-- 2. 그걸 서브쿼리로 평균주문금액보다 큰 새 쿼리를 짬
SELECT c.customer_name, o.amount
FROM customers c
JOIN orders o ON c.customer_id = o.customer_id
WHERE o.amount > (SELECT AVG(amount) FROM orders)
LIMIT 10;

-- 새로운 방식
WITH avg_order AS (
	SELECT AVG(amount) AS avg_amount 
	FROM orders
)
-- WITH 뒤에는 세미콜론 붙이지 않음
-- 사이에 엔터도 넣으면 안됨
-- 서브쿼리는 스칼라, 벡터, 매트릭스 모두 가능
SELECT c.customer_name, o.amount, ao.avg_amount
FROM customers c
JOIN orders o ON c.customer_id = o.customer_id
-- avg_order를 새로운 테이블이라고 인식하고, 이걸 더해주면 된다
JOIN avg_order ao ON o.amount > ao.avg_amount
LIMIT 10;

-- 사실 지금 같은 경우는 그냥 서브쿼리를 쓰는게 맞음
-- WITH 안에 들어가는 내용이 복잡해 질수록 CTE를 쓰는게 맞다

EXPLAIN ANALYZE
SELECT
	customer_id,
	(SELECT AVG(amount) FROM orders) AS avg_amount,
	amount,
	amount - (SELECT AVG(amount) FROM orders) AS diff
FROM orders
WHERE amount > (SELECT AVG(amount) FROM orders)
LIMIT 10;
-- time: 3.375ms
-- 평균을 3번 계산해야함
-- 여기서 cte를 쓰면
EXPLAIN ANALYZE
WITH avg_orders AS (
	SELECT AVG(amount) as avg_amount
	FROM orders
)
SELECT o.customer_id, ao.avg_amount, o.amount, o.amount - ao.avg_amount AS diff
FROM orders o
JOIN avg_orders ao ON o.amount > ao.avg_amount
LIMIT 10;
-- time: 1.067ms
-- 코드가 많이 줄어든다
-- 시간도 딱 1/3으로 준 것을 볼 수 있음. 평균을 한 번만 구해도 되니까

---------------------------------------------------------------------------------------
-- 문제 1
-- 1. 각 지역별 고객 수와 주문 수를 계산하세요
-- 2. 지역별 평균 주문 금액도 함께 표시하세요
-- 3. 고객 수가 많은 지역 순으로 정렬하세요

-- - 먼저 지역별 기본 통계를 CTE로 만들어보세요
-- - 그 다음 고객 수 기준으로 정렬하세요
-- 지역    고객수   주문수   평균주문금액
-- 서울    143     7,234   567,890
-- 부산    141     6,987   534,123
-- 대구    140     6,876   545,678

WITH reg_summary AS (
	SELECT
		c.region AS 지역명,
		COUNT(DISTINCT c.customer_id) AS 고객수,
		COUNT(o.order_id) AS 주문수,
		COALESCE(AVG(o.amount),0) AS 평균주문금액
	FROM customers c
	LEFT JOIN orders o ON o.customer_id = c.customer_id
	GROUP BY c.region
)
-- 위쪽은 로직
-- 아래는 실제 어떤 데이터가 들어가는가(프레젠테이션)
-- 다른 사람이 보는 코드 입장에서 보기 편하다
SELECT 
	지역명,
	고객수,
	주문수,
	ROUND(평균주문금액) AS 평균주문금액
FROM reg_summary
ORDER BY 고객수 DESC;
----------------------------------------------------------------------

-- 문제 2
-- 1. 각 상품의 총 판매량과 총 매출액을 계산하세요
-- 2. 상품 카테고리별로 그룹화하여 표시하세요
-- 3. 각 카테고리 내에서 매출액이 높은 순서로 정렬하세요
-- 4. 각 상품의 평균 주문 금액도 함께 표시하세요
-- - 먼저 상품별 판매 통계를 CTE로 만들어보세요
-- - products 테이블과 orders 테이블을 JOIN하세요
-- - 카테고리별로 정렬하되, 각 카테고리 내에서는 매출액 순으로 정렬하세요
-- ---
-- 카테고리      상품명           총판매량   총매출액      평균주문금액   주문건수   상품가격
-- 전자제품      스마트폰 123     450       125,678,900   279,286       450       567,890
-- 전자제품      노트북 456       298       98,234,500    329,644       298       1,234,567
-- 전자제품      태블릿 789       356       87,654,321    246,197       356       890,123
-- 컴퓨터        키보드 234       567       45,678,900    80,563        567       123,456
-- 컴퓨터        마우스 345       678       34,567,890    50,982        678       89,012
-- 액세서리      이어폰 456       234       23,456,789    100,243       234       234,567

SELECT * FROM products LIMIT 20;

WITH product_sales AS (
	SELECT 
		p.category AS 카테고리, 
		p.product_name AS 상품명,
		SUM(o.quantity) AS 상품별_총판매량,
		SUM(o.amount) AS 상품별_총매출액,
		AVG(o.amount) AS 상품별_평균주문금액,
		COUNT(o.order_id) AS 상품별_주문건수,
		p.price AS 상품가격
	FROM products p 
	LEFT JOIN orders o ON o.product_id = p.product_id 
	GROUP BY p.category, p.product_id, p.product_name, p.price
)
SELECT
	카테고리,
	상품명,
	상품별_총판매량 AS 총판매량,
	상품별_총매출액 AS 총매출액,
	ROUND(COALESCE(상품별_평균주문금액,0)) AS 평균주문금액,
	상품별_주문건수 AS 주문건수,
	상품가격
FROM product_sales
ORDER BY 카테고리, 총매출액 DESC;

------------------------------------------------------------------------------
-- 문제3
-- 카테고리별 매출 비중을 비교하고 싶을 때

WITH 
	product_sales AS (
		SELECT 
			p.category AS 카테고리, 
			p.product_name AS 상품명,
			SUM(o.quantity) AS 상품별_총판매량,
			SUM(o.amount) AS 상품별_총매출액,
			AVG(o.amount) AS 상품별_평균주문금액,
			COUNT(o.order_id) AS 상품별_주문건수,
			p.price AS 상품가격
		FROM products p 
		LEFT JOIN orders o ON o.product_id = p.product_id 
		GROUP BY p.category, p.product_id, p.product_name, p.price
	),
	category_total AS (
		SELECT
			카테고리,
			SUM(상품별_총매출액) AS 카테고리별_총매출액
		FROM product_sales
		GROUP BY 카테고리
	)
SELECT
	ps.카테고리,
	ps.상품명,
	-- ps.상품별_총매출액 AS 총매출액,
	-- ct.카테고리별_총매출액,
	ROUND(100 * ps.상품별_총매출액 / ct.카테고리별_총매출액,2) AS 카테고리별_매출비중
FROM product_sales ps
JOIN category_total ct ON ps.카테고리 = ct.카테고리
ORDER BY ps.카테고리, 카테고리별_매출비중 DESC;
-----------------------------------------------------------------------------------------
-- 문제4 고객 구매금액에 따라 [VVIP(상위 20%)/VIP(전체평균보다 높음)/일반(나머지)]으로 나누어 등급 통계[등급, 등급별 회원수, 등급별 구매액 총합, 등급별 평균 주문수]를 봄
-- 고객별 총구매액, 주문수
WITH 
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