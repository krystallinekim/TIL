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

-- 2-Q-3. 대리 직급 중, 과장의 최소 급여보다 많이 받는 직원의 사번, 이름, 직급코드, 급여 조회
SELECT 
    salary
FROM employee e 
LEFT JOIN job j ON e.job_code = j.job_code
WHERE j.job_name = '과장';  -- 과장의 급여

-- ANY: 서브쿼리의 결과 중 하나라도 만족하면 참
SELECT
    e.emp_id AS `사번`,
    e.emp_name AS `이름`,
    j.job_name AS `직급`,
    e.salary AS `급여`
FROM employee e 
LEFT JOIN job j ON e.job_code = j.job_code
WHERE 
    j.job_name = '대리' AND
--     e.salary > 2200000;  -- 최소값보다 크면 됨
--     (e.salary > 2200000 OR e.salary > 2500000 OR e.salary > 3760000);  -- 전체 리스트 중 아무 값이나보다 크면 됨 
    e.salary > ANY(  -- 하나라도 안의 값보다 크면 참 = 최소값보다만 크면 만족
        SELECT 
            salary
        FROM employee e 
        LEFT JOIN job j ON e.job_code = j.job_code
        WHERE j.job_name = '과장'
    );

-- 2-Q-4. 과장 직급 중, 차장의 최대 급여보다 많이 받는 직원의 사번, 이름, 직급코드, 급여 조회

-- ALL: 서브쿼리의 결과 모두가 조건을 만족할 시 참
SELECT
    e.emp_id AS `사번`,
    e.emp_name AS `이름`,
    j.job_name AS `직급`,
    e.salary AS `급여`
FROM employee e 
LEFT JOIN job j ON e.job_code = j.job_code
WHERE 
    j.job_name = '과장' AND
--     e.salary > 2490000;
--     (e.salary > 2800000 AND e.salary > 1550000 AND e.salary >2490000 AND e.salary > 2480000);
    e.salary > ALL(  -- 이번엔 모든 값보다 커야 최대값
        SELECT 
            salary
        FROM employee e 
        LEFT JOIN job j ON e.job_code = j.job_code
        WHERE j.job_name = '차장'
    );

-- ANY, ALL -> 여러 값들과 비교할 때 사용하는 연산자, 단 서브쿼리에서만 사용


-- EXISTS: 서브쿼리의 결과가 한 건이라도 존재하면 참
-- 2-Q-5. 한 번이라도 구매 기록이 있는 회원의 ID, 이름, 주소
-- SELECT DISTINCT userID FROM buytbl;  -- 구매 기록이 있는 회원의 ID
SELECT * FROM buytbl WHERE userid = 'BBK'/*, 'KBS', ... */;


SELECT
    u.userID AS `ID`,
    u.name AS `이름`,
    u.addr AS `주소`
FROM usertbl u 
WHERE EXISTS (  -- 서브쿼리에서 메인쿼리의 열을 가져다 씀
    SELECT 
        * 
    FROM buytbl b
    WHERE b.userid = u.userid  -- 이게 조회된 기록이 있으면 참임
);
-- WHERE u.userID IN (SELECT DISTINCT userID FROM buytbl);

-- 그냥 JOIN 써도 됨
SELECT
    DISTINCT u.userID AS `ID`,
    u.name AS `이름`,
    u.addr AS `주소`
FROM usertbl u
JOIN buytbl b ON b.userid = u.userid;


-- -------------------------------------------------------------------------------------------------------------------------
-- 3. 다중열
-- 서브쿼리의 결과가 여러 열일 경우

-- 괄호를 써서 여러 열을 한번에 처리

-- 3-Q-1. '하이유'와 같은 부서코드, 직급코드를 가진 사원을 조회
SELECT
    emp_name,
    dept_code,
    job_code
FROM employee 
WHERE 
    (dept_code, job_code) IN (SELECT dept_code, job_code FROM employee WHERE emp_name = '하이유');  -- 단일값이 아니라 쌍으로 묶어서 비교해야 함.
--     dept_code = (SELECT dept_code FROM employee WHERE emp_name = '하이유') AND 
--     job_code = (SELECT job_code FROM employee WHERE emp_name = '하이유');


-- 3-Q-2. '박나라' 와 직급코드가 일치하면서 사수가 같은 사원들의 사번, 직원명, 직급코드, 사수사번 조회

SELECT
    emp_id AS `사번`,
    emp_name AS `직원명`,
    job_code AS `직급코드`,
    manager_id AS `사수사번`
FROM employee
WHERE
    (job_code, manager_id) = (SELECT job_code, manager_id FROM employee WHERE emp_name = '박나라');


-- -------------------------------------------------------------------------------------------------------------------------
-- 4. 다중행 다중열
-- 서브쿼리의 결과가 여러 행, 여러 열일 경우

-- 4-Q-1. 각 부서별 최고 급여를 받는 직원의 정보(사번, 직원명, 부서코드, 급여)

SELECT 
    emp_id AS `사번`,
    emp_name AS `이름`,
    IFNULL(dept_code, '부서없음') AS `부서코드`,
    salary AS `급여`
FROM employee
WHERE (IFNULL(dept_code, '부서없음'), salary) IN(
    SELECT 
        IFNULL(dept_code, '부서없음') AS `부서코드`,
        MAX(salary)
    FROM employee
    GROUP BY `부서코드`
)
ORDER BY `부서코드`;  
-- 부서코드가 NULL인 행이 나오지 않음 -> NULL값은 검색되지 않기 때문에
-- 100 IN (NULL, 100, 200, 300) -> 100 = NULL (X), 100 = 100 (O), 100 = 200 (X), 100 = 300 (X)
-- NULL IN (NULL, 100, 200, 300) -> NULL = NULL(X), NULL = 100 (X), NULL = 200 (X), NULL = 300 (X)
-- >> IN은 동등비교를 하기 때문에, NULL값을 넣고 등호로 비교할 수 없어 검색되지 않음.
-- >> dept_code를 쓰는데에 전부 IFNULL로 NULL값을 제거해 주면 된다.

-- 4-Q-2. 각 직급별 최소 급여를 받는 직원의 정보(사번, 직원명, 직급코드, 급여)

SELECT 
    emp_id AS `사번`, 
    emp_name AS `직원명`, 
    job_code AS `직급코드`, 
    salary AS `급여` 
FROM employee
WHERE (job_code, salary) IN(
    SELECT 
        job_code,
        MIN(salary)
    FROM employee
    GROUP BY job_code
)
ORDER BY job_code;



-- -------------------------------------------------------------------------------------------------------------------------
-- 5. 인라인 뷰
-- 실제 테이블처럼 서브쿼리를 이용 - FROM 절에 서브쿼리를 넣고 테이블 대신 사용하는 것

SELECT 
    emp.emp_id, 
    emp.emp_name,
    emp.monthly_salary,
    emp.annual_salary
FROM (
    SELECT
        emp_id,
        emp_name,
        salary AS `monthly_salary`,
        salary * 12 AS `annual_salary`
    FROM employee
) AS `emp`;


SELECT 
    emp_id AS `사번`, 
    emp_name AS `직원명`, 
    job_code AS `직급코드`, 
    salary AS `급여` 
FROM(
    SELECT 
        *, 
        RANK() OVER(PARTITION BY job_code ORDER BY salary) AS `rank` 
    FROM employee
) AS `rank_table`
WHERE rank = 1;




















