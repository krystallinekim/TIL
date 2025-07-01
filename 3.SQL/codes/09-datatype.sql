USE lecture;

CREATE TABLE dt_demo (
	id 			INT 			PRIMARY KEY AUTO_INCREMENT,
    name 		VARCHAR(20) 	NOT NULL,
    nickname 	VARCHAR(20),
    birth 		DATE,
    score 		FLOAT,
    salary 		DECIMAL(20,3),
    description TEXT,
    is_active 	BOOL 			DEFAULT TRUE,				-- BOOL은 T/F 저장 -> 실제로는 tinyint(1)로 저장된 것도 볼 수 있다
    created_at 	DATETIME 		DEFAULT CURRENT_TIMESTAMP
    );

INSERT INTO dt_demo (name, nickname, birth, score, salary, description) 
	VALUES
	('김민수', 'minsu', '2010-05-21', 88.5, 0, '중학생으로 수학과 과학에 관심이 많음'),
	('박서연', 'seoyeon', '2005-11-13', 92.3, 800000, '열심히 공부하는 대학생'),
	('이현우', 'hyunwoo', '2008-07-04', 75.0, 0, '체육도 좋아함'),
	('최지훈', 'jihun', '2007-01-22', 81.7, 0, '평균 이상의 성적을 유지 중'),
	('정예린', 'yerin', '2006-03-30', 79.2, 0, ''),
	('한수민', 'sumin', '2009-09-15', 85.6, 300000, '독서와 글쓰기를 좋아함'),
	('윤지아', 'jia', '2003-12-02', 90.0, 1000000, '과학 경시대회 수상자'),
	('김하늘', 'haneul', '2011-06-18', 77.4, 0, ''),
	('이준호', 'junho', '2004-04-05', 82.9, 700000, '팀 프로젝트를 잘함'),
	('박지영', 'jiyoung', '2005-08-27', 88.0, 500000, '미술과 음악에도 재능이 있음'),
	('최민재', 'minjae', '1990-02-14', 85.0, 3500000, '직장인으로 성실히 일함'),
	('윤서현', 'seohyun', '1987-09-08', 78.5, 4200000, '마케팅 전문가'),
	('김다은', 'daeun', '1992-06-23', 91.2, 3900000, '프로그래밍을 좋아하는 사회인'),
	('이상훈', 'sanghoon', '1985-11-30', 80.0, 4500000, ''),
	('박소영', 'soyoung', '1995-03-17', 87.6, 3200000, '데이터 분석가');

-- 90점 이상만 조회
SELECT * FROM dt_demo WHERE score > 90;
-- 설명에 학생이라는 말이 없는 사람
SELECT * FROM dt_demo WHERE description NOT LIKE '%학생%';
-- 00년 이전 출생자만
SELECT * FROM dt_demo WHERE birth < '2000-01-01';


DESCRIBE dt_demo;
SELECT * FROM dt_demo;
DROP TABLE dt_demo;