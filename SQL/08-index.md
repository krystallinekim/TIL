# INDEX

## 기초

인덱스는 테이블의 열 단위에 생성되는 데이터베이스의 개체로, 데이터를 조회할 때 빠르게 접근하도록 도와주는 역할을 한다.

인덱스를 적절히 사용하면 시스템의 성능에 큰 도움이 되지만, 그렇지 않다면 오히려 시스템의 성능을 떨어뜨릴 수도 있다.

WHERE, ORDER BY, JOIN, GROUP BY 등의 성능을 향상시킬 수 있음

### 인덱스의 구분


- 인덱스는 `클러스터형 인덱스(Clustered Index)`와 `보조 인덱스(Secondary Index)`로 구분된다.

   - `클러스터형 인덱스(Clustered Index)`: 테이블당 한 개만 존재하며, 일반적으로 `PRIMARY KEY`가 이에 해당한다.
        
      - 기본적으로 이 기준으로 데이터를 오름차순으로 정렬해서 저장됨

   - `보조 인덱스(Secondary Index)`:  테이블당 여러 개를 생성할 수 있다. 
      
      - 클러스터형 인덱스 말고 전부 해당

- 또한, 인덱스는 중복 허용 여부에 따라 `고유 인덱스(Unique Index)`와 `비고유 인덱스(Nonunique Index)`로 나눌 수 있다.

   - `고유 인덱스(Unique Index)`: 인덱스 값들이 서로 중복되지 않는 인덱스이다. (PRIMARY KEY, UNIQUE)

   - `비고유 인덱스(Nonunique Index)`: 인덱스 값의 중복을 허용하는 인덱스이다. (FOREIGN KEY)


### 인덱스 생성

1. 자동으로 생성되는 경우

   - `PRIMARY KEY` 제약 조건으로 지정되는 열에 자동으로 클러스터형 인덱스가 생성된다.
   
   - `UNIQUE` 제약 조건으로 지정되는 열에 자동으로 보조 인덱스가 생성된다.


2. 직접 생성할 경우

   ```sql
   CREATE [UNIQUE] INDEX {인덱스 이름}  -- UNIQUE 생략시 비고유, 넣으면 고유 인덱스
   ON {테이블 이름}({열 이름}[, {열 이름}, ...]);  -- 열 이름 하나만 넣어도 되고, 여러개 넣어도 됨

   -- 인덱스를 분석 후 적용
   ANALYZE TABLE {테이블 이름};
   ```

### 인덱스 생성 시 주의 사항

- `WHERE`절에서 자주 사용되는 열에 인덱스를 만들어야 한다.
- 데이터의 중복도가 높은 열은 인덱스를 만들어도 별 효과가 없다.
- `JOIN`에 자주 사용되는 열에는 인덱스를 생성해 주는 것이 좋다.
- `DML(INSERT, UPDATE, DELETE)` 작업이 얼마나 자주 일어나는지를 고려해야 한다.
- 사용하지 않는 인덱스는 제거하자.


### 인덱스 조회

PostgreSQL 내장 테이블을 이용할 수 있다.

```sql
SELECT
	tablename,
	indexname,
	indexdef
FROM pg_indexes
WHERE tablename IN ('large_orders');
```

그냥 옆에 Schemas에서 테이블에 딸린 인덱스를 시각화해서 보는게 빠름

---

### 인덱스의 효과

```sql
EXPLAIN ANALYZE
SELECT COUNT(*) FROM large_orders WHERE region = '서울'
```
위 코드를 그냥 돌리면 Execution Time: 134.767ms가 나옴

인덱스를 적용하고 돌리면 14.319ms만에 결과를 볼 수 있다.

인덱스는 카테고리화 할 수 있는 것들(region, customer_id 등등)에 매우매우 효과적임

반대로, 인덱싱을 했을 때 더 안좋아지는 경우도 있다.
```sql
EXPLAIN ANALYZE
SELECT * FROM large_orders
WHERE amount BETWEEN 800000 AND 1000000;
```
인덱싱을 안했을 때 152.612ms, amount에 대해 인덱싱 했을 시 293.284ms가 나왔음

주로 가져올 행 수가 많거나, 카테고리화 못하는 컬럼에 대해 인덱싱을 했을 때 느린 편이다.

책에 사전에 기록된 모든 단어에 대해 색인이 되어 있는 느낌임

---

## 복합 인덱스

인덱싱을 한 컬럼에 대해서만 할 수 있는것은 아니다.

두 개 이상을 묶어서 인덱싱 할 수도 있다
```sql
CREATE INDEX idx_orders_amount_region ON large_orders(amount, region);
```
이 때, amount-region 와 region-amount는 다른 인덱스임

심지어 속도도 다르게 나온다.

따라서, 복합 인덱스를 적용할 때는 순서를 잘 맞춰야 함.

### 순서 기준

1. Selectivity

   전체 데이터 개수 대비 Unique 개수(Cardinality)의 비율을 말한다.

   users 테이블에 국가(country), 도시(city), 이름(name)이 있다고 하자.

   이름의 선택도가 가장 높고(거의 모두 고유값), 도시의 선택도는 중간(고유값이 많음), 국가의 선택도는 데이터가 많다면 거의 0에 가까울 것이다

   일반적으로, 복합 인덱스에서는 선택도가 높은 (country -> city) 순으로 인덱싱해 필터링 효과를 키운다.

1. WHERE/ORDER BY

   복합 인덱스는 구성된 컬럼 순서대로 정렬됨. 
   
   따라서, WHERE이나 ORDER BY를 사용할 때 인덱스 앞쪽에 위치한 컬럼부터 순서대로 사용해야 인덱스 효율이 높아진다.


## 종류

### B-Tree

가장 기본적인 인덱스 구조(보통 따로 설정 안하면 기본적으로 B-Tree로 나옴)

매우 범용성이 높다. 기본적으로 어떤 경우에도 적당히 좋은 검색 속도를 보임
```sql
CREATE INDEX <index_name> ON <table_name>(<col_name>);
```

### Hash

정확한 일치검색에 가장 특화된 구조.

대신 다른 종류의 검색(범위, 부분일치 등)은 아예 지원하지 않는다.

해시태그를 생각하면 편함
```sql
CREATE INDEX <index_name> ON USING HASH(<col>);
```

### B-Tree와 Hash 한눈에 비교

| 기능 | | B-Tree | Hash |
| --- | --- |  --- | --- |
| 정확 일치| = | 지원 | **매우 최적화된 지원** |
| 범위 검색| BETWEEN, 부등호 | 지원 | 지원 안함 |
| 정렬| ORDER BY | 지원 | 지원 안함 |
| 부분 일치| LIKE | 지원 | 지원 안함 |


## 인덱스를 안 쓰는 경우

인덱스는 기본적으로 데이터 전체에 색인을 넣는거라, 용량이 꽤 나온다.

인덱싱을 할 경우 검색 성능이 매우 향상되지만, 반대로 데이터를 그냥 수정/삭제하려고 하면 데이터 구조가 망가질 수 있어 매우 귀찮아진다.

- 자주 바뀌는 데이터(자주 업데이트되는 데이터)에는 인덱스를 추가하는 게 안 좋을 수도 있음

- 조회를 자주 해야 하는 데이터에는 인덱스를 추가하는 게 맞다.

- 실제 쿼리 패턴을 분석해 인덱스를 설계해야 함

## 인덱스가 적용되지 않는 경우

인덱스를 적용해 둬도, 이하의 경우에는 깡통이 된다.

1. 함수

   함수 내부의 데이터에 인덱스가 걸려 있어도 함수 밖에서 인덱싱은 적용되지 않는다.
   ```sql
   SELECT * FROM users WHERE UPPER(name) = 'JOHN';
   ```
   아예 함수 기반으로 인덱싱을 하면 해결됨.
   ```sql
   CREATE INDEX <index_name> ON users(UPPER(name));
   ```
   ---
1. 타입을 잘못 썼을 경우
   
   ```sql
   SELECT * FROM users WHERE age = '25';
   ```
   age의 데이터타입을 INT로 정의했는데, '25'는 str임

   사실, 이정도는 프로그램이 알아서 '25'를 25로 적용해 답을 구해주기는 한다.
   ```sql
   SELECT * FROM users WHERE age = 25;
   ```
   그래도, 인덱싱은 적용이 안되기 때문에 매우 느리게 검색할 것
   
   ---
1. 부정조건

   부정조건을 쓰면 인덱싱이 안된다.
   ```sql
   SELECT * FROM users WHERE age != 25;
   ```
   놀랍게도, 부등호를 써서 조건을 정의하면 인덱싱이 됨
   ```sql
   SELECT * FROM users WHERE age > 25 OR age < 25;
   ```
   ---

1. 앞에 와일드카드를 썼을 경우
   
   ```sql
   SELECT * FROM users WHERE name LIKE '%김';
   ```
   이건 딱히 해결방법이 없다. 전체 텍스트 검색 인덱스를 고려해 보자.