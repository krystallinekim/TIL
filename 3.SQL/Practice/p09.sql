USE practice;

SELECT COUNT(*) FROM sales
UNION
SELECT COUNT(*) FROM customers;

SELECT * FROM customers c
JOIN sales s ON c.customer_id = s.customer_id;

-- 예제 1: 건당 거래액이 가장 높은 10건의 고객명, 상품명, 주문금액 
SELECT
	c.customer_name AS 고객명,
    s.product_name AS 상품명,
    s.total_amount AS 주문금액
FROM customers c
INNER JOIN sales s ON c.customer_id = s.customer_id
ORDER BY s.total_amount DESC
LIMIT 10;

-- 예제 2: 고객 유형별 고객유형, 주문건수, 평균주문금액을 평균주문금액이 높은 순으로 정렬
SELECT
	c.customer_type AS 고객유형,
    COUNT(*) AS 고객유형별_주문건수,
    ROUND(AVG(s.total_amount)) AS 고객유형별_평균주문금액
FROM customers c
INNER JOIN sales s ON c.customer_id = s.customer_id
GROUP BY c.customer_type
ORDER BY 고객유형별_평균주문금액 DESC;
-- INNER/LEFT는 언제 써야 하는가
-- 답은 없지만, 위와 같은 경우 LEFT는 평균을 크게 오염시킬 가능성이 있어 INNER가 더 적합할 수 있음

-- 문제 1: 모든 고객의 이름과 구매한 상품명 조회
SELECT
	c.customer_name AS 고객명,
    COALESCE(s.product_name,'❌') AS 상품명
FROM customers c
LEFT JOIN sales s ON c.customer_id = s.customer_id
ORDER BY 고객명;

-- 문제 2: 고객 정보와 주문 정보를 모두 포함한 상세 조회
SELECT
	c.customer_name AS 고객명,
    c.customer_type AS 고객유형,
    c.join_date AS 가입일,
    s.product_name AS 상품명,
    s.category AS 카테고리,
    s.quantity AS 수량,
    s.total_amount AS 주문금액,
    s.sales_rep AS 담당자,
    s.region AS 지역
FROM customers c
JOIN sales s ON c.customer_id = s.customer_id;

-- 문제 3: VIP 고객들의 구매 내역만 조회
SELECT
    c.customer_type AS 고객유형,
	c.customer_name AS 고객명,
    c.join_date AS 가입일,
    s.product_name AS 상품명,
    s.category AS 카테고리,
    s.quantity AS 수량,
    s.total_amount AS 주문금액,
    s.sales_rep AS 담당자,
    s.region AS 지역
FROM customers c
INNER JOIN sales s ON c.customer_id = s.customer_id
WHERE c.customer_type = 'VIP'
ORDER BY 주문금액 DESC;

-- 문제 4: 50만원 이상 주문한 기업 고객들
SELECT
	DISTINCT c.customer_name
FROM customers c
INNER JOIN sales s ON c.customer_id = s.customer_id
WHERE
	c.customer_type = '기업'
    AND
    total_amount >= 500000;
-- 고객 별 분석이 하고싶으면 GROUP BY를 써도 됨

-- 문제 5: 2024년 하반기(7월~12월) 전자제품 구매 내역
SELECT
	s.order_date AS 주문일,
    s.category AS 카테고리,
    s.product_name AS 상품명,
    s.quantity AS 수량,
    s.total_amount AS 주문금액,
    s.sales_rep AS 담당자,
    s.region AS 지역,
	c.customer_name AS 고객명,
    c.customer_type AS 고객유형,
    c.join_date AS 가입일
FROM customers c
JOIN sales s ON c.customer_id = s.customer_id
WHERE
	(MONTH(s.order_date) BETWEEN 7 AND 12)
 	AND
	s.category = '전자제품';

-- 문제 6: 고객별 주문 통계 (INNER JOIN) [고객명, 유형, 주문횟수, 총구매, 평균구매, 최근주문일]
SELECT
	c.customer_name AS 고객명,
    c.customer_type AS 고객유형,
    COUNT(*) AS 주문횟수,
    SUM(s.total_amount) AS 총구매,
    ROUND(AVG(s.total_amount)) AS 평균구매,
    MAX(s.order_date) AS 최근주문일
FROM customers c
JOIN sales s ON c.customer_id = s.customer_id
GROUP BY 고객명, 고객유형;

-- 문제 7: 모든 고객의 주문 통계 (LEFT JOIN) - 주문 없는 고객도 포함
SELECT
	c.customer_name AS 고객명,
    c.customer_type AS 고객유형,
    COUNT(s.id) AS 주문횟수,
    COALESCE(SUM(s.total_amount),0) AS 총구매,
    COALESCE(ROUND(AVG(s.total_amount)),0) AS 평균구매,
    COALESCE(MAX(s.order_date),'주문없음') AS 최근주문일
FROM customers c
LEFT JOIN sales s ON c.customer_id = s.customer_id
GROUP BY c.customer_name, c.customer_type
ORDER BY 고객명;

-- 문제 8: 카테고리별 고객 유형 분석
SELECT
	s.category AS 카테고리,
    c.customer_type AS 고객유형,
    COUNT(DISTINCT c.customer_id) AS 고객수,
    COUNT(*) AS 주문수,
    SUM(s.total_amount) AS 주문_총액
FROM customers c
JOIN sales s ON c.customer_id = s.customer_id
GROUP BY 카테고리, 고객유형;

-- 문제 9: 고객별 등급 분류
-- 활동등급(구매횟수) : [0(잠재고객) < 브론즈 < 3 <= 실버 < 5 <= 골드 < 10 <= 플래티넘]
-- 구매등급(구매총액) : [0(신규) < 일반 <= 10만 < 우수 <= 20만 < 최우수 < 50만 <= 로얄]
SELECT
	c.customer_name,
    CASE
		WHEN COUNT(s.id) >= 10 	THEN '플래티넘'
        WHEN COUNT(s.id) >= 5 	THEN '골드'
        WHEN COUNT(s.id) >= 3 	THEN '실버'
        WHEN COUNT(s.id) >= 1 	THEN '브론즈'
        ELSE '잠재고객'
	END AS 활동등급,
    CASE
		WHEN SUM(s.total_amount) >= 500000 	THEN '👑로얄'
        WHEN SUM(s.total_amount) >= 200000 	THEN '❤️최우수'
        WHEN SUM(s.total_amount) >= 100000 	THEN '🟡우수'
        WHEN SUM(s.total_amount) > 0 		THEN '🚫일반'
        ELSE '신규'
	END AS 구매등급
FROM customers c
LEFT JOIN sales s ON c.customer_id = s.customer_id
GROUP BY c.customer_name;

-- 문제 10: 활성 고객 분석
-- 고객상태(최종구매일) [NULL(구매없음) | 활성고객 <= 30 < 관심고객 <= 90 < 휴면고객]별로
-- 고객수, 총주문건수, 총매출액, 평균주문금액 분석

-- 전체 고객에게 상태 부여하는 서브쿼리
SELECT
	c.customer_id,
	CASE
		WHEN DATEDIFF('2024-12-31', MAX(s.order_date)) > 90 THEN '휴면고객'
		WHEN DATEDIFF('2024-12-31', MAX(s.order_date)) > 30 THEN '관심고객'
		WHEN DATEDIFF('2024-12-31', MAX(s.order_date)) <= 30 THEN '활성고객'
		ELSE '구매없음'
	END AS 고객상태
FROM customers c
LEFT JOIN sales s ON c.customer_id = s.customer_id
GROUP BY c.customer_id;

-- 테이블 3개(고객정보, 판매정보, 고객상태)를 합친 후 고객상태에 따라 분류
SELECT
	ct.고객상태,
    COUNT(DISTINCT c.customer_id) AS 고객수,
    COUNT(s.id) AS 총주문건수,
    COALESCE(SUM(s.total_amount),0) AS 총매출액,
    COALESCE(ROUND(AVG(s.total_amount)),0) AS 평균주문금액
FROM customers c
LEFT JOIN sales s ON c.customer_id = s.customer_id
JOIN
	(SELECT
		c.customer_id,
		CASE
			WHEN DATEDIFF('2024-12-31', MAX(s.order_date)) > 90 THEN '휴면고객'
			WHEN DATEDIFF('2024-12-31', MAX(s.order_date)) > 30 THEN '관심고객'
			WHEN DATEDIFF('2024-12-31', MAX(s.order_date)) <= 30 THEN '활성고객'
			ELSE '구매없음'
		END AS 고객상태
	FROM customers c
	LEFT JOIN sales s ON c.customer_id = s.customer_id
	GROUP BY c.customer_id
    ) ct ON c.customer_id = ct.customer_id
GROUP BY 고객상태
ORDER BY FIELD(고객상태,'활성고객','관심고객','휴면고객','구매없음');
