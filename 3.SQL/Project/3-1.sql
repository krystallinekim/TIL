-- 월별 매출 및 전월 대비 증감률

-- 각 연월(YYYY-MM)별 총 매출과, 전월 대비 매출 증감률을 구하세요.
-- 결과는 연월 오름차순 정렬하세요.
WITH 
monthly_sales AS (
	SELECT
		DATE_TRUNC('month',invoice_date) AS month_invoice_date,
		SUM(total) AS monthly_total
	FROM invoices
	GROUP BY month_invoice_date
	ORDER BY month_invoice_date
),
former_month AS (
	SELECT
		TO_CHAR(month_invoice_date, 'YYYY-MM') AS year_month_invoice_date,
		monthly_total,
		LAG(monthly_total,1) OVER(ORDER BY month_invoice_date) AS lag_monthly_total
	FROM monthly_sales
)
SELECT
	year_month_invoice_date,
	monthly_total,
	CASE
		WHEN lag_monthly_total IS NULL THEN '-'
		ELSE ROUND((monthly_total - lag_monthly_total) * 100 / lag_monthly_total, 2)::TEXT || '%'
	END
FROM former_month
ORDER BY year_month_invoice_date;