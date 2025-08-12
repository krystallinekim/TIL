-- RECURSIVE = 재귀
-- factorial 정의할 때 나오는 거
--  f(n) = n * f(n-1) (n>1)
-- 					1 (n=1)
-- f(5) = 5 * f(4) = 5 * 4* f(3) = ... = 5 * 4 * 3 * 2 * f(1) = 5 * 4 * 3 * 2 * 1 = 120

WITH RECURSIVE numbers AS(
	-- 초기값
	SELECT 1 AS num
	UNION ALL
	-- 재귀값
	SELECT num + 1
	FROM numbers
	WHERE num < 10
)
SELECT * FROM numbers;
-- >> 1 2 3 4 5 6 7 8 9 10

SELECT * FROM employees;
-- 이게 TREE 구조 : 1 밑에 2,3,4,5 / 2 밑에 6,7 / 3 밑에 8,9 / ... / 6 밑에 12,13 / ...

-- 예시 : 회사 구조도
WITH RECURSIVE org_chart AS (
	SELECT
		employee_id,
		employee_name,
		manager_id,
		department,
		1 AS 레벨,
		-- 원래 employee_name은 varchar였음
		employee_name::text AS 조직구조
	FROM employees
	-- CEO인경우
	-- 바꿔줄수도 있음
	WHERE employee_id = 1
	-- UNION ALL은 중복이 있든 없든 상하로 이어붙여줌
	-- 위 아래에 컬럼 숫자만 맞으면 된다
	UNION ALL
	-- 
	SELECT
		e.employee_id,
		e.employee_name,
		e.manager_id,
		e.department,
		-- 이전 단계의 oc.레벨에 1을 더함
		oc.레벨 + 1,
		-- ||는 String CONCAT을 간단하게 표시
		(oc.조직구조 || '>>' || e.employee_name)::text
	FROM employees e
	-- 이전 줄의 employee_id가 내 상사인 사람들을 가져옴
	-- 더이상 employee_id가 누군가의 manager_id인 사람이 없을 때 = 이너 조인을 만족하는 친구(교집합)가 안나올때까지 반복함
	INNER JOIN org_chart oc ON e.manager_id=oc.employee_id
	-- 지금 만드는 org_chart를 org_chart 안에서 사용 --> 재귀
)
SELECT * FROM org_chart ORDER BY 레벨;


-- 1월 1일부터 하루씩 다음날을 보여줌
WITH RECURSIVE calender AS(
	SELECT '2024-01-01'::DATE AS 날짜
	UNION ALL
	SELECT (날짜 + INTERVAL '1 day')::DATE
	-- 재귀적인 부분, 자기 자신을 계속 참조하는 중
	FROM calender
	WHERE 날짜 < '2024-01-31'::DATE
)
SELECT 날짜
FROM calender;

-- 애초에 SQL에서 반복문을 쓰라고 만들어놓은게 아님
-- 재귀 CTE가 어떤 방식으로 작동하는가 / 언제 적용해야하는가
