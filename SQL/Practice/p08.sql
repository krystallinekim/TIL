USE practice;
CREATE TABLE customers AS SELECT * FROM lecture.customers;
SELECT * FROM sales;
SELECT * FROM products;
SELECT * FROM customers;


-- 스칼라형 서브쿼리

-- 1-1. (평균) 이상 매출 주문들
-- 매출 평균
SELECT AVG(total_amount) FROM sales;
-- 평균 이상 매출 주문들
SELECT * FROM sales
WHERE total_amount >= (
    SELECT AVG(total_amount) FROM sales
);

-- 1-2. (최고 매출 지역)의 모든 주문들
-- 최고 매출 지역
SELECT
    region
FROM sales
GROUP BY region
ORDER BY SUM(total_amount) DESC
LIMIT 1;

-- 최고매출지역의 주문들
SELECT * FROM sales
WHERE region = (
    SELECT region
    FROM sales
    GROUP BY region
    ORDER BY SUM(total_amount) DESC
    LIMIT 1
);

-- 벡터형 서브쿼리

-- (기업 고객들)의 모든 주문 내역
-- 기업 고객들
SELECT DISTINCT customer_id FROM customers;
-- 주문 내역
SELECT * FROM sales WHERE customer_id IN (SELECT DISTINCT customer_id FROM customers);

-- (재고 부족(50개 미만) 제품)의 매출 내역
-- 재고가 부족한 제품들
SELECT product_id FROM products WHERE stock_quantity < 50;
-- 재고 부족 제품의 매출
SELECT * FROM sales 
WHERE product_id IN (
    SELECT product_id 
    FROM products 
    WHERE stock_quantity < 50
);

-- (상위 3개 매출 지역)의 주문들
-- 매출 상위 3개 지역
SELECT region 
FROM sales
GROUP BY region
ORDER BY SUM(total_amount) DESC
LIMIT 3;

-- 서브쿼리에서 LIMIT을 쓸수가 없음
SELECT *
FROM sales
WHERE region IN (
    SELECT region
    FROM sales
    GROUP BY region
    ORDER BY SUM(total_amount) DESC
    LIMIT 3);

-- 주문 목록(대구, 인천, 부산)
SELECT *
FROM sales
WHERE region IN (
    SELECT region FROM (
        SELECT region
        FROM sales
        GROUP BY region
        ORDER BY SUM(total_amount) DESC
        LIMIT 3
    ) AS top_regions
);


-- 상반기(1/1 ~ 6/30) 주문 고객들의 하반기(7/1 ~ 12/31) 주문내역
-- 상반기 주문 고객들의 ID 목록
SELECT DISTINCT customer_id FROM sales
WHERE MONTH(order_date) BETWEEN 1 AND 6;
-- 하반기 AND 상반기 주문 고객들의 주문내역
SELECT * FROM sales
WHERE 
    (MONTH(order_date) BETWEEN 7 AND 12) 
    AND 
    customer_id IN (
        SELECT DISTINCT customer_id FROM sales
        WHERE MONTH(order_date) BETWEEN 1 AND 6
    );