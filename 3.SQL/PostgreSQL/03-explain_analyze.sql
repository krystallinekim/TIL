-- 실행 계획(예상)
EXPLAIN
SELECT * FROM large_customers WHERE customer_type = 'VIP';

-- "Seq Scan on large_customers  (cost=0.00..3746.00 rows=10190 width=160)"
-- cost는 비용, 1개의 행일때 비용이 0, 마지막 행까지 가져오는데는 비용이 3746이라는 것 --> 최적화가 좋다는 것은 cost가 낮다는 것
-- 코드 자체는 답만 나오면 좋지만, 더 좋은 코드를 보여주는 수단으로 볼 수 있다.
-- rows는 실제 반환될 행수(예상) --> 10190행을 가져온다
-- width는 행의 넓이(byte) --> 160byte
-- 실제 메모리를 얼마나 쓸 지 알 수 있다. 행당 160바이트 * 10190행

-- "  Filter: (customer_type = 'VIP'::text)"



-- 실행 + 통계
EXPLAIN ANALYZE 
SELECT * FROM large_customers WHERE customer_type = 'VIP';
-- "Seq Scan on large_customers  (cost=0.00..3746.00 rows=10190 width=160) (actual time=0.016..7.909 rows=9997 loops=1)"
-- Seq Scan: 인덱스가 없고 / 테이블 대부분의 행을 읽어야 하고 / 순차 스캔이 빠를 때 자동으로 시퀀셜 스캔을 돌림
-- actual time은 실제 걸린 시간(ms) --> 1행이 0.016ms > 마지막 행까지 7.909ms
-- actual rows는 실제로 나온 결과 --> 앞에껀 그냥 예상치였는데, 비슷하다는 게 신기

-- "  Filter: (customer_type = 'VIP'::text)"

-- "  Rows Removed by Filter: 90003"
-- 필터로 걸러진 행수

-- "Planning Time: 0.075 ms"

-- "Execution Time: 8.077 ms"

-- 버퍼 사용량 포함(임시 메모리 공간)
-- 진짜 자주 쓰는 데이터들을 저장하는 곳
EXPLAIN (ANALYZE, BUFFERS)
SELECT * FROM large_customers WHERE loyalty_points > 8000;

-- "Seq Scan on large_customers  (cost=0.00..3746.00 rows=20061 width=160) (actual time=0.011..7.127 rows=20223 loops=1)"
-- "  Filter: (loyalty_points > 8000)"
-- "  Rows Removed by Filter: 79777"

-- "  Buffers: shared hit=2496"
-- "Planning:"
-- "  Buffers: shared hit=3"
-- 버퍼를 얼마나 썼는지, 계획에선 얼마나 쓸 것이라 생각했는지 등을 보여줌

-- "Planning Time: 0.104 ms"
-- "Execution Time: 7.516 ms"

-- 상세 정보 포함
EXPLAIN (ANALYZE, VERBOSE, BUFFERS)
SELECT * FROM large_customers WHERE loyalty_points > 8000;
-- "Seq Scan on public.large_customers  (cost=0.00..3746.00 rows=20061 width=160) (actual time=0.014..6.805 rows=20223 loops=1)"
-- "  Output: customer_id, customer_name, age, customer_type, join_date, preferred_categories, communication_preference, loyalty_points, additional_info"
-- VERBOSE는 수다쟁이라는 뜻 --> 상세정보(컬럼명 등)을 볼 수 있음

-- "  Filter: (large_customers.loyalty_points > 8000)"
-- "  Rows Removed by Filter: 79777"
-- "  Buffers: shared hit=2496"
-- "Planning Time: 0.054 ms"
-- "Execution Time: 7.199 ms"


-- JSON 형태
EXPLAIN (ANALYZE, VERBOSE, BUFFERS, FORMAT JSON)
SELECT * FROM large_customers WHERE loyalty_points > 8000;
-- 이럼 진짜 json으로 다운받을 수 있다.

-- 진단
EXPLAIN ANALYZE
SELECT 
	c.customer_name,
	COUNT(o.order_id)
FROM large_customers c
LEFT JOIN large_orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_name;