USE lecture;
SELECT * FROM sales;

-- COUNT() : 개수
-- 보통 NULL 값이 있는 경우가 있어 id(id 개수) 혹은 *(전체 개수)을 쓴다.
SELECT COUNT(id) AS 매출건수 FROM sales;

SELECT
	COUNT(*) AS 총주문건수,
    COUNT(DISTINCT customer_id) AS 고객수, -- DISTINCT -> 중복 제거
    COUNT(DISTINCT product_name) AS 제품수
FROM sales;

-- SUM() : 총합
SELECT
	FORMAT(SUM(total_amount),0) AS 총매출액, 							-- FORMAT(큰 수, 소수점) : 3자리마다 콤마를 찍어 보기 쉽게 함
    SUM(quantity) AS 총판매수량,
	FORMAT(SUM(IF(region = '서울', total_amount,0)),0) AS 서울매출, 	-- IF를 이용해서 특정 값만 뽑아 쓸 수 있는데, 이러면 서울이 아니면 0값을 줘서 더해버린 셈임 -> 최적화면에서 별로 좋진 않음
	FORMAT(															-- SUM 안쪽이 길어지면 엔터로 구분해주는 게 좋다
		SUM(
			IF(
				category = '전자제품',total_amount,0
			)
        ),0
	) AS 전자매출

FROM sales;


-- AVG() -> 평균
SELECT
    FORMAT(AVG(total_amount),0)							 AS 평균매출액,
	FORMAT(SUM(total_amount) / COUNT(total_amount),0)	 AS 평균매출액2,
    FORMAT(AVG(quantity),0) 							 AS 평균판매수량,
    FORMAT(AVG(unit_price),0)							 AS 평균단가
FROM sales;

-- MIN / MAX
SELECT
	MIN(total_amount)	 AS 최소매출액,
    MAX(total_amount)	 AS 최대매출액,
    MIN(order_date)		 AS 첫주문일,
    MAX(order_date)		 AS 마지막주문일
FROM sales;

