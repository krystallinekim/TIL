USE lecture;
SELECT * FROM customers;
SELECT * FROM sales;

-- Scala, Vector, Matrix
-- 스칼라는 한 개의 데이터 / 벡터는 한 줄로 이루어진 데이터 / 매트릭스는 표로 이루어진 데이터

-- 스칼라
SELECT COUNT(*) FROM customers;
-- 벡터
SELECT customer_id FROM customers;
-- 매트릭스
SELECT * FROM customers;


-- 모든 VIP의 id
SELECT customer_id FROM customers WHERE customer_type = 'VIP';
-- 결과: C001, C005, C010, C013, ...

-- sales에서 (모든 VIP)의 주문내역??
-- 모든 VIP의 id(서브쿼리)와 IN을 이용
SELECT * 
FROM sales
WHERE customer_id IN (
    SELECT customer_id 
    FROM customers 
    WHERE customer_type = 'VIP'
)
ORDER BY total_amount DESC;

-- (전자제품을 구매한 고객들)의 모든 주문을 보고싶다
-- 전자제품을 구매한 고객들
-- DISTINCT로 중복 제거하는거 잊지 말기
SELECT DISTINCT customer_id FROM sales WHERE category = '전자제품';

-- 모든 주문 보기
SELECT * FROM sales 
WHERE customer_id IN (
    SELECT DISTINCT customer_id 
    FROM sales 
    WHERE category = '전자제품'
)
ORDER BY customer_id, total_amount DESC;
-- ORDER BY로 정렬하거나, GROUP BY와 HAVING으로 그룹화하는 것도 가능함
-- 스칼라든(=), 벡터(IN)든 전부 WHERE 구절 안에서 이용중