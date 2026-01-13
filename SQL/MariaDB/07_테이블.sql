-- -----------------------------------------------------------------------------------------
-- 1. 테이블 생성(CREATE)
DROP TABLE IF EXISTS member;

CREATE TABLE member(
    mem_no INT NOT NULL,
    mem_id VARCHAR(20) NOT NULL,
    mem_pass VARCHAR(20) NOT NULL,
    mem_name VARCHAR(10) NOT NULL,
    enroll_date DATE DEFAULT CURDATE()
);

-- 테이블에 샘플 데이터 추가
INSERT INTO member (mem_no, mem_id, mem_pass, mem_name)
VALUES (1, 'hong123', '0000', '홍길동');  -- 열을 생략하면 기본값으로 들어감

INSERT INTO member
VALUES (2, 'lee123', '0000', '이몽룡', NULL);  -- NULL도 줄 수 있음

INSERT INTO member
VALUES (3, 'seong123', '0000', '성춘향', '2026-01-11');  -- 특정 날짜 지정도 가능

INSERT INTO member
VALUES (4, 'lim123', '0000', '임꺽정', CURDATE());  -- 함수의 값도 지정 가능

INSERT INTO member
VALUES (5, 'kim123', '0000', '김삿갓');  -- 테이블 열 수와 맞지 않으면 에러 생김

INSERT INTO member (mem_no, mem_id, mem_pass)
VALUES (6, 'park123', '0000');  -- mem_name 열은 NOT NULL이라 빈값이면 삽입 불가능

-- 데이터 수정
UPDATE member
SET mem_id = NULL
WHERE mem_name = '홍길동';  -- mem_id가 NOT NULL이라 수정시 에러생김



-- -----------------------------------------------------------------------------------------
-- 2. 제약 조건
DROP TABLE IF EXISTS member;

CREATE TABLE member(
    mem_no INT AUTO_INCREMENT PRIMARY KEY,  -- 시스템에서 자동으로 PK 생성
    mem_id VARCHAR(20) NOT NULL UNIQUE,
    mem_pass VARCHAR(20) NOT NULL,
    mem_name VARCHAR(10) NOT NULL,
    enroll_date DATE DEFAULT CURDATE()
);

INSERT INTO member (mem_id, mem_pass, mem_name)
VALUES ('hong1', '0000', '홍길동');
VALUES ('hong2', '0000', '홍길동');
VALUES ('hong3', '0000', '홍길동');
VALUES ('hong4', '0000', '홍길동');

SELECT LAST_INSERT_ID(); -- 마지막으로 AUTO_INCREMENT된 ID

-- 제약조건 지정하기
DROP TABLE IF EXISTS member;

CREATE TABLE member(
    mem_no INT AUTO_INCREMENT,
    mem_id VARCHAR(20) NOT NULL,
    mem_pass VARCHAR(20) NOT NULL,
    mem_name VARCHAR(10) NOT NULL,
    enroll_date DATE DEFAULT CURDATE(),
    /*CONSTRAINT*/ PRIMARY KEY (mem_no, mem_id),  -- CONSTRAINT는 생략 가능 / 열 2개로 지정은 여기서 함
    /*CONSTRAINT*/ /*uq_member_mem_id*/ UNIQUE(mem_id)  -- UNIQUE도 여러개 열 묶어서 생성 가능 / 이름도 지정 가능함
);


-- -----------------------------------------------------------------------------------------
-- 2-1. 외래키
DROP TABLE IF EXISTS member_grade;
CREATE TABLE member_grade (
  grade_code VARCHAR(10) PRIMARY KEY,
  grade_name VARCHAR(10) NOT NULL
);

INSERT INTO member_grade VALUES ('vip', 'VIP');
INSERT INTO member_grade VALUES ('gold', '골드');
INSERT INTO member_grade VALUES ('normal', '일반');

SELECT * FROM member_grade;

DROP TABLE IF EXISTS member;

CREATE TABLE member (
    mem_no INT AUTO_INCREMENT PRIMARY KEY,
    mem_id VARCHAR(20) UNIQUE,
    mem_pass VARCHAR(20) NOT NULL,
    mem_name VARCHAR(10) NOT NULL,
    grade_code VARCHAR(10) REFERENCES member_grade/*(grade_code)*/,  -- 지정 안하면 그 테이블의 PK를 참조해옴
    enroll_date DATE DEFAULT CURDATE()
);

INSERT INTO member (mem_id, mem_pass, mem_name, grade_code)
VALUES ('hong123', '0000', '홍길동', 'vip');  -- 됨

INSERT INTO member (mem_id, mem_pass, mem_name, grade_code)
VALUES ('lee123', '0000', '이몽룡', 'bronze');  -- foreign key constraint fails, grade_code열의 3개 값만 넣을 수 있다.

INSERT INTO member (mem_id, mem_pass, mem_name, grade_code)
VALUES ('seong123', '0000', '성춘향', NULL);  -- NULL은 됨(NOT NULL 안줬으니까)

SELECT * FROM member;

-- 외래키로 연결해 놓으면 **부모 테이블**의 삭제/수정 시 오류 발생
UPDATE member_grade SET grade_code = 'vvip' WHERE grade_code = 'vip';
DELETE FROM member_grade WHERE grade_code = 'vvip';  

-- -----------------------------------------------------------------------------------------
-- 2-2. CHECK

DROP TABLE IF EXISTS member;

CREATE TABLE member (
    mem_no INT AUTO_INCREMENT PRIMARY KEY,
    mem_id VARCHAR(20) UNIQUE,
    mem_pass VARCHAR(20) NOT NULL,
    mem_name VARCHAR(10) NOT NULL,
    gender CHAR(2) CHECK(gender IN ('M', 'F')),
    age TINYINT,
    grade_code VARCHAR(10) REFERENCES member_grade,
    enroll_date DATE DEFAULT CURDATE(),
    /*CONSTRAINT ck_member_age */CHECK(age >= 0)
);

INSERT INTO member (mem_id, mem_pass, mem_name, gender, age, grade_code)
VALUES ('hong123', '0000', '홍길동', 'M', 20, 'vip');

INSERT INTO member (mem_id, mem_pass, mem_name, gender, age, grade_code)
VALUES ('lee123', '0000', '이몽룡', '남', 21, 'gold');

INSERT INTO member (mem_id, mem_pass, mem_name, gender, age, grade_code)
VALUES ('seong123', '0000', '성춘향', 'F', -22, 'normal');

UPDATE member SET gender = 'aa' WHERE mem_no = 1;  -- 업데이트도 체크에 위배되면 불가


-- -----------------------------------------------------------------------------------------
-- 3. 테이블 수정

-- 3-1. 열 추가/수정/삭제

-- 추가: ADD
ALTER TABLE usertbl 
ADD homepage VARCHAR(30);


ALTER TABLE usertbl 
ADD age TINYINT 
    DEFAULT 0 
    -- FIRST; -- 제일 앞에
    AFTER birthyear;

-- 수정: MODIFY
ALTER TABLE usertbl 
MODIFY gender CHAR(2) 
    DEFAULT 'M' 
    NOT NULL 
    CHECK(gender IN ('M', 'F'));

ALTER TABLE usertbl
MODIFY name CHAR(15)
    NOT NULL;

ALTER TABLE usertbl
MODIFY name CHAR(1)
    NOT NULL; -- Data truncated for column 'name', 이미 존재하는 데이터와 충돌됨

ALTER TABLE usertbl
MODIFY name INT
    NOT NULL;  -- Truncated incorrect INTEGER value

ALTER TABLE usertbl
MODIFY homepage INT;  -- 이건 안에 데이터가 없어서 수정 가능함


-- 열 이름 수정: RENAME COLUMN ~ TO ~
ALTER TABLE usertbl 
RENAME COLUMN name TO u_name;

-- MODIFY + RENAME
ALTER TABLE usertbl
CHANGE COLUMN u_name name VARCHAR(20) NOT NULL;

-- 열 삭제: DROP
ALTER TABLE usertbl 
DROP COLUMN gender;

ALTER TABLE usertbl 
DROP COLUMN userID;  -- buytbl에서 외래키라서, 참조되는 열이 있다면 삭제 불가 

DROP TABLE IF EXISTS dept_copy;
CREATE TABLE dept_copy(
    SELECT *
    FROM department
);

ALTER TABLE dept_copy 
DROP COLUMN location_id;  -- 테이블의 모든 열을 삭제하는 것도 불가. 테이블에는 항상 한 개의 열은 있어야 함



-- 3-2. 열 제약조건 추가/삭제(수정은 불가)
DROP TABLE IF EXISTS member, member_grade;

CREATE TABLE member_grade (
  grade_code VARCHAR(10),
  grade_name VARCHAR(10) NOT NULL
);

CREATE TABLE member (
    mem_no INT,
    mem_id VARCHAR(20) NOT NULL,
    mem_pass VARCHAR(20) NOT NULL,
    mem_name VARCHAR(10) NOT NULL,
    enroll_date DATE DEFAULT CURDATE()
);

-- 제약조건 추가: ADD CONSTRAINT

-- member_grade.grade_code + PK
ALTER TABLE member_grade 
ADD CONSTRAINT 
    PRIMARY KEY(grade_code);

-- member.mem_no + AUTO_INCREMENT PK
ALTER TABLE member 
ADD CONSTRAINT 
    PRIMARY KEY(mem_no);

ALTER TABLE member 
MODIFY mem_no INT AUTO_INCREMENT;

-- member.mem_id + UNIQUE
ALTER TABLE member 
ADD CONSTRAINT uq_member_mem_id UNIQUE(mem_id);

-- member + grade_code + FK
ALTER TABLE member 
ADD COLUMN grade_code VARCHAR(10) AFTER mem_name;

ALTER TABLE member 
ADD CONSTRAINT FOREIGN KEY(grade_code) REFERENCES member_grade(grade_code);

-- member + gender + CHECK
ALTER TABLE member ADD COLUMN gender CHAR(2) CHECK(gender IN ('M', 'F'));

-- member + age + CHECK
ALTER TABLE member ADD COLUMN age TINYINT;
ALTER TABLE member ADD CONSTRAINT CHECK(age >= 0);



-- 실습
-- employee.emp_no + UNIQUE
ALTER TABLE employee ADD CONSTRAINT UNIQUE(emp_no);

-- employee.dept_code, job_code + FK
ALTER TABLE employee
ADD CONSTRAINT FOREIGN KEY(dept_code) REFERENCES department(dept_id);

ALTER TABLE employee
ADD CONSTRAINT FOREIGN KEY(job_code) REFERENCES job(job_code);
    
-- department.location_id + FK
ALTER TABLE department
ADD CONSTRAINT FOREIGN KEY(location_id) REFERENCES location(local_code);

-- location.national_code + FK
ALTER TABLE location
ADD CONSTRAINT FOREIGN KEY(national_code) REFERENCES national(national_code);

-- 제약조건 삭제: DROP CONSTRAINT

-- member_grade.grade_code + PK
ALTER TABLE member_grade 
DROP CONSTRAINT PRIMARY KEY;

-- member - PK
ALTER TABLE member 
DROP CONSTRAINT PRIMARY KEY;

ALTER TABLE member
MODIFY mem_no INT;

-- member - UNIQUE
ALTER TABLE member 
DROP CONSTRAINT uq_member_mem_id;

-- member + grade_code + FK
ALTER TABLE member 
DROP CONSTRAINT `1`;

ALTER TABLE member 
DROP CONSTRAINT `CONSTRAINT_1`;

