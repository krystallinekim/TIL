USE lecture;
-- 사전설명
-- 테이블에 AS 붙이기
-- 고객정보와 주문정보를 한 번에 보고 싶을때 (테이블이 customers, sales 나뉘어 있음) --> 테이블 2개를 JOIN

-- 다른 테이블의 데이터와 지금 테이블의 데이터를 같이 보고 싶다면
-- 간단한 경우는 서브쿼리를 써서 볼 수 있다
SELECT
    *,
    (
        SELECT customer_name FROM customers
        WHERE customers.customer_id = sales.customer_id
    ) AS customer_name,
-- customers 계속 쓰기 귀찮으니 c로 줄여버릴 수도 있다. FROM 뒤 테이블 이름 뒤에 줄임말을 붙여주면 됨.
    (
        SELECT customer_type FROM customers c
        WHERE c.customer_id = s.customer_id
    ) AS customer_type
FROM sales s;
-- 더 많은 데이터를 가져오거나 코드가 길어질수록 같은 내용이 반복되고 귀찮고 실수할 확률도 커진다

-- 이럴 때 쓰기 좋은 것이 JOIN
SELECT *, c.customer_name, c.customer_type FROM customers c
JOIN sales s ON c.customer_id = s.customer_id;
-- 위쪽 서브쿼리를 쓴 복잡한 코드를 매우 간단하고 편하게 가져온 것이 JOIN


SELECT 
    c.customer_name,
    c.customer_type
FROM customers c
INNER JOIN sales s ON c.customer_id = s.customer_id
WHERE s.total_amount >= 500000
ORDER BY s.total_amount DESC;
-- JOIN 쓴 테이블에서도 WHERE나 ORDER BY, GROUP BY와 HAVING 모두 잘 적용됨


-- JOIN의 또 다른 장점 
-- 모든 고객의 구매 현황 분석(구매를 하지 않았어도 분석하고 싶을 때)


-- JOIN 없는 상태 -> 원본 테이블 안쪽 데이터만 분석 가능 --> customer_id만 보고는 정보가 부족하다
-- subquery를 써서 외부 데이터를 가져올 수는 있지만, 코드가 너무 길어지고, 많은 데이터를 가져오기도 길고 현학적이 된다.
SELECT
	customer_id,
    (SELECT customer_name FROM customers WHERE sales.customer_id = customers.customer_id) AS 고객명,
    (SELECT customer_type FROM customers WHERE sales.customer_id = customers.customer_id) AS 고객종류,
    COUNT(*) AS 주문횟수,
	SUM(total_amount) AS 총구매액,
	CASE 
        WHEN COUNT(*) > 5 THEN '충성고객'
        WHEN COUNT(*) > 3 THEN '일반고객'
		WHEN COUNT(*) > 0 THEN '신규고객'
		ELSE '잠재고객'
	END AS 활성도
FROM sales
GROUP BY customer_id;
-- 그런데 결과를 잘 보면, 이 테이블에서는 잠재고객을 절대 찾을 수 없다. -> 구매한 사람들만 대상으로 데이터를 정리했기 때문
-- 마치 오늘 결석한 사람 손들라고 하는 것과 비슷


-- JOIN을 통해 외부 테이블의 데이터를 같이 분석 가능함
SELECT
    c.customer_id,
	c.customer_name AS 고객명,
    c.customer_type AS 고객종류,
    COUNT(s.id) AS 주문횟수,
	COALESCE(SUM(s.total_amount),0) AS 총구매액,	-- 총구매액이 NULL인 사람들에게 0값을 주기 위해 coalesce를 썼음
    CASE 
        WHEN COUNT(s.id) > 5 THEN '충성고객'
        WHEN COUNT(s.id) > 3 THEN '일반고객'
		WHEN COUNT(s.id) > 0 THEN '신규고객'
        ELSE '잠재고객'
	END AS 활성도
FROM customers c
LEFT JOIN sales s ON c.customer_id = s.customer_id
GROUP BY c.customer_id, c.customer_name, c.customer_type;
-- LEFT JOIN을 써서 아무것도 구매 안한 잠재고객도 분석 가능해짐



-- JOIN 종류

SELECT
    *
FROM customers c
LEFT JOIN sales s ON c.customer_id = s.customer_id
WHERE s.id IS NULL;

-- ON 뒤에 붙이는 것에 따라 어떻게 붙이는지가 달라짐
-- 조건 없이 붙이면(JOIN sales s;) customers-sales를 진짜 모두 붙이기만 함(customers 50줄 * sales 120줄 = 6000줄)
-- 예시의 경우는 customer_id가 매칭되는 데이터를 붙임

-- JOIN의 종류(JOIN 앞에 붙을 수 있는 것들)
-- 벤 다이어그램(원 2개짜리)으로 이해 가능, 원에서 칠하고 싶은 부분을 JOIN으로 정리할 수 있다.
-- LEFT JOIN : 왼쪽 테이블(c) 와 매칭되는 데이터 + 매칭되는 오른쪽 데이터 -> 오른쪽 데이터에 결과가 없어도 붙여줌 (A)
-- LEFT JOIN + WHERE b.key IS NULL : 한번도 주문한적 없는 사람이 나옴 (A∩Bᶜ)
-- (INNER) JOIN : 왼쪽 테이블(c)와 오른쪽 테이블(s) 둘 다 존재하는 것만 붙일 수 있음 (A∩B)
