-- 2. 고객별 전체 구매 건수 계산
--     - (CustomerId, TotalPurchase)
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
)
SELECT *
FROM customer_genre_count
