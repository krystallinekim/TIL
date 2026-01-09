-- 서브쿼리
-- 한 SQL 구문 안에 다른 SQL 구문을 넣는 것

-- 서브쿼리 없이 구현
SELECT 
    dept_code 
FROM employee 
WHERE emp_name = '노옹철';  -- D9

SELECT 
    emp_name,
    dept_code
FROM employee
WHERE dept_code = 'D9';  -- 원하는 테이블

-- >> 서브쿼리 사용

SELECT 
    emp_name,
    dept_code
FROM employee
WHERE dept_code = (
    SELECT 
        dept_code 
    FROM employee 
    WHERE emp_name = '노옹철'
);

-- -------------------------------------------------------------------------------------------------------------------------
-- 1. 단일행
-- 서브쿼리의 결과가 단일 행인 경우

-- 1-Q-1. 전 직원의 평균 급여보다 많은 급여를 받는 직원들의 정보(사번, 직원명, 직급코드, 급여)
SELECT AVG(salary) FROM employee;  -- 전 직원의 평균 급여

SELECT
    emp_id AS `사번`,
    emp_name AS `직원명`,
    job_code AS `직급코드`,
    salary AS `급여`
FROM employee
WHERE salary > (SELECT AVG(salary) FROM employee);

-- 1-Q-2. 노옹철 사원의 급여보다 많이 받는 직원들의 정보(사번, 직원명, 부서명, 급여)
SELECT salary FROM employee WHERE emp_name = '노옹철';  -- 노옹철 사원의 급여

SELECT
    e.emp_id AS `사번`,
    e.emp_name AS `직원명`,
    d.dept_title AS `부서명`,
    e.salary AS `급여`
FROM employee e
LEFT JOIN department d ON e.dept_code = d.dept_id
WHERE e.salary > (SELECT salary FROM employee WHERE emp_name = '노옹철');


SELECT
    dept_code AS `부서코드`,
    SUM(salary) AS `급여합`
FROM employee
GROUP BY dept_code
ORDER BY `급여합` DESC LIMIT 1;

-- -------------------------------------------------------------------------------------------------------------------------
-- 2. 다중행
-- 서브쿼리의 결과가 여러 행일 경우

-- 2-Q-1. 각 부서별 최고 급여를 받는 직원의 정보(이름, 직급코드, 부서코드, 급여)
SELECT MAX(salary) FROM employee GROUP BY dept_code;  -- 부서별 최고 급여

SELECT
    emp_name AS `이름`,
    job_code AS `직급코드`, 
    dept_code AS `부서코드`,
    salary AS `급여`
FROM employee
WHERE salary IN (SELECT MAX(salary) FROM employee GROUP BY dept_code)
ORDER BY salary;  -- 사실 이 코드는 문제가 많음. 다른 부서에서 같은 급여를 받으면 같이 잡힘

-- 
SELECT 
    emp_name AS `이름`,
    job_code AS `직급코드`, 
    dept_code AS `부서코드`,
    salary AS `급여`
FROM (
    SELECT 
        *, 
        RANK() OVER(PARTITION BY dept_code ORDER BY salary) AS `rank` 
    FROM employee) AS `rank_table`
WHERE `rank` = 1;  -- 이게 맞다

-- 2-Q-2. 직원들의 사번, 직원명, 부서코드, 구분(사원/사수) 포함
SELECT DISTINCT manager_id FROM employee WHERE manager_id IS NOT NULL;  -- 사수의 사번

SELECT
    emp_id AS `사번`,
    emp_name AS `직원명`,
    dept_code AS `부서코드`,
--     CASE
--         WHEN emp_id IN (
--             SELECT 
--                 DISTINCT manager_id 
--             FROM employee 
--             WHERE manager_id IS NOT NULL
--         ) THEN '사수'
--         ELSE '사원'
--     END AS `구분`
    IF(emp_id IN (
        SELECT 
            DISTINCT manager_id 
        FROM employee 
        WHERE manager_id IS NOT NULL
    ), '사수', '사원') AS `구분`
FROM employee;



-- -------------------------------------------------------------------------------------------------------------------------
-- 3. 다중열
-- 서브쿼리의 결과가 여러 열일 경우




-- -------------------------------------------------------------------------------------------------------------------------
-- 4. 다중행 다중열
-- 서브쿼리의 결과가 테이블일 경우

































