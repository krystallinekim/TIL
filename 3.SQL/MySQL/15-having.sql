USE lecture;

-- 총 매출이 천만원 이상인 카테고리 찾기
SELECT
	category,
    COUNT(*) AS 주문건수,
    SUM(total_amount) AS 총매출액
FROM sales
-- WHERE total_amount >= 1000000		-- 건당 10만원 이상의 건들만 가지고 / total_amount를 계산		-> 이미 원본데이터에 필터링을 걸고 그룹핑
GROUP BY category
HAVING SUM(total_amount) >= 10000000;	-- 총 천만원 이상이 나오는 데이터를 가지고 / total_amount를 계산 	-> 그룹핑한 피벗 테이블의 결과에 필터링을 거는 것

-- WHERE과 HAVING은 쓰는 용도가 다름


-- 활성 지역 찾기 (주문건수 >= 20, 고객수 >= 15)
SELECT
	region AS 지역,
    COUNT(*) AS 지역별_주문건수,
    COUNT(DISTINCT customer_id) AS 지역별_고객수,
    SUM(total_amount) AS 지역별_매출액,
	ROUND(AVG(total_amount)) AS 지역별_평균주문액
FROM sales
GROUP BY region
HAVING 지역별_주문건수 >= 20 AND 지역별_고객수 >= 15;

-- 우수 영업사원 찾기, 월평균 매출액이 50만원 이상
SELECT
	sales_rep AS 영업사원,
	COUNT(*) AS 사원별_판매건수,
    COUNT(DISTINCT customer_id) AS 사원별_고객수,
    SUM(total_amount) AS 사원별_매출액,
-- 	COUNT(DISTINCT DATE_FORMAT(order_date, '%Y-%m')) AS 활동개월수,
 	COUNT(DISTINCT MONTH(order_date)) AS 활동개월수,
    ROUND(
		SUM(total_amount) / 
        COUNT(DISTINCT MONTH(order_date))
	) AS 월평균_매출액
FROM sales
GROUP BY sales_rep
HAVING 월평균_매출액 >= 5 * POWER(10,5)
ORDER BY 월평균_매출액 DESC;
