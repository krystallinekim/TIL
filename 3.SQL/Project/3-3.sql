-- 고객별 누적 구매액 및 등급 산출

-- 각 고객의 누적 구매액을 구하고,
-- 상위 20%는 'VIP', 하위 20%는 'Low', 나머지는 'Normal' 등급을 부여하세요.
WITH customer_total AS (
	SELECT
		c.customer_id,
		c.first_name,
		c.last_name,
		SUM(i.total) AS total_sum,
		PERCENT_RANK() OVER(ORDER BY SUM(i.total)) AS percentile
	FROM customers c
	LEFT JOIN invoices i ON i.customer_id = c.customer_id
	GROUP BY c.customer_id, c.first_name, c.last_name
)
SELECT
	first_name || ' ' || last_name AS customer_name,
	total_sum,
	CASE
		WHEN percentile >= 0.8 THEN 'VIP'
		WHEN percentile <= 0.2 THEN 'LOW'
		ELSE 'Normal'
	END AS customer_rank
FROM customer_total
ORDER BY customer_id


