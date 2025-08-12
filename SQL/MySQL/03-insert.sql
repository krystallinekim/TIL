USE lecture;
DESC members;

-- 데이터 입력
-- id는 어차피 auto_increment로 자동증가해서 안넣는게 맞음
-- join_date도 자동으로 값을 생성해줘서 넣을 필요가 없음
-- column 이름과 데이터를 정확하게 매치하는게 중요
INSERT INTO members (name,email) VALUES ('김정수','kim@a.com');
INSERT INTO members (name,email) VALUES ('김정수2','kim2@a.com');
-- 여러개를 한번에 넣는 것도 가능함
INSERT INTO members (email,name) VALUES
    ('lee@a.com','이이이'),
    ('park@b.com','박박박');

-- 데이터 확인/조회 (Read)

-- 전체 조회
SELECT * FROM members;
-- id, join_date가 자동으로 생성된 것까지 확인 가능함
-- *는 와일드카드(모두)

-- 단일 데이터 조회
SELECT * FROM members WHERE id=1;
-- 여기서 *는 모든 컬럼을 뜻함
-- 매우 VLOOKUP과 비슷함