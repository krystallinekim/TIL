USE lecture;

SELECT * FROM sales;
SELECT * FROM customers; 

-- 서브쿼리로

SELECT
  c.customer_id,
  c.customer_name,
  c.customer_type,
  (SELECT COUNT(*)
     FROM sales s
     WHERE s.customer_id = c.customer_id) AS 총주문횟수,
  (SELECT COALESCE(SUM(total_amount), 0)
     FROM sales s
     WHERE s.customer_id = c.customer_id) AS 총주문금액,
  (SELECT COALESCE(AVG(total_amount), 0)
     FROM sales s
     WHERE s.customer_id = c.customer_id) AS 평균주문금액,
  (SELECT COALESCE(MAX(order_date), '주문없음')
     FROM sales s
     WHERE s.customer_id = c.customer_id) AS 최근주문일
FROM customers c;

-- JOIN + GROUP

SELECT
    c.customer_id,
    c.customer_name,
    c.customer_type,
    COUNT(s.id) AS 총주문횟수,
    COALESCE(SUM(s.total_amount), 0) AS 총주문금액,
    ROUND(COALESCE(AVG(s.total_amount), 0), 0) AS 평균주문금액,
    COALESCE(MAX(s.order_date), '주문없음') AS 최근주문일
FROM customers c
LEFT JOIN sales s
    ON c.customer_id = s.customer_id
GROUP BY
    c.customer_id,
    c.customer_name,
    c.customer_type
ORDER BY 총주문금액 DESC;