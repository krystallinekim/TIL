USE lecture;

-- 카테고리별 데이터 (피벗테이블 행 = 카테고리, 값 = 매출액)
SELECT
	category			AS 카테고리,
    COUNT(*)			AS 주문건수,
    SUM(total_amount)	AS 매출액,
    AVG(total_amount)	AS 평균매출
FROM sales
GROUP BY category
ORDER BY 매출액 DESC;
-- 카테고리 별로 그룹화 해서 그룹별로 주문건수/총매출액/평균매출을 보는 것
-- 최종 결과를 완전히 새로운 테이블을 만들어서 보여준다(지금까지는 원래 있던 테이블을 잘라서 만들거나 옆에 추가해서 만들었었음)

-- 지역별 매출 분석
SELECT
	region AS 지역,
    COUNT(*) AS 주문건수,
    COUNT(DISTINCT customer_id) AS 고객수,
    ROUND(COUNT(*) / COUNT(DISTINCT customer_id),2) AS 고객당_주문수,
	ROUND(
		SUM(total_amount) / COUNT(DISTINCT customer_id)
	) AS 고객당_평균매출액,
	SUM(total_amount) AS 총매출액,
    ROUND(AVG(total_amount)) AS 평균매출액
FROM sales
GROUP BY region
ORDER BY 총매출액 DESC;

-- 다중 Grouping
-- 두개 이상의 그룹도 지정 가능함
-- ORDER BY로 기준 2개 잡아서 보는것도 좋음

-- 지역, 카테고리에 따른 총매출액
SELECT
	region AS 지역,
    category AS 카테고리,
    COUNT(*) AS 주문건수,
    SUM(total_amount) AS 총매출액,
    ROUND(AVG(total_amount)) AS 평균매출액
FROM sales
GROUP BY region, category
ORDER BY 지역, 총매출액 DESC;

-- 영업사원당 월별 성과
SELECT
	DATE_FORMAT(order_date, '%Y-%m') AS 월,
	sales_rep AS 영업사원,
	COUNT(*) AS 주문건수,
    SUM(total_amount) AS 월매출액
FROM sales
GROUP BY sales_rep, DATE_FORMAT(order_date, '%Y-%m')
ORDER BY 월, 월매출액 DESC;

-- MAU(월별 활성 이용자수)
SELECT
	DATE_FORMAT(order_date, '%Y-%m') AS 월,
    SUM(total_amount) AS 월매출액,
    COUNT(*) AS 주문건수,
    COUNT(DISTINCT customer_id) AS MAU
FROM sales
GROUP BY DATE_FORMAT(order_date, '%Y-%m');


-- 요일별 매출 패턴
SELECT
-- 	DAYOFWEEK(order_date) AS 요일번호
	DAYNAME(order_date) AS 요일,
	SUM(total_amount) AS 매출,
    COUNT(*) AS 주문건수
FROM sales
GROUP BY DAYNAME(order_date)  -- , DAYOFWEEK(order_date)
ORDER BY 매출 DESC;

-- GROUP BY로 그룹을 묶을 때, 요일번호 컬럼은 더하거나 셀 수 있는 집계 컬럼이 아니기 때문에 에러가 난다. 따라서, GROUP BY로 요일번호를 묶어주던가, 아니면 아예 요일번호 컬럼을 없애서 요일로만 집계하고, 매출과 주문건수를 이에 따라 합산하는 식으로 묶어야 함