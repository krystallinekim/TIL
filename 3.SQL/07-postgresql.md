# PostgreSQL


## PostgreSQL vs MySQL

### 철학 및 사용 목적

| 구분   | MySQL                  | PostgreSQL                     |
|--------|------------------------|-------------------------------|
| 철학   | 빠르고 단순한 처리     | SQL 표준 준수와 ACID 보장에 충실   |
| 사용 목적 | 웹 서비스에서 빠른 응답 | 복잡한 쿼리와 고급 기능 지원  |
| 강점   | 단순 읽기 성능, 쉬운 사용성  | 복잡한 쿼리의 최적화 |

### 선택해야 할 경우

**MySQL 선택 조건**  
- 단순한 CRUD 중심 서비스  
- 웹 애플리케이션 중심, 빠른 응답 시간 필요  
- 제한된 서버 자원 환경
- 빠른 개발과 배포가 필요  

**PostgreSQL 선택 조건**  
- 복잡한 통계 및 리포팅  
- JSON, 배열, 범위 등 데이터 모델링 활용  
- 데이터 무결성, 정확성 중요  

---

## 데이터 타입

PostgreSQL은 기본 타입 외에도 배열, JSONB, 범위, IP, 좌표 등 다양한 고유 타입을 지원

복잡한 데이터 구조를 직관적으로 저장 및 조회 가능

```sql
CREATE TABLE datatyoe_demo (
    id SERIAL PRIMARY KEY,       -- MySQL에서의 AUTO_INCREMENT
    tags TEXT[],                 -- 문자열 배열
    metadata JSONB,              -- JSON 객체 (binary 형태)
    salary_range INT4RANGE,      -- 정수 범위
    ip_address INET,             -- IP 주소 (IPv4/IPv6)
    location POINT               -- 2차원 좌표 (x, y)
);
```

1. **`TEXT[]`** :  `'{tag1,tag2,tag3}'`

    문자열 배열, 다중 값을 하나의 컬럼에 저장 가능
    
    tags[2] 처럼 내부의 데이터를 좌표로 꺼낼 수 있다.

   


2. **`JSONB`** : `'{"name": "Alice", "age": 30}'::jsonb`

    이진 형태 JSON 저장, 쿼리 효율성 및 인덱싱에 유리함
    
    json에서 특정 데이터 종류를 뽑아내거나, 특정 조건으로 활용할 수도 있음.

    ```sql
    SELECT
        name,
        metadata,
        metadata->>'department' AS 부서,
        -- 화살표에 꺾쇠가 하나면 결과를 jsonb로, 두개면 text로 추출
        metadata->'skills' AS 능력
    FROM datatype_demo
    -- 메타데이터에 level이 senior라는게 있으면 출력
    WHERE metadata @> '{"level":"senior"}'; 
    ```

3. **`INT4RANGE`** : `'[30000, 50000)'`

    정수 범위, 시작 ~ 끝 구간 표시 
    
    []는 닫힌 구간 - 이상/이하, ()는 열린 구간 - 초과/미만

    정수범위는 `INT4RANGE`, 실수는 `NUMRANGE`, 타임스탬프 범위는 `TSRANGE` 등등

    ```sql
    SELECT
        name,
        salary,
        salary_range,
        -- <@ 을 통해 포함관계를 볼 수도 있음 
        salary <@ salary_range AS 포함여부,
        -- 범위의 양 끝값도 뽑을 수 있다
        UPPER(salary_range) - LOWER(salary_range) AS 연봉폭
    FROM datatype_demo;
    ```
4. **`INET`** :  `'192.168.0.1'`
    IP 주소 및 서브넷 정보 저장 

5. **`POINT`**:  `'(37.505027,127.005011)'`

    2차원 평면 좌표 (x, y) 저장

    위도/경도를 쓰면 지도 데이터도 볼 수 있다.

    `POINT(37.505027,127.005011) <-> location AS 거리` 처럼 거리계산도 가능



## `generate_series`

`generate_series`는 지정한 범위 내 연속된 값을 생성할 수 있다. 

`random()` 함수와 함께 사용하면 랜덤 값을 포함한 대용량 데이터 생성 가능  

테스트 데이터 생성, 시뮬레이션 등에 많이 씀

```sql
-- 1부터 100까지 숫자 시퀀스 생성
SELECT generate_series(1, 100);

-- 특정 기간 내 하루 단위 날짜 시퀀스 생성
SELECT generate_series('2024-01-01'::DATE, '2024-01-10'::DATE, '1 day'::INTERVAL);

-- generate_series와 random() 이용 랜덤 데이터 생성
SELECT
    generate_series(1, 1000) AS id,
    (random() * 100)::INT AS random_score,
    'Category-' || (1 + (random() * 5)::INT) AS category;
```

## 쿼리 분석

### EXPLAIN

```sql
EXPLAIN
SELECT * FROM large_orders WHERE customer_id = 'CUST-025000';
```
```sql
Seq Scan on large_orders  (cost=0.00..1234.56 rows=10 width=100)
  Filter: (customer_id = 'CUST-025000'::text)
```
EXPLAIN만 쓰면, 실제로 쿼리를 돌리지는 않고 컴퓨터가 예상하는 쿼리 실행 정보를 보여준다.

- Seq Scan: 전체 테이블을 순차적으로 스캔 (INDEX 없음)
- cost=0.00..1234.56: 예상 시작 비용과 총 비용  
- rows=10: 예상 반환 행 수  

---

### EXPLAIN ANALYZE

```sql
EXPLAIN ANALYZE
SELECT * FROM large_orders WHERE customer_id = 'CUST-025000';
```
```sql
Seq Scan on large_orders  (cost=0.00..1234.56 rows=10 width=100) (actual time=0.020..1.234 rows=12 loops=1)
  Filter: (customer_id = 'CUST-025000'::text)
  Rows Removed by Filter: 998
Planning Time: 0.123 ms
Execution Time: 1.456 ms
```
EXPLAIN ANALYZE는 실제로 쿼리를 실행 후, 그 결과를 분석해 준다.

- actual time=0.020..1.234: 실제 실행 시간 (최초 출력~마지막 출력)  
- rows=12: 실제 반환 행 수  
- Rows Removed by Filter: 필터 조건에 걸러진 행 수  
- Planning Time: 쿼리 계획 수립 시간  
- Execution Time: 쿼리 실행 전체 시간  

---

### EXPLAIN (ANALYZE, BUFFERS, VERBOSE)

```sql
EXPLAIN (ANALYZE, BUFFERS, VERBOSE)
SELECT * FROM large_customers WHERE loyalty_points > 8000;
```
```sql
Seq Scan on public.large_customers large_customers  (cost=0.00..1000.00 rows=500 width=150) (actual time=0.015..20.000 rows=450 loops=1)
  Output: customer_id, name, loyalty_points, ...
  Filter: (loyalty_points > 8000)
  Rows Removed by Filter: 10550
  Buffers: shared hit=3500 read=50
Planning Time: 0.200 ms
Execution Time: 20.500 ms
```

`BUFFERS` - 진짜 많이 사용하는 데이터를 저장하는 곳
- Buffers: 메모리 버퍼 사용 통계 (shared hit=캐시에서 읽음, read=디스크에서 읽음)  

`VERBOSE` - 컬럼명 같은 상세정보를 볼 수 있음
- Output: 출력 컬럼명과 순서  


---

### EXPLAIN (FORMAT JSON)

```sql
EXPLAIN (ANALYZE, BUFFERS, VERBOSE, FORMAT JSON)
SELECT * FROM large_customers WHERE loyalty_points > 8000;
```

실행 계획을 JSON 형식으로 출력  

복잡한 쿼리 계획 프로그램 파싱, 시각화에 유용  

### MySQL에서의 EXPLAIN

```sql
EXPLAIN
SELECT c.customer_name, s.product_name, s.total_amount
FROM customers c
INNER JOIN sales s ON c.customer_id = s.customer_id
WHERE c.customer_type = 'VIP';
```
```
-- +----+-------------+-------+--------+---------------+---------+---------+-------+------+-------------+
-- | id | select_type | table | type   | possible_keys | key     | key_len | ref   | rows | Extra       |
-- +----+-------------+-------+--------+---------------+---------+---------+-------+------+-------------+
-- |  1 | SIMPLE      | c     | ALL    | PRIMARY       | NULL    | NULL    | NULL  | 50   | Using where |
-- |  1 | SIMPLE      | s     | ref    | customer_id   | cust_id | 12      | c.id  | 2    | NULL        |
-- +----+-------------+-------+--------+---------------+---------+---------+-------+------+-------------+
```

### MySQL - PostgreSQL 비교

1. 출력 형태 및 정보 밀도

    | 측면       | MySQL                       | PostgreSQL                      |
    | ---------- | --------------------------- | ------------------------------ |
    | 출력 형태  | 테이블 형태      | 트리 구조 |
    | 정보 밀도  | 간결함                      | 매우 상세함                     |
    | 가독성     | 초보자 친화적                | 전문가 친화적                   |
    | 사용 편의성 | 직관적                      | 익숙해지면 강력함               |



1. 기능 차이

    | 기능 항목     | MySQL                      | PostgreSQL                                   |
    | ------------ | -------------------------- | -------------------------------------------- |
    | 비용 정보     | 제한적                     | 상세 제공     |
    | 실제 통계     | 특정 버전 이후로 제공     | 로 기본 제공                |
    | 메모리 사용량 | 제한적             | `BUFFERS` 옵션으로 상세 추적 가능             |
    | 출력 형식     | TEXT, JSON                 | TEXT, JSON, YAML, XML                         |



**MySQL**  
- `type = ALL`: 전체 테이블 스캔 → 인덱스 필요성 검토  
- `Extra = Using temporary`: 정렬 중간 결과 → 메모리/쿼리 구조 최적화 필요  
- `Extra = Using filesort`: ORDER BY 최적화 필요  
- `rows` 수가 과도한 경우: 조건 추가 혹은 인덱스 점검  


**PostgreSQL**  
- `cost`, `actual time`, `rows` 등을 통해 세밀한 병목 분석 가능  
- `BUFFERS`와 `I/O` 관련 옵션으로 메모리 vs 디스크 접근량 분석  
- JSON 출력으로 자동화된 분석 도구와 연계 용이  
- 통계 정보(`pg_stats`) 확인 → 잘못된 실행계획 원인 파악 가능  
