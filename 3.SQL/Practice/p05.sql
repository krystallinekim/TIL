USE practice;
SELECT * FROM userinfo;

ALTER TABLE userinfo ADD COLUMN age INT DEFAULT 20;
UPDATE userinfo SET age =30 WHERE id BETWEEN 1 and 5;


-- 이름 오름차순 상위 3명
SELECT * FROM userinfo 
	ORDER BY name 
	LIMIT 3;
-- gmail을 쓰는 사람들을 나이순으로 정렬
SELECT * FROM userinfo 
	WHERE email LIKE '%@gmail.com' 
	ORDER BY age;
-- 나이가 많은 사람들 중에 핸드폰 번호 오름차순으로 3명의 이름,전화번호, 나이만 확인
SELECT name,phone,age FROM userinfo 
	ORDER BY 
		age DESC,
		phone;
-- 가장 이름이 빠른 1명을 제외한 3명을 이름순으로 조회
SELECT * FROM userinfo 
	ORDER BY age 
	LIMIT 3 
	OFFSET 1; -- 앞에 한개 제외함 -> 페이지화 할때 사용함