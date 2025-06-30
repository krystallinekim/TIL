USE lecture;

-- 테이블 생성 - name, age라는 column 2개를 가지는 sample이라는 table(표)을 생성
CREATE TABLE sample(
-- 30글자 제한인, name이라는 column1을 만들었음
	name VARCHAR(30),
-- 숫자(정수)만 받는 age라는 column2를 만들었음
    age INT
);

-- 현재 테이블 보기
SHOW TABLES;
-- 왼쪽 schemas에서도 lecture-tables-sample-columns 안에 name, age가 잘 들어가 있는 걸 확인할 수 있다.

-- 현재 테이블 삭제
DROP TABLE sample;

CREATE TABLE members(
-- id는 정수이고, 자동으로 증가하는 번호이고, 이걸 primary key로 쓰겠다
	id INT AUTO_INCREMENT PRIMARY KEY,
-- name은 30글자까지, 빈칸이면 안되는 키(필수 입력)
	name VARCHAR(30) NOT NULL,
-- email은 100글자까지, 중복 불가능함
    email VARCHAR(100) UNIQUE,
-- join_date는 날짜를 받고, 기본값이 현재 날짜임
    join_date DATE DEFAULT(CURRENT_DATE)
);

-- 현재 테이블을 상세 확인
DESC members;