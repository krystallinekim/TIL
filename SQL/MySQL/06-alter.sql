USE lecture;
DESC members;
SELECT * FROM members;

-- 테이블 스키마 변경

-- 나이 컬럼(INT) 추가 / 빈칸 불가 / 기본값 20
-- 이 때 실제 값으로 기본값을 바꿔주는건 따로 직접 수정
ALTER TABLE members ADD COLUMN age INT NOT NULL DEFAULT 20;
-- 주소 컬럼(VARCHAR(100)) 추가 / 기본값 미입력
ALTER TABLE members ADD COLUMN address VARCHAR(100) DEFAULT '미입력';

-- 컬럼 이름/데이터 타입 수정
ALTER TABLE members CHANGE COLUMN address juso VARCHAR(100);
ALTER TABLE members MODIFY COLUMN juso VARCHAR(50);

-- 컬럼 삭제
ALTER TABLE members DROP COLUMN age;