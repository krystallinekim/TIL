USE lecture;

SELECT * FROM sales;
SELECT * FROM customers; 

-- 각 카테고리 평균매출 중에서 50만원 이상만 구하기 (HAVING 사용)
SELECT
	category AS 카테고리,
    ROUND(AVG(total_amount)) AS 평균매출
FROM sales
GROUP BY category
HAVING 평균매출 >= 500000;
-- 가능은 한데, HAVING은 그룹일 때만 쓸 수 있다 --> 피벗 테이블일 때만 사용할 수 있다는 것

-- HAVING 안쓰고 서브쿼리로
SELECT
	category AS 카테고리,
    ROUND(AVG(total_amount)) AS 평균매출
FROM sales
GROUP BY category;

-- 위 테이블을 가지고 더 다양한 걸 할 수 있다
SELECT
	*
FROM (
	SELECT
		category AS 카테고리,
		ROUND(AVG(total_amount)) AS 평균매출
	FROM sales
	GROUP BY category
) AS category_summary
WHERE 평균매출 >= 500000;
-- 이건 SELECT * FROM category_summary와 같은것임
-- 가상의 category_summary라는 테이블을 만든 것

-- FROM 뒤에 쓸 수 있다는게 중요
-- 데이터 양이 많을 때 잘라서 넣는 등
-- 테이블을 잘라서 작게 보는 것이 View임(InLine view)

-- 1. 카테고리별 매출 분석 후 필터링 -> 카테고리명, 주문건수, 총매출, 평균매출, 평균매출(0<저단가<400000<중단가<800000<고단가) 

SELECT
	category AS 카테고리명,
    COUNT(*) AS 주문건수,
    SUM(total_amount) AS 총매출,
    AVG(total_amount) AS 평균매출,
    CASE
		WHEN AVG(total_amount) > 800000 THEN '고단가'
        WHEN AVG(total_amount) > 400000 THEN '중단가'
        WHEN AVG(total_amount) > 0 		THEN '저단가'
        ELSE '0'
    END AS 단가구분
FROM sales
GROUP BY category;

-- 위 방식은 평균 매출을 계속 계산해 줘야함. 그냥 '평균매출'이라는 말로 쓸 수는 없나?
-- 서브쿼리
SELECT
	category AS 카테고리명,
    COUNT(*) AS 주문건수,
    SUM(total_amount) AS 총매출,
    ROUNT(AVG(total_amount)) AS 평균매출
FROM sales
GROUP BY category;

-- 이제 평균매출은 "평균매출"로 가져다 쓸 수 있다.

SELECT
	*,
	CASE
		WHEN 평균매출 > 800000 THEN '고단가'
		WHEN 평균매출 > 400000 THEN '중단가'
		WHEN 평균매출 > 0 THEN '저단가'
		ELSE '0'
	END AS 단가구분
FROM(
	SELECT
		category AS 카테고리명,
		COUNT(*) AS 주문건수,
		SUM(total_amount) AS 총매출,
		ROUND(AVG(total_amount)) AS 평균매출
	FROM sales
	GROUP BY category
)AS category_analysis;

-- IN-Line View = 코드 안에 있다
-- 아예 많이 쓰는 테이블을 밖에 따로 View로 저장할 수도 있음 --> Schemas에서 Views가 있음


-- 영업사원별 성과 등급 분류 --> 영업사원, 총매출액, 평균매출액, 주문건수, 주문등급(주문건수 0 <= C < 15 <= B < 30 <= A < 50 <= S), 매출등급(총매출 0 < C < 백만 < B < 3백만 < A < 5백만 < S)
SELECT
	sales_rep AS 영업사원,
    SUM(total_amount) AS 총매출액,
    ROUND(AVG(total_amount)) AS 평균매출액,
    COUNT(*) AS 주문건수
FROM sales
GROUP BY sales_rep;

SELECT
	영업사원,
    총매출액,
    CASE
		WHEN 총매출액 >= 50000000 THEN 'S'
        WHEN 총매출액 >= 30000000 THEN 'A'
        WHEN 총매출액 >= 10000000 THEN 'B'
        ELSE 'C'
    END AS 매출등급,
    평균매출액,
    주문건수,
    CASE
		WHEN 주문건수 >= 25 THEN 'S'
        WHEN 주문건수 >= 20 THEN 'A'
        WHEN 주문건수 >= 15 THEN 'B'
        ELSE 'C'
    END AS 주문등급
FROM (
	SELECT
		sales_rep AS 영업사원,
		SUM(total_amount) AS 총매출액,
		ROUND(AVG(total_amount)) AS 평균매출액,
		COUNT(*) AS 주문건수
	FROM sales
	GROUP BY sales_rep
) AS rep_anal
ORDER BY 평균매출액 DESC;