-- 인덱스
SELECT * FROM employees LIMIT 10;


EXPLAIN SELECT * FROM employees;  
-- type: ALL(Full table scan) / key: NULL

EXPLAIN SELECT * FROM employees WHERE emp_no = 293939;  
-- type: const(클러스터형 인덱스 사용) / key: PRIMARY 
-- 검색 조건이 PK인 emp_no라서, PK를 이용해 검색함


-- 수동 인덱스 생성
EXPLAIN SELECT * FROM employees WHERE first_name = 'moon';  -- 0.062s

CREATE INDEX idx_employees_first_name
          ON employees(first_name);

EXPLAIN SELECT * FROM employees WHERE first_name = 'moon';  -- 0.000s
-- type: ref(보조 인덱스) / key: idx_employees_first_name
-- 검색 대상 row 숫자도 300,000 -> 243으로 감소




