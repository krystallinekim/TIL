USE lecture;

-- ANY : 여러 값들 가운데 하나라도 만족하면 TRUE (OR)

-- 1. VIP 고객들의 최소 주문액보다 높은 모든 주문

-- 서브쿼리 : VIP의 total amount
SELECT 
	s.total_amount
FROM sales s
INNER JOIN customers c ON s.customer_id = c.customer_id
WHERE c.customer_type = 'VIP';

SELECT
	customer_id,
    product_name,
    total_amount
FROM sales 
WHERE total_amount > ANY(	
	-- 1. VIP의 모든 주문금액들(벡터)
	SELECT 
		s.total_amount
	FROM sales s
	INNER JOIN customers c ON s.customer_id = c.customer_id
	WHERE c.customer_type = 'VIP'
)
-- >> 일반 sales에서 total_amount 값을 찾고, ANY 안의 값(벡터)들과 하나하나 비교해서 하나라도 더 크기만 하면 TRUE 
AND
customer_id NOT IN (SELECT customer_id FROM customers WHERE customer_type = 'VIP')
ORDER BY total_amount DESC;

-- 비교(MIN 사용) >> 위 코드는 아래 코드와 같은 결과임

SELECT
	customer_id,
    product_name,
    total_amount
FROM sales 
WHERE total_amount > (	
	SELECT 
		MIN(s.total_amount)
	FROM sales s
	INNER JOIN customers c ON s.customer_id = c.customer_id
	WHERE c.customer_type = 'VIP'
)
AND
customer_id NOT IN (SELECT customer_id FROM customers WHERE customer_type = 'VIP')
ORDER BY total_amount DESC;

-- >> 결과는 같지만, 뉘앙스가 다름

-- 모든 매출들 중 어떤 지역 평균 매출액의 최소값보다 매출이 높은 주문들
SELECT * FROM sales 
-- 2. 벡터 중 작은 값이 있다면 조회
WHERE total_amount > ANY(
	-- 1. 각 지역 평균 매출액들(벡터)
	SELECT AVG(total_amount) FROM sales GROUP BY region)
ORDER BY total_amount;


-- ALL : 벡터의 모든 값들이 조건을 만족하면 통과

SELECT * FROM employees
-- 2. 벡터 안의 모든 값보다 큰 값이면 반환
WHERE salary > ALL(
	-- 1. 부서 내 연봉의 평균
	SELECT AVG(salary) FROM employees GROUP BY department_id);


