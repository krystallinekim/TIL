USE lecture;

-- 모든 컬럼, 모든 레코드
SELECT * FROM members;

-- 모든 컬럼, id=2
SELECT * FROM members WHERE id=2;

-- 이름 | 이메일, 모든 레코드
SELECT name,email FROM members;

-- 이름, 이름=김정수
SELECT name FROM members WHERE name='김정수';

-- 

