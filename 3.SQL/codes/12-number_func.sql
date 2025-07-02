USE lecture;
SELECT * FROM dt_demo;

-- 실수(소수점) 함수들
SELECT
	name,
    score AS 원점수,	
    ROUND(score) AS 반올림,			-- 소수점 아래를 전부 반올림
    ROUND(score, 1) AS 소수1_반올림,	-- 소수점 아래 n번째 자리에서 반올림할지도 설정 가능
    CEIL(score) AS 올림,				-- 소수점 아래를 전부 올림
    FLOOR(score) AS 내림,			-- 소수점 아래를 전부 내림
    TRUNCATE(score, 1) AS 버림		-- 소수점 아래 n번째 자리를 전부 버림 # 내림과 버림의 차이는 음수일때
FROM dt_demo;

-- 사칙연산
SELECT
	10 + 3 		AS 덧셈,
    10 - 3 		AS 뺄셈,
    10 * 3 		AS 곱셈,
    10 / 3 		AS 나눗셈,
	10 DIV 3 	AS 몫,
    10 % 3 		AS 나머지,
    MOD(10,3) 	AS 나머지2,	-- Modulo
    POWER(10,3)	AS 거듭제곱,	-- Square(^2), Cube(^3), 나머지는 Power
    SQRT(10)	AS 루트,		-- Square Root
    ABS(-10)	AS 절댓값;	-- Absolute
    
    
    
-- 조건문 (IF)
SELECT
	name,
    score,
    IF(score >= 80, '우수', '보통') AS 평가		-- IF는 TRUE/FALSE인 경우에 적합 / 늘어나면 힘들어진다
FROM dt_demo;

-- 조건문 (CASE)
SELECT
	name,
    score,
    CASE
		WHEN score >= 90 THEN 'A'	-- CASE는 다양한 조건을 걸 수 있음
        WHEN score >= 80 THEN 'B'	-- 위에서부터 내려가면서 해당사항 있으면 바로 값을 준다
        WHEN score >= 70 THEN 'C'	-- 위로 갈수록 좁은 조건, 아래로 갈수록 넓은 조건을 줘야함
        ELSE 'D'					-- ELSE는 나머지 값이 해당되지 않은 경우를 전부 이걸로 처리하겠다는것
        END AS Grade
FROM dt_demo;

-- IF와 CASE는 조건이 2개일 경우 같은 일을 한다
SELECT
	id,
    name,
    id % 2 AS 나머지,
	CASE
		WHEN id % 2 = 0 THEN '짝수'
        ELSE '홀수'
	END AS 홀짝,
    IF (id %2 = 1, '홀수', '짝수') AS 홀짝2
FROM dt_demo;

-- NULL
SELECT
	name,
    IFNULL(score,0) AS 점수
FROM dt_demo;

