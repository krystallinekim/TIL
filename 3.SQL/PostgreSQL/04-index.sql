-- INDEX 조회
SELECT
	tablename,
	indexname,
	indexdef
FROM pg_indexes
WHERE tablename IN ('large_orders', 'large_customers');


ANALYZE large_orders;
ANALYZE large_customers;

-- 캐시 날리기(실제로 쓰면 안됨)
SELECT pg_stat_reset();


-- 행 하나를 보기 위해 10만행을 봐야함
EXPLAIN ANALYZE
SELECT * FROM large_orders
WHERE customer_id = 'CUST-025000';
-- time 107.644 ms
-- >> INDEXING 후 time 0.108ms
-- 효율성이 말도 안되게 늘어남
-- 검색 방식도 바뀜. Bitmap Index Scan


EXPLAIN ANALYZE
SELECT COUNT(*) FROM large_orders WHERE region = '서울'
-- time 100ms
-- >> time 11.702ms
-- 인덱싱은 카테고리화될 수 있는 것들(region, customer_id 등) 에 매우매우 효과적


-- 전체의 20%를 보기 위해 전체 데이터를 봐야함
EXPLAIN ANALYZE
SELECT * FROM large_orders
WHERE amount BETWEEN 800000 AND 1000000;
-- time 152.612ms
-- >> time 293.284ms
-- 인덱싱을 하면 더 안좋아질 수도 있음
-- 주로 가져올 행 수가 많거나, 카테고리화 못하는 컬럼에 인덱싱을 할 경우에 안좋음
-- 모든 컬럼(*)을 가져올 때도 느림


-- INDEX 만들기
CREATE INDEX idx_orders_customer_id ON large_orders(customer_id);
CREATE INDEX idx_orders_amount ON large_orders(amount);
CREATE INDEX idx_orders_region ON large_orders(region);



-- 복합 인덱스
-- 두개 이상의 컬럼에 따로 인덱스를 넣고 SELECT를 돌리면 느림
-- 둘을 한번에 인덱싱해서 돌리면 빠름

CREATE INDEX idx_orders_region_amount ON large_orders(region, amount);

EXPLAIN ANALYZE
SELECT * FROM large_orders
WHERE region = '서울'
ORDER BY amount DESC
LIMIT 100;
-- time 106.626ms
-- >> time 0.376ms
-- > 시간이 훨씬 빨라짐


-- 복합 인덱스의 순서
-- region-amount와 amount-region은 다른 인덱스
-- 심지어 속도도 다름

CREATE INDEX idx_orders_amount_region ON large_orders(amount, region);

SELECT
	indexname,
	pg_size_pretty(pg_relation_size(indexname::regclass)) AS index_size
FROM pg_indexes
WHERE tablename = 'large_orders'
AND indexname LIKE '%region%amount%' OR indexname LIKE '%amount%region%'

-- 인덱스 순서의 가이드라인
-- 고유값의 비율

SELECT COUNT(DISTINCT region) AS 고유지역수, COUNT(*) AS 전체행수 FROM large_orders;
-- DISTINCT 개수가 매우 작다 --> 선택도(고유값 비율)가 매우 작다 (거의 0%)

SELECT COUNT(DISTINCT amount) AS 고유금액수, COUNT(*) AS 전체행수 FROM large_orders;
-- 이번엔 선택도가 거의 99%

SELECT COUNT(DISTINCT customer_id) AS 고유고객수, COUNT(*) AS 전체행수 FROM large_orders;
-- 약 5%

-- 일반적으로 선택도가 높은(amount -> region 순서) 것부터 인덱싱하는게 맞다