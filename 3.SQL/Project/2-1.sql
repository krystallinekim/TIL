-- 직원별 담당 고객 수 집계

-- 각 직원(employee_id, first_name, last_name)이 담당하는 고객 수를 집계하세요.
-- 고객이 한 명도 없는 직원도 모두 포함하고, 고객 수 내림차순으로 정렬하세요.

SELECT
	e.employee_id,
	e.first_name,
	e.last_name,
	COUNT(DISTINCT customer_id) AS customer_count
FROM employees e
LEFT JOIN customers ON customers.support_rep_id = e.employee_id
GROUP BY e.employee_id, e.first_name, e.last_name
ORDER BY customer_count DESC;