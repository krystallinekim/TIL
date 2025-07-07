-- 데이터 무결성 검사(양 쪽에 비어있는 데이터 찾기)
-- MySQL에는 FULL OUTER JOIN이 없다.
-- 왼쪽, 오른쪽 구해서 UNION으로 합쳐줘야함
-- LEFT JOIN + RIGHT JOIN - 교집합
USE lecture;
SELECT
	'LEFT' AS 출처,
    c.customer_name,
    s.product_name
FROM customers c
LEFT JOIN sales s on c.customer_id = s.customer_id

UNION

SELECT 
	'RIGHT' AS 출처,
    c.customer_name,
    s.product_name
FROM customers c
RIGHT JOIN sales s on c.customer_id = s.customer_id
-- 가운데 교집합 부분이 2번 들어간다
WHERE c.customer_id IS NULL;
-- 결과적으로 A U (B n Ac) = A U B
-- postgreSQL이나 다른 좋은거 쓰면 한번에 쓸 수 있음