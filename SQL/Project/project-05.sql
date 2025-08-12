-- 5. 대표 장르 기준으로 고객을 그룹화 (클러스터링 대체)
--     - Rock 고객 그룹, Pop 고객 그룹 등

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
		RANK() OVER(PARTITION BY customer_id ORDER BY purchasecount DESC) AS ranking
	FROM customer_genre_count
),
customer_genre AS (
	SELECT 
		customer_id,
		customer_name,
		genre_name
	FROM customer_genre_ratio 
	WHERE ranking = 1
)
-- SELECT * FROM customer_genre;
SELECT
	genre_name,
	COUNT(*)
	-- STRING_AGG(customer_name,',' ORDER BY customer_name) AS customers
FROM customer_genre
GROUP BY genre_name
