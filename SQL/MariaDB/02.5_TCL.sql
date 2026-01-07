USE employees_db;
-- TCL(Transaction Control Language)

-- AUTOCOMMIT(자동저장)
-- 켜져있을 경우 자동으로 DB에 저장함.
-- AUTOCOMMIT이 켜져있으면 롤백/커밋이 의미가 없다.
SELECT @@AUTOCOMMIT;

SHOW VARIABLES LIKE 'AUTOCOMMIT%';

SET AUTOCOMMIT = 0;  -- 비활성화 
SET AUTOCOMMIT = 1;  -- 활성화


SELECT * FROM usertbl WHERE userid = 'HGD';

INSERT INTO usertbl(`userid`, `name`, `birthYear`, `addr`) VALUES ('HGD', '홍길동', 2015, '서울');

DELETE FROM usertbl WHERE userid = 'HGD';

-- ROLLBACK(되돌리기)
ROLLBACK;

-- COMMIT(저장)
COMMIT;
