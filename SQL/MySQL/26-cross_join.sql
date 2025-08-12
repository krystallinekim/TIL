USE lecture;
-- 카르테시안 곱, 곱집합 --> 모든 경우의 수 조합을 만듦

SELECT 
	c.customer_name,
    p.product_name
FROM customers c
CROSS JOIN products p;
-- 고객 한 명이 상품 하나를 살 수 있는 모든 경우의 수
-- customers 50개 * products 20개 = 총 1000가지 경우의 수가 나옴

SELECT 
	c.customer_name,
    p.product_name,
    p.category,
    p.selling_price
FROM customers c
CROSS JOIN products p
WHERE c.customer_type = 'VIP'
ORDER BY c.customer_name, p.category;

-- 사용처: VIP 고객에게 구매하지 않은 상품만 추천할 때

SELECT
	c.customer_name AS 고객명,
    p.product_name AS 미구매
FROM customers c
CROSS JOIN products p
WHERE c.customer_type = 'VIP' 
	-- 서브쿼리에서 1 혹은 0을 반환했을 때, NULL인 경우를 고르겠다 --> 미구매 상품
	AND NOT EXISTS(
		-- SELECT 1 WHERE 조건 하면 결과값은 1이나 NULL을 반환
		SELECT 1 FROM sales s
        -- 구매 내역이 있는가?
        -- sales에 있는 고객정보와 상품정보가 둘다 있을 때 1
		WHERE s.customer_id = c.customer_id
		AND s.product_id = p.product_id
	)
ORDER BY 고객명;

-- CROSS JOIN은 모든 경우의 수를 뽑아서, 그중 필요없는 경우를 제외하면서 확인할 때 사용함 