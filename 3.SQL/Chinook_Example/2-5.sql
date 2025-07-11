-- 각 고객의 최근 구매 내역

-- 각 고객별로 가장 최근 인보이스(invoice_id, invoice_date, total) 정보를 출력하세요.

WITH customer_invoice AS (
	SELECT
		c.customer_id,
		c.first_name,
		c.last_name,
		i.invoice_id,
		i.invoice_date,
		i.total,
		FIRST_VALUE(i.invoice_date) OVER(
		  PARTITION BY c.customer_id
		  ORDER BY i.invoice_date DESC
		  ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING
		) AS first_invoice
	FROM customers c
	LEFT JOIN invoices i ON i.customer_id = c.customer_id
	ORDER BY c.customer_id
)
SELECT
	customer_id,
	first_name || ' ' || last_name AS customer_name,
	invoice_id,
	invoice_date,
	total
FROM customer_invoice
WHERE invoice_date = first_invoice
ORDER BY customer_id;