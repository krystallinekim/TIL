-- SELECT column FROM table WHERE condition ORDER BY order LIMIT numbers
-- 특정 컬럼을 가져오기 / 어디서? 어떤 테이블에서 / 특정 조건 기준으로 / 어떤 순서로 / n개까지만
USE lecture;
SELECT * FROM students;
DESC students;

-- 컬럼에서 값이 특정 값인 데이터 가져오기(중복 가능함)
SELECT * FROM students WHERE name = '이도윤';
-- 값이 n 이상(>=), 이하(<=), 초과(>), 미만(<)
SELECT * FROM students WHERE age <= 30;
-- 값이 n이 아님(<> or !=)
SELECT * FROM students WHERE id != 1;
-- 값이 n1과 n2 사이
SELECT * FROM students WHERE age BETWEEN 20 AND 40;
SELECT * FROM students WHERE age >= 20 AND age <= 40;
-- 특정 리스트에 포함된 값 반환
SELECT * FROM students WHERE id IN (1,3,4,7);

-- 유사조건 LIKE
-- %(있을수도, 없을수도 있다 -> 와일드카드처럼)
SELECT * FROM students WHERE name LIKE '김%';
SELECT * FROM students WHERE name LIKE '%윤%';
-- _(빈칸, 정확히 개수만큼 글자가 있다)
SELECT * FROM students WHERE name LIKE '윤__';

