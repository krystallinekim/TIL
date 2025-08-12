CREATE TABLE datatype_demo(
	-- MySQL에도 있지만, 이름이 살짝 다른 친구들
	id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	age INT,
	salary NUMERIC(12,2),
	is_active BOOLEAN DEFAULT TRUE,
	created_at TIMESTAMP DEFAULT NOW(),
	-- PostgreSQL 전용
	tags TEXT[],			-- 배열 타입 ['a','b','c','d','e'] ->> tags[2]='c' 쓸 수 있음
	metadata JSONB,			-- JSON Binary 타입
	ip_address INET,		-- IP 주소를 저장하는 전용 타입
	location POINT,			-- 좌표값 저장하는 타입(위도, 경도 저장 등) ->> (x,y)
	salary_range INT4RANGE	-- 범위를 저장하는 타입
);


INSERT INTO datatype_demo (
    name, age, salary, tags, metadata, ip_address, location, salary_range
) VALUES
(
    '김철수',
    30,
    5000000.50,
    ARRAY['개발자', 'PostgreSQL', '백엔드'],        -- 배열
    '{"department": "IT", "skills": ["SQL", "Python"], "level": "senior"}'::JSONB,  -- JSONB
    '192.168.1.100'::INET,                         -- IP 주소
    POINT(37.5665, 126.9780),                      -- 서울 좌표
    '[3000000,7000000)'::INT4RANGE                 -- 연봉 범위
),
(
    '이영희',
    28,
    4500000.00,
    ARRAY['디자이너', 'UI/UX'],
    '{"department": "Design", "skills": ["Figma", "Photoshop"], "level": "middle"}'::JSONB,
    '10.0.0.1'::INET,
    POINT(35.1796, 129.0756),                      -- 부산 좌표
    '[4000000,6000000)'::INT4RANGE
);


-- TEXT[]: 배열
SELECT * FROM datatype_demo;
SELECT
	name,
	tags[1],
	-- tags에 특정 값이 배열 안에 있는지도 확인 가능
	'PostgreSQL' = ANY(tags) AS pg_dev
FROM datatype_demo;


-- JSONB: json
SELECT
	name,
	-- metadata는 정보에 대한 정보
	metadata,
	-- json에서 특정 데이터 종류를 뽑아낼 수 있다
	metadata->>'department' AS 부서,
	-- 화살표에 꺾쇠가 하나면 jsonb로, 두개면 text로 추출
	metadata->'skills' AS 능력
FROM datatype_demo;

SELECT
	name,
	metadata ->> 'department' AS 부서
FROM datatype_demo
-- 메타데이터에 level이 senior라는게 있으면 출력
WHERE metadata @> '{"level":"senior"}'; 


-- INT4RANGE: 범위 (실수형은 NUMRANGE)
SELECT
	name,
	salary,
	-- [300만 이상, 7백만 미만) -> 닫힌 구간/열린 구간
	salary_range,
	-- salary는 실수형이라 정수형 범위인 salary_range와 비교할 수 없다 --> 타입을 바꿔주면 됨
	salary::INT <@ salary_range AS 포함여부,
	-- 범위에서는 
	UPPER(salary_range),
	LOWER(salary_range),
	UPPER(salary_range) - LOWER(salary_range) AS 연봉폭
FROM datatype_demo;


-- POINT: 좌표값
SELECT
	name,
	location[0] AS 위도,
	location[1] AS 경도,
	-- 특정 위치와 거리계산도 가능 (단위는 없이 그냥 포인트 간 길이)
	POINT(37.505027,127.005011) <-> location AS 거리
FROM datatype_demo;
