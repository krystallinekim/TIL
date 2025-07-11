-- 4. 고객별 ‘가장 많이 산 장르’ 추출
--     - ROW_NUMBER() OVER (PARTITION BY CustomerId ORDER BY Ratio DESC)
--     - → 가장 높은 비율의 장르 선택
--     - (CustomerId, 대표 장르)

WITH
customer_count AS (
	SELECT
		c.customer_id,
		c.first_name || ' ' || c.last_name AS customer_name,
		g.name AS genre_name,
		COUNT(*) OVER(PARTITION BY c.customer_id) AS totalpurchase
	FROM customers c
	JOIN invoices i ON c.customer_id = i.customer_id
	JOIN invoice_items ii ON ii.invoice_id = i.invoice_id
	JOIN tracks t ON t.track_id = ii.track_id
	JOIN genres g ON g.genre_id = t.genre_id
),
customer_genre_count AS (
	SELECT
		customer_id,
		customer_name,
		genre_name,
		totalpurchase,
		COUNT(*) AS purchasecount
	FROM customer_count
	GROUP BY customer_id, customer_name, genre_name, totalpurchase
),
customer_genre_ratio AS (
	SELECT 
		*, 
		ROUND(purchasecount * 100 / totalpurchase,2) AS ratio,
		RANK() OVER(PARTITION BY customer_id ORDER BY purchasecount DESC)
	FROM customer_genre_count
)
SELECT 
	customer_id,
	customer_name,
	genre_name
FROM customer_genre_ratio 
WHERE rank = 1
