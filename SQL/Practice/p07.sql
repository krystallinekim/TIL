USE practice;
CREATE TABLE dt_demo_practice AS SELECT * FROM lecture.dt_demo_practice;
SELECT * FROM dt_demo_practice;


-- 종합 정보 표시
-- id / name / nickname (NULL=미설정) / 출생년도 (19xx년생) / 나이 / 점수 (반올림-소수1, NULL=0), 등급 (A>=90,B>=80,C>=70,나머지는 D), 상태(활성/비활성), 연령대 (미성년 - 19 - 청년 - 30 - 중년 - 50 - 장년 - 64 - 노년)

SELECT
	id AS ID,
    name AS 이름,
    IFNULL(nickname,'미설정') AS 닉네임,
    DATE_FORMAT(birth,'%Y년생') AS 출생년도,
    TIMESTAMPDIFF(YEAR,birth,NOW()) AS 나이,
    IFNULL(ROUND(score,1),0) AS 점수,
    CASE
		WHEN score >= 90 THEN 'A'
        WHEN score >= 80 THEN 'B'
        WHEN score >= 70 THEN 'C'
        ELSE 'D'
	END AS 등급,
    IF(is_active = 1, '활성', '비활성') AS 상태,
    CASE
		WHEN TIMESTAMPDIFF(YEAR,birth,NOW()) >= 64 THEN '노년'
		WHEN TIMESTAMPDIFF(YEAR,birth,NOW()) >= 50 THEN '중년'
		WHEN TIMESTAMPDIFF(YEAR,birth,NOW()) >= 40 THEN '장년'
		WHEN TIMESTAMPDIFF(YEAR,birth,NOW()) >= 30 THEN '청장년'
		WHEN TIMESTAMPDIFF(YEAR,birth,NOW()) >= 19 THEN '청년'
        ELSE '미성년'
	END AS 연령대
FROM dt_demo_practice;