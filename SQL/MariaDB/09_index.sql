-- 인덱스
SELECT * FROM employees LIMIT 10;


-- PK
EXPLAIN SELECT * FROM employees;  
-- type: ALL(Full table scan) / key: NULL

EXPLAIN SELECT * FROM employees WHERE emp_no = 293939;  
-- type: const(클러스터형 인덱스 사용) / key: PRIMARY 
-- 검색 조건이 PK인 emp_no라서, PK를 이용해 검색함


-- 수동 인덱스
EXPLAIN SELECT * FROM employees WHERE first_name = 'moon';  -- 0.062s

CREATE INDEX idx_employees_first_name
          ON employees(first_name);

EXPLAIN SELECT * FROM employees WHERE first_name = 'moon';  -- 0.000s
-- type: ref(보조 인덱스) / key: idx_employees_first_name
-- 검색 대상 row 숫자도 300,000 -> 243으로 감소


-- UNIQUE: 중복된 값이 저장되었으면 고유인덱스는 생성X

CREATE UNIQUE INDEX idx_departments_dept_name
                 ON departments(dept_name);

EXPLAIN SELECT * FROM departments WHERE dept_name = 'Human Resources';


-- 여러 열을 묶어서 인덱스
EXPLAIN SELECT * FROM employees 
WHERE first_name = 'moon' AND last_name = 'Yetto';

CREATE INDEX idx_employees_names
          ON employees(first_name, last_name);

-- 인덱스 삭제
DROP INDEX idx_employees_first_name ON employees;

-- 인덱스가 있는데 사용하지 않는 경우
-- 1. 전체 데이터 조회 시
EXPLAIN SELECT * FROM employees;
-- 2. 조회 조건의 범위가 넓을 때(전체의 n% 이상일 때)
EXPLAIN SELECT * FROM employees WHERE emp_no < 300000;
-- 3. 조회 조건에 연산식이 들어갈 때
EXPLAIN SELECT * FROM employees WHERE emp_no * 1 = 100000;



-- 중복도가 높은 데이터일 경우
ALTER TABLE employees ADD INDEX idx_employees_gender(gender);
EXPLAIN SELECT * FROM employees WHERE gender = 'M';
-- 있어도 그냥 풀스캔(ALL)을 돌림



