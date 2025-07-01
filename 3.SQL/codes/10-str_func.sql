USE lecture;
SELECT * FROM dt_demo;

-- 길이
-- 결과값이 갑자기 없는 컬럼의 없는 데이터를 만들어서 SELECT 뒤 내용의 이름과 결과값을 반환함 -> 가상컬럼 -> 테이블과 전혀 연관이 없다.
SELECT LENGTH('hello sql');
-- LENGTH는 영어만 제대로 반환함 -> 그냥 전부 CHARLENGTH로 쓸것
SELECT
	name, 
    CHAR_LENGTH(name) AS '길이' 
FROM dt_demo;
-- 가상컬럼을 만들때, AS를 쓰면 별명을 붙여주는것도 가능함. 그냥 써도 되는데 '' 안쓰면 띄어쓰기를 못씀


-- 연결 CONCAT(문자열1, 문자열2, 문자열3, ...)
SELECT CONCAT('hello ','sql ','!!');
-- 테이블 안의 데이터와도 가능함
SELECT 
	CONCAT(name, ' (', score, '점)') AS info 
FROM dt_demo;

SELECT 
	nickname,
    UPPER(nickname) AS UN,
    LOWER(nickname) AS LN
FROM dt_demo;

-- 부분 문자열 추출
-- substring(문자열, 시작점, 길이), LEFT/RIGHT(문자열,길이)
SELECT SUBSTRING('hello sql!',2,4);
SELECT LEFT('hello sql!',5);
SELECT RIGHT('hello sql!',5);

-- CONCAT이랑 조합시 축약같은것도 가능
SELECT
	description,
    CONCAT(
		SUBSTRING(description, 1, 10) , '...') AS intro
FROM dt_demo;

SELECT
	description,
    CONCAT(
		LEFT(description,3),
        '...',
        RIGHT(description,3)
	) AS summary
FROM dt_demo;

-- 문자열 치환 
-- REPLACE(전체, 바뀔 부분, 바꿀 내용) -> 욕설필터, 검열 등에 사용
SELECT REPLACE('A@gmail.com','A','B');
SELECT 
	description,
    REPLACE (description, '학생', '**') AS secret
FROM dt_demo;

-- 동적 추출
-- LOCATE(부분문자열, 전체 문자열) -> 문자열의 위치(숫자)를 반환
SELECT LOCATE('@','username@gmail.com'); -- @의 위치를 숫자로 반환함
SELECT
	description,
    SUBSTRING(description,1,LOCATE(
		'학생',description)-1
	) AS '학생설명'
FROM dt_demo;


-- 공백 없애기
SELECT '    what??     ' AS '입력', TRIM('    what??     ')'반환';

