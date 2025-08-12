-- EXISTS (~한 적이 있는~)
-- 사실 IN으로 전부 처리 가능함
-- 하지만 처리시간이 exist가 훨씬 빠름
USE lecture;

-- 전자제품을 구매한 고객정보
SELECT DISTINCT customer_id FROM sales WHERE category = '전자제품';

-- 일반적인 IN을 이용한 방법
SELECT
	*
FROM customers
-- sales 안의 모든 customer_id를 뽑아옴 -> customer_id와 일일이 대조 --> 매우 오래걸린다
WHERE customer_id IN (SELECT DISTINCT customer_id FROM sales WHERE category = '전자제품');


-- EXISTS를 쓰면?
SELECT
	*
FROM customers c
-- sales에서 카테고리가 전자제품을 산 사람들이면 TRUE
WHERE EXISTS (SELECT 1 FROM sales s WHERE s.customer_id = c.customer_id AND s.category = '전자제품');
-- >> 결과는 똑같음
-- >> 데이터를 어딘가에 뷸러오는 개념이 아닌, 바로 비교하면서 있으면 바로 검색도 멈춰줌 --> 최적화 측면에서 매우 유리
-- >> 있나 없나를 확인한다면, EXISTS가 훨씬 좋다

-- 전자제품과 의류를 모두 구매해 본 이력이 있고, 50만원 이상 구매도 해본 고객을 찾자
SELECT
	c.customer_name,
    c.customer_type
FROM customers c
WHERE 
    -- 전자제품 구매 이력 있음
    EXISTS(SELECT 1 FROM sales s1 WHERE s1.customer_id = c.customer_id AND s1.category = '전자제품') AND
    -- 의류 구매 이력 있음
    EXISTS(SELECT 1 FROM sales s2 WHERE s2.customer_id = c.customer_id AND s2.category = '의류') AND
    -- 50만원 이상
    EXISTS(SELECT 1 FROM sales s3 WHERE s3.customer_id = c.customer_id AND s3.total_amount >= 500000);

-- NOT EXISTS --> 한번도 팔린적 없는 상품을 찾아볼 때
-- >> IN으로는 매우 복잡하다