-- DATA Structure (Graph, Tree, List, Hash, ...) --> 참 다양하구나, 시각화해서 보는 정도

-- B-TREE(Binary tree)
CREATE INDEX <index_name> ON <table_name>(<col_name>);
-- 범위검색(BETWEEN, 부등호)
-- 정렬(ORDER BY)
-- 부분 일치(LIKE)
-- 전부 괜찮게 지원함 --> 기본적으로 인덱스는 이걸로 생성됨

-- Hash
CREATE INDEX <index_name> ON USING HASH(<col>);
-- 정확한 일치검색(=)에 가장 특화
-- 범위, 정렬, 부분일치 전부 안됨
-- #해시태그 생각하면 된다
-- Ex) 내가 쓴 글 보기

-- 부분인덱스
CREATE INDEX <index_name> ON <table_name>(<col_name>) 
WHERE 조건 = '...';
-- 특정 조건의 데이터만 자주 검색하는 경우(공간/비용 절약)
-- Ex) 재학중인 학생만 인덱싱 해야하는 경우


-- 인덱스를 안 쓰는 경우
SELECT * FROM users WHERE UPPER(name) = 'JOHN';	-- 1. 함수를 쓰면 인덱스를 못씀
SELECT * FROM users WHERE age = '25';			-- 2. age는 숫자인데 문자를 잘못 넣고 비교하면 인덱스를 안씀(돌아가긴 함)
SELECT * FROM users WHERE name LIKE '%김';		-- 3. LIKE에서 앞쪽에 와일드카드가 들어갈 경우
SELECT * FROM users WHERE age != 25;			-- 4. 부정조건인 경우

-- 해결방법은
-- 1. 함수 기반으로 인덱싱
CREATE INDEX <index_name> ON users(UPPER(name));-- UPPER(name)을 가져올 때 인덱싱을 함
-- 2. 타입 잘 쓰기
SELECT * FROM users WHERE age = 25; 
-- 3. 전체 텍스트 검색 인덱스를 고려
-- 4. 부정조건을 범위조건으로
SELECT * FROM users WHERE age < 25 OR age > 25;

-- 검색 성능 +++ / 저장 공간 추가 필요 - / 수정 성능 ---
-- 수정/삭제시 데이터 구조가 망가지는 경우가 있다.
-- 자주 바뀌는 데이터에는 인덱스 추가하는게 좋지 않을 수 있음
-- 조회를 자주 하는 데이터는 인덱스를 추가하는게 좋다
-- >> 실제 쿼리 패턴을 분석해 인덱스를 설계하고, 성능 측정 후 실제 데이터를 투입