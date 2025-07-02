USE lecture;
SELECT * FROM dt_demo;

SELECT
	name,
    score AS 원점수,	
    ROUND(score) AS 반올림,			-- 소수점 아래를 전부 반올림
    ROUND(score, 1) AS 소수1_반올림,	-- 소수점 아래 n번째 자리에서 반올림할지도 설정 가능
    CEIL(score) AS 올림,				-- 소수점 아래를 전부 올림
    FLOOR(score) AS 내림,			-- 소수점 아래를 전부 내림
    TRUNCATE(score, 1) AS 버림		-- 소수점 아래 n번째 자리를 전부 버림 # 내림과 버림의 차이는 음수일때
FROM dt_demo;