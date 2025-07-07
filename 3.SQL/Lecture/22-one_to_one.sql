USE lecture;

-- 1:1 구성
-- employees
CREATE TABLE employees(
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  hire_date DATE NOT NULL
);
-- employee_details
CREATE TABLE employee_details(
  emp_id INT PRIMARY KEY,
  social_number VARCHAR(20) UNIQUE,
  address TEXT,
  salary DECIMAL(20),
  FOREIGN KEY (emp_id) REFERENCES employees(id) ON DELETE CASCADE	-- 1:1 관계
);


INSERT INTO employees VALUES
(1, '김철수', '2023-01-01'),
(2, '이영희', '2023-02-01'),
(3, '박민수', '2023-03-01');

INSERT INTO employee_details VALUES
(1, '123456-1234567', '서울시 강남구', 5000000),
(2, '234567-2345678', '서울시 서초구', 4500000),
(3, '345678-3456789', '부산시 해운대', 4000000);

SELECT * FROM employees;
SELECT * FROM employee_details;
-- 김철수의 주소는?

-- 직원테이블에서 이름이 김철수인 사람과 id가 같은 ed 테이블의 주소

-- 1. 서브쿼리
SELECT address FROM employee_details
WHERE emp_id = (
	SELECT id FROM employees WHERE name = '김철수'	-- 타고간다는 의미에서는 훨씬 직관적임 / 길어지면 귀찮아짐
);

-- 2. JOIN
SELECT ed.address 
FROM employees e
JOIN employee_details ed ON e.id = ed.emp_id
WHERE e.name = '김철수';

-- 1:1은 굳이 쪼갤 이유가 있는가 라는 의문이 계속 든다.
-- 민감한 데이터를 따로 처리하고 싶을 경우 / 데이터에 NULL값이 너무 많은게 꼴보기 싫을 경우