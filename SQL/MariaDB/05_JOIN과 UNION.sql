-- JOIN/UNION

SELECT * FROM usertbl u
JOIN buytbl b ON b.userID = u.userID;

SELECT * FROM employee;

-- 실습: 사번, 직원명, 부서명, 직책명 한번에 나타내기
SELECT
    employee.emp_id AS `사번`,
    employee.emp_name AS `직원명`,
    department.dept_title AS `부서명`,
    job.job_name AS `직책`
FROM employee
LEFT JOIN department ON employee.dept_code = department.dept_id
LEFT JOIN job ON employee.job_code = job.job_code;


-- Natural JOIN
SELECT * FROM employee
NATURAL JOIN department;  -- 같은 이름을 가진 열을 기준으로 자동 JOIN
-- 같은 이름이 여러개이거나, 같은 이름이 없으면 제대로 작동하지 않음

-- 실습: usertbl | buytbl -> JYP의 이름, 주소, 연락처, 주문상품 이름 조회
SELECT
    usertbl.name AS `이름`,
    usertbl.addr AS `주소`,
    CONCAT_WS('-', usertbl.mobile1, usertbl.mobile2, usertbl.mobile3) AS `연락처`,
    buytbl.prodName AS `주문상품`
FROM usertbl
JOIN buytbl ON usertbl.userID = buytbl.userID
WHERE usertbl.userID = 'JYP';


-- 실습
-- 1. employee | department ->  보너스를 받는 사원들의 사번, 직원명, 보너스, 부서명 조회
SELECT
    employee.emp_id AS `사번`,
    employee.emp_name AS `직원명`,
    employee.bonus AS `보너스`,
    department.dept_title AS `부서명`
FROM employee
LEFT JOIN department ON employee.dept_code = department.dept_id
WHERE employee.bonus IS NOT NULL;

-- 2. employee | department -> 인사관리부가 아닌 사원들의 직원명, 부서명, 급여 조회
SELECT
    employee.emp_name AS `직원명`,
    department.dept_title AS `부서명`,
    employee.salary AS `급여`
FROM employee
LEFT JOIN department ON employee.dept_code = department.dept_id
WHERE department.dept_title != '인사관리부';

-- 3. employee | department | job -> 사번, 직원명, 부서명, 직급명 조회
SELECT
    employee.emp_id AS `사번`,
    employee.emp_name AS `직원명`,
    department.dept_title AS `부서명`,
    job.job_name AS `직급명`
FROM employee
LEFT JOIN department ON employee.dept_code = department.dept_id
LEFT JOIN job ON employee.job_code = job.job_code;


-- 실습: 사번, 직원명, 부서코드, 사수 사번, 사수명
SELECT
    d.dept_title AS `부서명`,
    e.emp_id AS `사번`,
    e.emp_name AS `직원명`,
    m.emp_id AS `사수사번`,
    m.emp_name AS `사수명`
FROM employee e
JOIN employee m ON e.manager_id = m.emp_id
LEFT JOIN department d ON e.dept_code = d.dept_id;

-- NON EQUAL(비등가) JOIN: 등호를 사용하지 않는 JOIN
SELECT * FROM employee;
SELECT * FROM sal_grade;
    
-- employee의 salary가 sal_grade의 min~max 사이에 있을 때 sal_level을 구하기
SELECT
    e.emp_name AS `직원명`,
    e.salary AS `급여`,
    s.sal_level AS `급여등급`
FROM employee e
LEFT JOIN sal_grade s ON e.salary BETWEEN s.min_sal AND s.max_sal
-- LEFT JOIN sal_grade s ON s.min_sal <= e.salary AND s.max_sal > e.salary
ORDER BY `급여` DESC;


-- 실습
-- 1. 이름에 '형'자가 들어있는 직원들의 사번, 직원명, 직급명을 조회하세요.
SELECT
    e.emp_id   AS `사번`,
    e.emp_name AS `직원명`,
    j.job_name AS `직급명`
FROM employee e 
LEFT JOIN job j ON e.job_code = j.job_code 
WHERE e.emp_name LIKE '%형%';


-- 2. 70년대생 이면서 여자이고, 성이 전 씨인 직원들의 직원명, 주민번호, 부서명, 직급명을 조회하세요.
SELECT
    e.emp_name   AS `직원명`,
    e.emp_no     AS `주민번호`,
    d.dept_title AS `부서명`,
    j.job_name   AS `직급명`
FROM employee e
LEFT JOIN department d ON e.dept_code = d.dept_id
LEFT JOIN job j        ON e.job_code = j.job_code
WHERE 
    LEFT(e.emp_no, 1) = 7 AND 
    SUBSTRING(e.emp_no, 8, 1) IN (2, 4) AND 
    LEFT(e.emp_name,1) = '전';


-- 3. 각 부서별 평균 급여를 조회하여 부서명, 평균 급여를 조회하세요. 단, 부서 배치가 안된 사원들의 평균도 같이 나오게끔 조회해 주세요.
SELECT
    d.dept_title            AS `부서명`,
    ROUND(AVG(e.salary),-1) AS `평균 급여`
FROM employee e
LEFT JOIN department d ON e.dept_code = d.dept_id
GROUP BY d.dept_title;


-- 4. 각 부서별 총 급여의 합이 1000만원 이상인 부서명, 급여의 합을 조회하세요.
SELECT
    d.dept_title  AS `부서명`,
    SUM(e.salary) AS `급여합`
FROM employee e
JOIN department d ON e.dept_code = d.dept_id
GROUP BY d.dept_title;


-- 5. 해외영업팀에 근무하는 직원들의 직원명, 직급명, 부서 코드, 부서명을 조회하세요.
SELECT
    e.emp_name   AS `직원명`,
    j.job_name   AS `직급명`,
    e.dept_code  AS `부서 코드`,
    d.dept_title AS `부서명`
FROM employee e
JOIN department d ON e.dept_code = d.dept_id
JOIN job j        ON e.job_code = j.job_code
WHERE d.dept_title LIKE '해외영업%';


-- 6. 테이블을 다중 JOIN 하여 사번, 직원명, 부서명, 지역명, 국가명 조회하세요.
SELECT
    e.emp_id        AS `사번`,
    e.emp_name      AS `직원명`,
    d.dept_title    AS `부서명`,
    l.local_name    AS `지역명`,
    n.national_name AS `국가명`
FROM employee e
LEFT JOIN department d ON e.dept_code = d.dept_id
LEFT JOIN location l   ON d.location_id = l.local_code
LEFT JOIN national n   ON l.national_code = n.national_code;


-- 7. 테이블을 다중 JOIN 하여 사번, 직원명, 부서명, 지역명, 국가명, 급여 등급 조회하세요.
SELECT
    e.emp_id        AS `사번`,
    e.emp_name      AS `직원명`,
    d.dept_title    AS `부서명`,
    l.local_name    AS `지역명`,
    n.national_name AS `국가명`,
    s.sal_level     AS `급여등급`
FROM employee e
LEFT JOIN department d ON e.dept_code = d.dept_id
LEFT JOIN location l   ON d.location_id = l.local_code
LEFT JOIN national n   ON l.national_code = n.national_code
LEFT JOIN sal_grade s  ON e.salary BETWEEN s.min_sal AND s.max_sal;


-- 8. 부서가 있는 직원들의 직원명, 직급명, 부서명, 지역명을 조회하세요.
SELECT
    e.emp_name   AS `직원명`,
    j.job_name   AS `직급명`,
    d.dept_title AS `부서명`,
    l.local_name AS `지역명`
FROM employee e
JOIN department d    ON e.dept_code = d.dept_id
LEFT JOIN job j      ON e.job_code = j.job_code
LEFT JOIN location l ON d.location_id = l.local_code;


-- 9. 한국과 일본에서 근무하는 직원들의 직원명, 부서명, 지역명, 근무 국가를 조회하세요.
SELECT
    e.emp_name      AS `직원명`,
    d.dept_title    AS `부서명`,
    l.local_name    AS `지역명`,
    n.national_name AS `근무 국가`
FROM employee e
LEFT JOIN department d ON e.dept_code = d.dept_id
LEFT JOIN location l   ON d.location_id = l.local_code
LEFT JOIN national n   ON l.national_code = n.national_code
WHERE n.national_name IN ('한국', '일본');



-- UNION
SELECT * FROM employee WHERE ent_date IS NOT NULL

UNION

SELECT * FROM employee WHERE salary > 3000000;













