USE lecture;
SELECT * FROM students;

-- ORDER BY : 특정 컬럼을 기준으로 정렬함
-- 기본 정렬 방식은 (PK인 id 기준으로) (오름차순 정렬)
-- ASC / DESC = 오름차순(뒤로갈수록 값이 커짐) / 내림차순(뒤로갈수록 값이 작아짐)

-- 이름순 정렬 (ASC는 생략 가능)
SELECT * FROM students ORDER BY name;
SELECT * FROM students ORDER BY name DESC;