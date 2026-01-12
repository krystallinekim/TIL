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
-- 3. 외래키
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


