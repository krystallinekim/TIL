-- INSERT 실습
-- usertbl 테이블에 한 개의 행을 삽입
INSERT INTO usertbl(`userid`, `name`, `birthYear`, `addr`)
VALUES ('HGD', '홍길동', 2015, '서울');

-- 열의 순서와 데이터 타입이 맞지 않아서 에러가 발생한다.
INSERT INTO usertbl(`userid`, `name`, `birthYear`, `addr`)
VALUES ('LEE', 2015, '이몽룡', '강원');

-- 기본 키 열에 중복된 값이 입력될 수 없어서 에러가 발생한다.
INSERT INTO usertbl(`userid`, `name`, `birthYear`, `addr`)
VALUES ('HGD', '길동이', 2005, '경기');

-- 기본 키 열에 NULL이 입력될 수 없어서 에러가 발생한다.
INSERT INTO usertbl(`name`, `birthYear`, `addr`)
VALUES ('이몽룡', 2015, '강원');

-- 모든 열에 저장될 값을 지정하지 않았기 때문에 에러가 발생한다.
INSERT INTO usertbl
VALUES ('LEE', '이몽룡', 2015, '강원');

-- usertbl 테이블에 한 개의 행을 삽입 (모든 열에 데이터 삽입)
INSERT INTO usertbl
VALUES ('LEE', '이몽룡', 2015, '강원', '010', '4567', '8901', NULL, CURDATE());

-- usertbl 테이블에 어러 개의 행을 삽입
INSERT INTO usertbl(`userid`, `name`, `birthYear`, `addr`)
VALUES ('LIM', '임꺽정', 1996, '경기'), ('SUNG', '성춘향', 2020, '강원');

-- 테이블 복사
-- 테이블의 구조만 복사한다. (데이터 제외)
CREATE TABLE usertbl_copy (
	SELECT *
	FROM usertbl
   WHERE 1 = 0
);

-- SELECT 결과를 usertbl_copy 테이블에 삽입
INSERT INTO usertbl_copy
	SELECT *
	FROM usertbl
	WHERE addr = '서울';

-- SELECT로 조회한 열의 개수가 테이블의 열의 개수보다 적어서 에러가 발생한다.
INSERT INTO usertbl_copy (
	SELECT `userid`, 
	       `name`
	FROM usertbl
	WHERE addr = '강원'
);

-- birthYear, addr 열에 NULL이 입력될 수 없어서 에러가 발생한다.
INSERT INTO usertbl_copy(`userid`, `name`) (
	SELECT `userid`, 
	       `name`
	FROM usertbl
	WHERE addr = '강원'
);

-- SELECT 결과를 usertbl_copy 테이블에 삽입
INSERT INTO usertbl_copy(`userid`, `name`, `birthYear`, `addr`) (
	SELECT `userid`, 
	       `name`,
	       `birthYear`, 
			 `addr`
	FROM usertbl
	WHERE addr = '강원'
);

-- UPDATE 실습
UPDATE usertbl
SET `name` = '고길동'
WHERE `userid` = 'HGD';

-- 테스트 테이블 생성
CREATE TABLE emp_salary(
	SELECT emp_id,
	       emp_name,
	       salary,
	       bonus
	FROM employee
);

-- WHERE 절을 작성하지 않으면 모든 행이 변경된다.
UPDATE emp_salary
SET emp_name = '홍길동';

-- 모든 사원의 급여를 기존 급여에서 10프로 인상한 금액으로 변경
UPDATE emp_salary
SET salary = salary * 1.1;

-- DELETE 실습
-- usertbl 테이블에서 userid가 HGD인 회원을 삭제
-- SELECT *
DELETE
FROM usertbl
WHERE userid = 'HGD';

-- usertbl 테이블에서 mobile1이 NULL인 회원들 중 상위 2명을 삭제
-- SELECT *
DELETE
FROM usertbl
WHERE mobile1 IS NULL
ORDER BY addr
LIMIT 2;

-- emp_salary 테이블의 모든 데이터 삭제
-- WHERE 절을 작성하지 않으면 모든 행이 삭제된다.
SELECT *
-- DELETE
FROM emp_salary;

-- 테이블 삭제
DROP TABLE emp_salary;

-- TCL(Transaction Control Language)
-- COMMIT, ROLLBACK
SELECT @@AUTOCOMMIT;

SHOW VARIABLES LIKE 'AUTOCOMMIT%';

SET AUTOCOMMIT = 1; -- 활성화
SET AUTOCOMMIT = 0; -- 비활성화

-- AUTOCOMMIT이 활성화된 경우 ROLLBACK으로 실행이 취소되지 않는다.
DELETE 
-- SELECT *
FROM usertbl
WHERE userid = 'LEE';

ROLLBACK;

-- AUTOCOMMIT이 비활성된 경우 ROLLBACK으로 실행이 취소된다.
UPDATE usertbl
SET `name` = '바보킴'
WHERE `userid` = 'BBK';

ROLLBACK;

-- AUTOCOMMIT이 비활성된 경우 변경 사항을 반영하려면 COMMIT을 실행해야 한다.
UPDATE usertbl
SET `name` = '바보킴'
WHERE `userid` = 'BBK';

COMMIT;