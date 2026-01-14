-- 프로시저

-- 1. 프로시저 생성
DELIMITER $$
CREATE PROCEDURE userProc()
BEGIN
    SELECT * FROM usertbl; 
END $$
DELIMITER ;

CALL userProc();

-- 1-1. 매개변수

-- 이름을 입력받아 조회하는 프로시저
DELIMITER $$
CREATE OR REPLACE PROCEDURE userProc(
    IN username VARCHAR(10)  -- 변수 선언 파트, IN은 외부에서 받는 변수
)
BEGIN
    SELECT * FROM usertbl WHERE name = username; 
END $$
DELIMITER ;

CALL userProc('성시경');


-- DBMS 변수
SET @gender = 'M';
SELECT @gender;


-- 사용자의 ID를 입력받아서 이름을 돌려주는 프로시저
DELIMITER $$
CREATE OR REPLACE PROCEDURE userProc(
    IN id CHAR(8),
    OUT username VARCHAR(20)  -- 외부로 내보낼 변수명
)
BEGIN
    SELECT `name`
    INTO username  -- name을 username에 할당
    FROM usertbl 
    WHERE userID = id;
END $$
DELIMITER ;

CALL userProc('BBK', @uname);  -- @변수(전역)에 할당

SELECT @uname;


-- 조건문
-- IF

DELIMITER $$
CREATE OR REPLACE PROCEDURE empProc(
    IN id CHAR(3)
)
BEGIN
    DECLARE hyear INT;  -- 지역변수 선언
    
    SELECT YEAR(hire_date) 
    INTO hyear 
    FROM employee 
    WHERE emp_id = id;

    IF hyear < 2000 THEN 
        SELECT '1990년대 입사';
    ELSEIF hyear < 2010 THEN 
        SELECT '2000년대 입사';
    ELSEIF hyear < 2020 THEN 
        SELECT '2010년대 입사';
    ELSE SELECT '기타';
    END IF;

END $$
DELIMITER ;

CALL empProc('204');

-- CASE

DELIMITER $$
CREATE OR REPLACE PROCEDURE gradeProc(
    IN score TINYINT
)
BEGIN
    DECLARE grade CHAR(1);

    CASE
        WHEN score >= 90 THEN
            SET grade = 'A';
        WHEN score >= 80 THEN
            SET grade = 'B';
        WHEN score >= 70 THEN
            SET grade = 'C';
        WHEN score >= 60 THEN
            SET grade = 'D';
        ELSE
            SET grade = 'F';
    END CASE;

    SELECT score AS '점수', grade AS '등급';
END $$
DELIMITER ;

CALL gradeProc(59);








