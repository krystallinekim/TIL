-- View
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
-- 1. 생성

CREATE VIEW v_employee
AS SELECT
    e.emp_id     AS `사번`,
    e.emp_name   AS `직원명`,
    d.dept_title AS `부서명`,
    j.job_name   AS `직급명`,
    e.hire_date  AS `입사일`
FROM employee e
LEFT JOIN department d ON e.dept_code = d.dept_id
LEFT JOIN job j        ON e.job_code = j.job_code;

SELECT * FROM v_employee WHERE `부서명` IS NULL;


CREATE OR REPLACE VIEW v_employee
AS SELECT
    emp_id,
    emp_name,
    CASE
        WHEN SUBSTRING(emp_no, 8, 1) IN ('1', '3') THEN 'M'
        WHEN SUBSTRING(emp_no, 8, 1) IN ('2', '4') THEN 'F'
        ELSE 'Unknown'
    END AS `gender`,
    salary
FROM employee;

SELECT emp_name, gender FROM v_employee;
-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
-- 2. 수정
CREATE VIEW v_userbuytbl
AS SELECT
    u.userID,
    u.name,
    b.prodName,
    u.addr,
    CONCAT_WS('-', u.mobile1, u.mobile2, u.mobile3)
FROM usertbl u
JOIN buytbl b ON u.userID = b.userID;

SELECT * FROM v_userbuytbl;

ALTER VIEW v_userbuytbl
AS SELECT
    u.userID,
    u.name,
    b.prodName,
    u.addr,
    CONCAT_WS('-', u.mobile1, u.mobile2, u.mobile3) AS `mobile`
FROM usertbl u
JOIN buytbl b ON u.userID = b.userID;

-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
-- 3. DML(INSERT, UPDATE, DELETE)

CREATE VIEW v_job
AS SELECT * FROM job;

SELECT * FROM v_job;
SELECT * FROM job;

-- INSERT
INSERT INTO v_job VALUES ('J8', '계약직');

-- UPDATE
UPDATE v_job
SET job_name = '인턴'
WHERE job_code = 'J8';

-- DELETE
DELETE FROM v_job
WHERE job_code = 'J8';

-- DML이 불가능한 경우
-- 1. 뷰의 정의에 포함되지 않은 열을 조작하는 경우

CREATE OR REPLACE VIEW v_job
AS SELECT job_code FROM job;

-- job_name은 뷰에 정의되지 않아, DML이 불가능함
SELECT job_name FROM v_job;
INSERT INTO v_job VALUES ('J8', '계약직');
UPDATE v_job SET job_name = '인턴' WHERE job_code = 'J7';
DELETE FROM v_job WHERE job_name = '사원';

-- 2. 산술연산으로 정의된 열을 조작하는 경우
CREATE VIEW v_emp_salary
AS SELECT
    emp_id,
    emp_name,
    emp_no,
    salary * 12 AS salary
FROM employee;

SELECT * FROM v_emp_salary;
INSERT INTO v_emp_salary (emp_id, emp_name, emp_no) VALUES ('999', '이름', '000000-0000000');
UPDATE v_emp_salary SET salary = 50000000 WHERE emp_id = '999';
DELETE FROM v_emp_salary WHERE salary = 57600000;  -- DELETE는 가상열을 조작하는 게 아니라, 조회 조건으로만 사용하기 때문에 삭제하는 건 가능


-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
-- 4. WITH CHECK OPTION - 서브쿼리에 기술된 조건에 부합하지 않는 값으로 수정하는 경우 오류를 발생시킴
CREATE OR REPLACE VIEW v_employee
AS SELECT * 
    FROM employee 
    WHERE salary >= 3000000
    WITH CHECK OPTION;

SELECT * FROM v_employee;

UPDATE v_employee SET salary = 8000000 WHERE emp_id = '200';


-- ----------------------------------------------------------------------------------------------------------------------------------------------------------
-- 5. 삭제
DROP VIEW v_userbuytbl;
DROP VIEW v_employee, v_emp_salary, v_job;