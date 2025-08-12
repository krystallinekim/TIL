USE lecture;

-- 1:N 관계는 많이 써본 customer-sales, product-sales 간의 관계
-- 고객번호 1 -> 주문들이 N

SELECT
	c.customer_id AS 고객번호,
    c.customer_name AS 고객명,
    COUNT(s.id) AS 주문횟수,
    GROUP_CONCAT(s.product_name) AS 주문제품들		-- 텍스트 관련 집계함수
FROM customers c
LEFT JOIN sales s ON c.customer_id = s.customer_id
GROUP BY c.customer_id, c.customer_name;