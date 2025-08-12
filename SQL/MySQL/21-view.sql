USE lecture;
-- VIEW는 데이터를 저장한 게 아니라, 저장한 쿼리를 저장 --> 원본 데이터가 수정되면 뷰도 같이 바뀜
-- 매크로라고 이해하면 편함

-- 뷰 삭제
DROP VIEW customer_summary;

-- 뷰 생성
CREATE VIEW customer_summary AS
SELECT 
	c.customer_id AS 고객번호, 
    c.customer_name AS 고객명, 
    c.customer_type AS 고객유형,
	c.join_date AS 가입일,
    COUNT(s.id) AS 주문횟수,
    COALESCE(SUM(s.total_amount), 0) AS 총구매액,
    COALESCE(ROUND(AVG(s.total_amount), 0),0) AS 평균주문액,
    COALESCE(MAX(s.order_date), '주문없음') AS 최근주문일
FROM customers c
LEFT JOIN sales s ON c.customer_id = s.customer_id
GROUP BY c.customer_id, c.customer_name, c.customer_type;

SELECT * FROM customer_summary;

SELECT 
	고객유형,
    AVG(총구매액)
FROM customer_summary
GROUP BY 고객유형;

-- 충성고객(주문횟수 >5)
SELECT
	고객명
FROM customer_summary
WHERE 주문횟수 > 5;
-- 잠재고객(가입일 가장 최근인 10명)
SELECT
	*
FROM customer_summary
WHERE 최근주문일 != '주문없음'
ORDER BY 가입일 DESC
LIMIT 10;


-- View 2: 카테고리별 성과 요약 View
-- 카테고리 별로, 총 주문건수, 총매출액, 평균주문금액, 구매고객수, 판매상품수, 매출비중(전체매출에서 해당 카테고리가 차지하는비율)

-- CREATE VIEW category_performance AS
SELECT
	category AS 카테고리,
    COUNT(*) AS 총주문건수,
    SUM(total_amount) AS 총매출액,
    AVG(total_amount) AS 평균주문금액,
    COUNT(DISTINCT customer_id) AS 구매고객수,
    COUNT(DISTINCT product_id) AS 판매상품수,
    SUM(total_amount) / (SELECT SUM(total_amount) FROM sales) AS 매출비중
FROM sales
GROUP BY category;

SELECT * FROM category_performance;

-- View 3: 월별 매출 요약
-- 주문월(24-07), 월주문건수, 월평균매출액, 월활성고객수, 월신규고객수

CREATE VIEW monthly_sales AS
SELECT
	DATE_FORMAT(s.order_date,'%Y-%m') AS 주문월,
    COUNT(*) AS 월주문건수,
    AVG(s.total_amount) AS 월평균매출액,
    COUNT(DISTINCT s.customer_id) AS 월활성고객수,
    COUNT(DISTINCT CASE
		WHEN DATE_FORMAT(c.join_date,'%Y-%m') = DATE_FORMAT(s.order_date,'%Y-%m') THEN c.customer_id
        END) AS 월신규고객수
FROM sales s
JOIN customers c ON s.customer_id = c.customer_id
GROUP BY 주문월
ORDER BY 주문월;

SELECT * FROM monthly_sales;


SELECT * FROM sales;
SELECT * FROM customers;
SELECT * FROM customer_summary;