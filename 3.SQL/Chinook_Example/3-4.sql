-- 국가별 재구매율(Repeat Rate)

-- 각 국가별로 전체 고객 수, 2회 이상 구매한 고객 수, 재구매율을 구하세요.
-- 결과는 재구매율 내림차순 정렬.
WITH 
customer_sales AS (
	SELECT
		c.customer_id,
		c.country
	FROM customers c
	JOIN invoices i ON c.customer_id = i.customer_id
),
country_total_customers AS (
	SELECT
		country,
		COUNT(DISTINCT customer_id) AS customer_total
	FROM customer_sales
	GROUP BY country
),
country_2times_customers AS (
	SELECT
		country,
		COUNT(customer_id) AS customer_buy
	FROM customer_sales
	GROUP BY country, customer_id
	HAVING COUNT(customer_id) >= 2
),
country_2times AS (
	SELECT
		country,
		COUNT(customer_buy) AS customer_2buy
	FROM country_2times_customers
	GROUP BY country
)
SELECT
	ct.country,
	ct.customer_total,
	c2.customer_2buy,
	c2.customer_2buy * 100 / ct.customer_total AS repeat_rate
FROM country_total_customers ct
LEFT JOIN country_2times c2 ON ct.country = c2.country
ORDER BY repeat_rate DESC, customer_total DESC