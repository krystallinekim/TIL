-- 윈도우 함수

-- 1. 순위 함수

SELECT 
    name,
    addr,
    height,
    ROW_NUMBER() OVER(ORDER BY height DESC) AS 'row_number',  -- 1 2 3 4 5 -> 동점자는 무시
    RANK()       OVER(ORDER BY height DESC) AS 'rank',        -- 1 2 2 4 5 -> 동점자 수만큼 건너뜀
    DENSE_RANK() OVER(ORDER BY height DESC) AS 'dense_rank',  -- 1 2 2 3 4 -> 동점자 수는 무시
    NTILE(4) OVER(ORDER BY height DESC) AS 'ntile'
FROM usertbl;


-- 실습: 지역을 기준으로 그룹화 후 키를 기준으로 순서
SELECT
    ROW_NUMBER() OVER(PARTITION BY addr ORDER BY height DESC) AS 'row',  -- PARTITION BY는 윈도우 함수에서 그룹화 기능
    name,
    addr,
    height
FROM usertbl;

-- 실습: employee에서 급여가 높은 상위 10명의 순위, 직원명, 급여
USE employees_db;
SELECT
    RANK() OVER(ORDER BY salary DESC) AS 'rank',
    emp_name,
    salary
FROM employee
LIMIT 10;
-- WHERE `rank` BETWEEN 1 AND 10;  -- 윈도우 함수의 결과는 WHERE로 쓸 수 없다
-- >> 서브쿼리 사용

SELECT 
    emp.rank,
    emp.name,
    emp.salary
FROM(
    SELECT
        RANK() OVER(ORDER BY salary DESC) AS 'rank',
        emp_name AS 'name',
        salary
    FROM employee
) emp
WHERE emp.rank BETWEEN 1 AND 10;

-- 2. 분석 함수

-- 테이블의 다른 위치의 값을 참조 가능함. 값이 없으면 NULL

SELECT
    name,
    height,
    LEAD(height, 1) OVER(ORDER BY height DESC) AS 'lead', -- 다음 행의 데이터
    height - LEAD(height, 1) OVER(ORDER BY height DESC) AS 'leaddiff',
    LAG(height, 1)  OVER(ORDER BY height DESC) AS 'lag',  -- 이전 행의 데이터
    height - LAG(height, 1) OVER(ORDER BY height DESC) AS 'lagdiff'
FROM usertbl;


SELECT
    name,
    height,
    FIRST_VALUE(height) OVER(ORDER BY height DESC) AS 'first value',
    FIRST_VALUE(height) OVER(ORDER BY height DESC) - height AS 'fvdiff'    
FROM usertbl;
