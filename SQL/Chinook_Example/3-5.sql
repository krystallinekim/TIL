-- 최근 1년간 월별 신규 고객 및 잔존 고객

-- 최근 1년(마지막 인보이스 기준 12개월) 동안,
-- 각 월별 신규 고객 수와 해당 월에 구매한 기존 고객 수를 구하세요.
WITH 
monthly_sales AS (
	SELECT
		customer_id,
		invoice_date,
		FIRST_VALUE(invoice_date) OVER(
			PARTITION BY customer_id 
			ORDER BY invoice_date  
			ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING
		) AS first_invoice
	FROM invoices
),
customer_exist AS (
	SELECT
		TO_CHAR(DATE_TRUNC('month',invoice_date), 'YYYY-MM') AS year_month,
		invoice_date,
		customer_id,
		CASE
			WHEN invoice_date = first_invoice THEN 'New'
			ELSE 'Existing'
		END AS new_exist
	FROM monthly_sales
	WHERE invoice_date >= (SELECT MAX(invoice_date) - INTERVAL '12 months' FROM invoices)
),
newcomers AS (
	SELECT
		year_month,
		COUNT(customer_id) AS new_cust
	FROM customer_exist
	WHERE new_exist = 'New'
	GROUP BY year_month
),
existing AS (
	SELECT
		year_month,
		COUNT(customer_id) AS exist_cust
	FROM customer_exist
	WHERE new_exist = 'Existing'
	GROUP BY year_month
)
SELECT
	e.year_month,
	n.new_cust,
	e.exist_cust
FROM newcomers n
FULL OUTER JOIN existing e ON n.year_month = e.year_month;