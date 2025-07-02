USE practice;
DESC userinfo;
SELECT * FROM userinfo;

-- userinfo에 email column 추가, char(40) 제한, 기본값 설정
ALTER TABLE userinfo ADD COLUMN email VARCHAR(40) DEFAULT 'ex@gmail.com';

-- nickname 데이터 타입을 varchar(100)으로 수정
ALTER TABLE userinfo MODIFY COLUMN nickname VARCHAR(100);

-- reg_date 삭제
ALTER TABLE userinfo DROP COLUMN reg_date;

-- 이메일 추가해봄
UPDATE userinfo SET email='mrowl@rustylake.com' WHERE id=4;
UPDATE userinfo SET email='ladyinlake@rustylake.com' WHERE id=2;
UPDATE userinfo SET email='detective@rustylake.com' WHERE id=3;
UPDATE userinfo SET email='parrot@rustylake.com' WHERE id=1;

-- email에 unique 제약조건 추가
ALTER TABLE userinfo MODIFY COLUMN email VARCHAR(40) UNIQUE;


-- UNIQUE INDEX 삭제(나중에 다시 할것)
SHOW INDEX FROM userinfo;
ALTER TABLE userinfo DROP INDEX email;