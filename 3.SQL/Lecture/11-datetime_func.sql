USE lecture;
SELECT * FROM dt_demo;

-- 현재 날짜 & 시간

-- 날짜 + 시간
SELECT NOW();				-- 진짜 현재 날짜와 시간을 보고 싶을때
SELECT CURRENT_TIMESTAMP(); -- 현재 시간을 기록해놔야 할때...지만 결국 별 차이 없다

-- 날짜
SELECT CURDATE(); 		-- 이건 함수처럼 쓸 때
SELECT CURRENT_DATE();	-- 컬럼명처럼 쓰고 싶을 때

-- 시간
SELECT CURTIME();
SELECT CURRENT_TIME();

-- 현재 날짜/시간을 적을 수 있게 되었다 = 원하는 포맷을 적을 수 있다
SELECT 
	name,
    birth AS 원본,
    DATE_FORMAT(birth, '%Y년 %m월 %d일') AS 한국식,	 	-- 2010년 5월 21일
    DATE_FORMAT(birth, '%Y-%m') AS 년월, 			-- 2010-05
    DATE_FORMAT(birth, '%M %d %Y') AS 영문식,		 	-- May 21 2010
    DATE_FORMAT(birth, '%w') AS 요일번호,				-- 5
    DATE_FORMAT(birth, '%W') AS 요일이름 				-- Friday
FROM dt_demo;
-- 2024(Y)/24(y), May(M)/05(m), 21st(D)/05(d), W(Friday)/w(5)

-- 시간
SELECT 
	created_at AS 원본시간,
    DATE_FORMAT(created_at, '%Y-%m-%d %H:%i') AS 분까지만,	-- 2025-07-01 15:17
    DATE_FORMAT(created_at, '%p %h:%i') AS 12시간			-- PM 03:17
FROM dt_demo;
-- 15(H)/03(h), AM-PM(%p)

-- 날짜 계산 함수
SELECT
	name,
    birth,
    DATEDIFF(CURRENT_DATE(),birth) AS 살아온_일수,		-- 5521 / DATEDIFF() : 둘 사이의 날짜 차이
	TIMESTAMPDIFF(YEAR,birth,CURRENT_DATE()) AS 나이	-- 15	/ TIMESTAMPDIFF(결과 단위, 날짜1, 날짜2): 날짜 1과 날짜 2의 차이(결과 단위로)
FROM dt_demo;

-- 더하기/빼기
SELECT
	name,
    birth,
    DATE_ADD(birth, INTERVAL 100 DAY) AS 백일후,			-- 태어난 뒤로 100일 뒤 / ADD(기준일, 일수) 
    DATE_ADD(birth, INTERVAL 1 YEAR) AS 돌,				-- 태어난 뒤로 1달 뒤	/ INTERVAL n 단위
	DATE_SUB(CURRENT_DATE(), INTERVAL 1 MONTH) AS 1달전	-- 현재일로부터 1달 빼기 / SUB(기준일, 일수)
FROM dt_demo;

-- 계정 생성 후 경과 시간
SELECT
	name,
    created_at,
    TIMESTAMPDIFF(HOUR,created_at,NOW()) AS 경과시간,
    TIMESTAMPDIFF(DAY,created_at,NOW()) AS 경과일수
FROM dt_demo;

-- 날짜 구성 요소 추출
SELECT
	name,
    birth,						-- date 정보 (YMD)
    YEAR(birth) AS 연,			-- 연
    MONTH(birth) AS 월, 			-- 월
    DAY(birth) AS 일,			-- 일
    DAYNAME(birth) AS 요일,		-- 요일 -> Friday로 고정값
    DAYOFWEEK(birth) AS 요일번호,	-- 요일번호 -> 요일번호에 따라 '일요일', 'SUN' 등으로 매핑 가능함
    QUARTER(birth)AS 분기		-- 분기
FROM dt_demo;

-- 월별, 연도별
SELECT
	YEAR(birth) AS 출생년도,
    COUNT(*) AS 인원수
FROM dt_demo
GROUP BY YEAR(birth)		-- GROUP BY : 뒤에 오는걸 기준으로 몇개가 있는지 분류함 -> AGGREGATED QUERY에서는 이게 있어야 분류 가능
ORDER BY 출생년도;			-- ORDER BY '출생년도'로 하면 '출생년도'라는 str로 인식해서 정렬 안함 -> ORDER BY 출생년도 / ORDER BY YEAR(birth) 로 하면 컬럼명 / 연도 로 인식해서 정렬 가능 
							-- AS 뒤에는 띄어쓰기를 그냥 안해서 컬럼명을 인식시키는게 제일 좋다 -> 지금같은 경우에 ORDER BY 출생 년도 이렇게 되면 인식을 못하기 때문
-- 이걸 잘 쓰면
SELECT
	(YEAR(birth) DIV 10) * 10 AS 출생연도_10년단위,
    COUNT(*) AS 인원수
FROM dt_demo
GROUP BY 출생연도_10년단위
ORDER BY 출생연도_10년단위;
-- 이렇게 연도별로 나눠주기도 가능함



