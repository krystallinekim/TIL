-- 같은 테이블을 이용해 JOIN을 하는 경우

-- id가 1 차이나면 적은 사람이 상사, 많은사람이 부하라고 가정
SELECT 
	상사.name AS 상사명,
    부하.name AS 부하명
FROM employees 상사
LEFT JOIN employees 부하 ON 부하.id = 상사.id +1;

-- 고객 간 구매 패턴 유사성
-- 손님1, 손님2, 공통구매카테고리 수, 공통카테고리 이름
SELECT
	c1.customer_name AS 고객1,
    c2.customer_name AS 고객2,
	COUNT(DISTINCT s1.category) AS 공통카테고리수,
    GROUP_CONCAT(DISTINCT s1.category) AS 공통카테고리
-- 1번 손님의 구매데이터
FROM customers c1
INNER JOIN sales s1 ON s1.customer_id = c1.customer_id
-- 2번 손님의 구매데이터 -> 1번 id와 다른 사람을 고름(같은 사람끼리 / 이미 비교한 사람 다시 고르는 경우 제거)
INNER JOIN customers c2 ON c1.customer_id < c2.customer_id
-- 모든 경우의 수 중에 s1과 s2에 공통카테고리가 있는 경우만
INNER JOIN sales s2 ON s2.customer_id = c2.customer_id AND s1.category = s2.category
GROUP BY c1.customer_name, c2.customer_name
ORDER BY 공통카테고리수 DESC
