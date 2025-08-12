USE lecture;

SELECT * FROM sales;

-- 매출 평균보다 높은 금액을 주문한 판매데이터
SELECT
  *
FROM sales
WHERE total_amounts > AVG(total_amounts);
-- 이건 작동하지 않는 함수임
-- 집계함수는 여기서 안돌아간다

-- 이걸 작동시키려면 
-- 1. 평균을 구해서 기억해 놓고
SELECT AVG(total_amounts) FROM sales;

-- 2. 그 값을 이용해서 SELECT를 구하면 된다
SELECT * FROM sales WHERE total_amounts > 288375;

-- 이걸 한번에 한줄에 작성 가능 -> 서브쿼리
SELECT * FROM sales WHERE total_amounts > (SELECT AVG(total_amounts) FROM sales);
-- 괄호 안에 평균 구하는 서브쿼리를 넣어주는 형식

-- 그룹핑과는 보고싶은게 다르다. "집계함수를 통해 구한 값" 을 가지고 원래 데이터와 비교할 때 사용하는 기능
SELECT
    product_name,
    total_amount AS 판매액수,
    total_amount - (SELECT AVG(total_amount) FROM sales) AS 편차
FROM sales
-- 판매가가 평균보다 높음
WHERE total_amount > (SELECT AVG(total_amount) FROM sales);

-- 데이터가 여러 개 나오는 경우
SELECT * FROM sales;
-- 데이터가 하나 나오는 경우
SELECT AVG(total_amount) FROM sales;

-- 서브쿼리도 쿼리임. 이거처럼 특정 값 하나만 나오는 케이스 / 데이터가 여러 개 나오는 케이스가 존재함 --> 쓰임새가 다름

-- sales에서 (가장 비싼 주문)을 한 주문
SELECT * FROM sales
WHERE total_amount = (
    SELECT MAX(total_amount) FROM sales
);
    
-- 사실 서브쿼리 없이 같은 결과를 낼 수 없지는 않음
SELECT * FROM sales
ORDER BY total_amount DESC LIMIT 1;
-- 그런데 하는 작용, 최적화 모든 게 다른 작업. 실제로 결과도 다르게 나올 수 있음
-- 데이터 숫자가 커진다면, 몇억 개의 데이터를 전부 정렬시키고 그 중 하나만 뽑아오는거라 매우 시간이 오래 걸림
-- 서브쿼리를 통해 최대값 한번만 구한다면, 굳이 계산을 여러번 할 필요 없다.


-- (가장 최근 주문일)의 주문데이터
SELECT * FROM sales
WHERE order_date = (
    SELECT MAX(order_date) FROM sales
);

-- 가장 (주문액수 평균)과 유사한 주문데이터 5개 = 편차가 가장 작은 5개
SELECT
    *,
    ABS(
        total_amount 
        - 
        (SELECT AVG(total_amount) FROM sales)
    ) AS 편차
FROM sales
ORDER BY 편차
LIMIT 5;