USE test_db;
-- 1. 형변환 함수
-- 숫자 데이터를 문자 데이터로 형변환

SELECT 12345
SELECT CAST(12345 AS CHAR) AS 'CHAR';
SELECT CONVERT(12345, CHAR) AS 'CHAR';

SELECT name, birthyear, CONVERT(birthyear, CHAR) AS 'birthyear_char' FROM usertbl;

-- 숫자 사이에서도 변환 가능 (실수 <-> 정수)
SELECT CONVERT(AVG(amount), INT) FROM buytbl;

-- 문자 -> 숫자
SELECT '100,000', CONVERT('100,000', INT);
SELECT CONVERT(REPLACE('100,000,000', ',', ''), INT);

SELECT '1,000,000' - '500,000';  -- '-'같은 산술연산자는 알아서 계산 가능한 숫자로 변환해서 계산함(묵시적 형변환)

SELECT name, mobile1, CONVERT(mobile1, INT) FROM usertbl;

-- 문자, 숫자 -> 날짜/시간 데이터

SELECT 
    CONVERT(20260107, DATE) AS 'today_int', 
    CONVERT('2026-01-07', DATE) AS 'today_str', 
    CONVERT(20260132, DATE) AS 'impossible'
;

SELECT CONVERT('26-01-07 12:06:59', DATETIME);

-- ------------------------------------------------------------------------------------------------------------
-- 2. 제어 흐름 함수 실습
-- IF
SELECT IF(100 < 200, '참', '거짓');

-- 실습: buytbl 테이블에서 고객 별 구매 개수의 합계 조회
--       단, 구매 개수가 10개 이상이면 'VIP 고객', 10개 미만이면 '일반 고객'으로 출력
SELECT userid AS '아이디',
		 SUM(amount) AS '구매 개수의 합',
		 IF(SUM(amount) >= 10, 'VIP 고객', '일반 고객') AS '고객 유형'
FROM buytbl
GROUP BY userid
ORDER BY `구매 개수의 합` DESC;

-- IFNULL
SELECT IFNULL(NULL, '값이 없이'), IFNULL(100, '값이 없이');
SELECT NVL(NULL, '값이 없이'), NVL(100, '값이 없이'); -- 10.3 버전부터 지원

-- NVL2
SELECT NVL2(NULL, 100, 200), NVL2(300, 100, 200);


-- 실습: employee 테이블에서 보너스가 있다면 0.1로 통일해 연봉 조회
--       직원명, 보너스, 보너스 포함 연봉 조회
USE employees_db;
SELECT emp_name , COALESCE(bonus, 0) AS 'bonus', salary * NVL2(bonus, 1.1, 1.0) AS 'salary_adjust' FROM employee;


-- NULLIF
SELECT NULLIF('123asd', 123), NULLIF(123, 456);
-- 형변환도 묵시적으로 적용됨

-- CASE 연산자

SELECT 
    CASE 5  -- CASE 안에 값이 있을 경우 WHEN 안의 값과 같은지 다른지를 확인
        WHEN  1 THEN '일'  -- 5 == 1?
        WHEN  2 THEN '이'  -- 5 == 2?
        WHEN  5 THEN '오'  -- 5 == 5 -> '오' 출력
        WHEN 10 THEN '십'  -- 이건 체크 안하고 넘어감
        ELSE '기타'        -- 여기까지 오면 그냥 '기타' 출력
    END AS 'CASE-WHEN';


SELECT
    CASE  -- CASE 안에 값이 없으면 WHEN 안의 T/F로 따짐
        WHEN 10 < 20 THEN '10 < 20'
        WHEN 10 = 20 THEN '10 = 20'
        ELSE '모름'
    END AS '결과';


-- 실습: employee 테이블에서 급여등급 조회
SELECT
    emp_name,
    salary,
    CASE
        WHEN salary > 5000000 THEN '1등급'
        WHEN salary > 3500000 THEN '2등급'
        WHEN salary > 2000000 THEN '3등급'
        ELSE '4등급'
    END AS 'grade'
FROM employee
ORDER BY salary DESC;

-- ------------------------------------------------------------------------------------------------------------
-- 3. 문자열 함수

-- 아스키 코드
SELECT ASCII('A'), CHAR(65);
SELECT ASCII('가'), CHAR(234);  -- 한글은 유니코드로 변환해 주는데, 이건 제대로 변환해주지는 않음 

-- 길이
SELECT BIT_LENGTH('string') AS 'bit길이', CHAR_LENGTH('string') AS '문자개수', LENGTH('string') AS 'Byte수';
SELECT BIT_LENGTH('문자열') AS 'bit길이', CHAR_LENGTH('문자열') AS '문자개수', LENGTH('문자열') AS 'Byte수';
-- MariaDB에서는 UTF-8 인코딩을 이용함
-- 영문자는 1바이트 안에 영어 한글자가 들어갈 수 있다. 한글은 3Byte 사용

-- CONCAT
SELECT CONCAT('2026', '01', '07') AS 'CONCAT', CONCAT_WS('-', '2026', '01', '07') AS 'CONCAT_WS';

-- 실습: 휴대전화 번호 합치기
SELECT userID, name, CONCAT_WS('-', mobile1, mobile2, mobile3) FROM usertbl;

-- 위치
SELECT ELT(2, '하나', '둘', '셋');  -- n번째의 문자열('둘')을 반환함
SELECT FIELD('둘', '하나', '둘', '셋');  -- 찾을 문자열('둘')이 몇번째(2)에 있는지 반환함
SELECT FIND_IN_SET('둘', '하나,둘,셋');  -- 문자열 리스트에서 찾아서 반환함. ','로 구분되며 공백이 없어야 함
SELECT INSTR('하나, 둘, 셋', '둘');  -- 1번째부터 시작함
SELECT LOCATE('둘', '하나, 둘, 셋');  -- INSTR과 순서가 반대
-- 없으면 0나옴

-- 숫자변환
SELECT CONVERT(5910.388482, CHAR), FORMAT(5910.388482, 3);  -- 회계형식으로 바꿔주고, 소수점 n자리에서 반올림도 해줌

-- 삽입
SELECT INSERT('123456789', 3, 4, 'abcd');  -- 3번째(3)부터 4개(3~6)를 지우고 그 자리에 'abcd'를 삽입
-- 실습: 개인정보 숨기기
SELECT emp_name, INSERT(emp_no, 8, 7, '#######') AS 'emp_no' FROM employee; -- 비밀정보를 마스킹할 때 사용하기도 함.


-- 문자열 추출
SELECT LEFT('abcdef', 3), RIGHT('abcdef', 2);  -- 왼쪽/오른쪽에서 n글자 추출

-- 실습: 이메일의 @ 앞부분을 추출하기
SELECT emp_name, email, LEFT(email, LOCATE('@', email)-1) AS 'ID' FROM employee;

SELECT SUBSTRING('abcdefg', 3, 4);  -- 내부에서 문자열 추출(3번째 글자부터 4개 반환)
SELECT SUBSTRING_INDEX('aaa_bbbb_cc_ddd_ee_ff_g', '_', 3);  -- sep가 n개 나오면 그 구분자부터 오른쪽은 버림
SELECT SUBSTRING_INDEX('aaa_bbbb_cc_ddd_ee_ff_g', '_', -3);  -- 음수면 반대로 셈

-- 실습: employee 테이블에서 이름/성별/ID 조회
SELECT 
    emp_name, 
    SUBSTRING_INDEX(email, '@', 1) AS 'ID', 
    CASE
        WHEN SUBSTRING(emp_no, 8, 1) IN ('1', '3') THEN '남성'
        WHEN SUBSTRING(emp_no, 8, 1) IN ('2', '4') THEN '여성'
        ELSE '성별 불명'
    END AS 'gender',
    IF(SUBSTRING(emp_no, 8, 1) IN ('1', '3'), '남성', '여성') AS 'gender2'  -- IF 쓰는게 더 깔끔할듯
FROM employee;

-- 알파벳
SELECT UPPER('AbCdEf'), LOWER('AbCdEf');  -- 소문자/대문자

-- 채우기
SELECT LPAD('abcd', 10), LPAD('abcd', 10, ' '), LPAD('abcd', 10, '#');  -- 빈칸으로 두면 스페이스 넣는거랑 똑같음
SELECT RPAD('abcd', 10), RPAD('abcd', 10, '#');
SELECT LPAD('abcd', 3), RPAD('abcd', 3);  -- 잘릴때는 그냥 오른쪽 끝에서부터 짤림

SELECT emp_name, RPAD(LEFT(emp_no, 7), 14, '#') FROM employee;  -- 자르고 남은 부분에 채우는 용도

-- 정리
SELECT LTRIM('      trim      '), RTRIM('      trim      '), TRIM('      trim      ');
SELECT TRIM(
    BOTH
--     LEADING
--     TRAILING
    '@@' FROM '@@@@@trim@@@@@') AS 'trim'; -- 특정 문자열 제거도 가능함. 더이상 문자열을 제거할 수 없을 때까지.



-- REPLACE
SELECT REPLACE('stringoldstring', 'old', 'new');  -- 특정 문자열을 찾아서 다른 문자열로 대체
SELECT emp_name, email, REPLACE(email, '@ismoon.or.kr', '') AS 'ID' FROM employee;  -- 대신 전부 같은 형식이라는게 확실해야 함
SELECT emp_name, REPLACE(email, '@ismoon.or.kr', '@gmail.com') AS 'gmail' FROM employee;


-- 기타
SELECT REPEAT('abc', 3), REVERSE('abc'), CONCAT('Maria', SPACE(10), 'DB');


-- ------------------------------------------------------------------------------------------------------------
-- 4. 수학 함수

-- 소수점
--        올림         내림       (소수점 이하) 반올림   소수점 이하 버림
SELECT CEIL(3.546), FLOOR(3.546),    ROUND(3.546,2),    TRUNCATE(3.546, 2);  -- 소수점 아래 자리는 음수도 가능

-- 계산
SELECT MOD(5,2), 5 % 2, 5 MOD 2;
SELECT POW(2, 2), SQRT(2);

-- 기타
SELECT RAND(42), RAND(42) * 100 + 1, FLOOR(RAND(42) * 100 + 1);  -- 0.0 ~ 0.999 -> 1 ~ 100

-- ------------------------------------------------------------------------------------------------------------
-- 5. 날짜 및 시간 함수

-- 날짜/시간 계산
-- ADD(덧셈)/SUB(뺄셈) + DATE(날짜)/TIME(시간)
SELECT 
    ADDDATE(CURDATE(), 20), -- DATE 기본은 일, TIME 기본은 초
    ADDDATE(CURDATE(), INTERVAL 20 DAY),
    ADDDATE(CURDATE(), INTERVAL 1 YEAR),
    ADDTIME(NOW(), '1:1:1');  -- 문자열로 시간데이터 제공도 가능

SELECT DATEDIFF(CURDATE(), '2026-01-01'), TIMEDIFF(CURTIME(), '12:50:00');  -- 날짜/시간 차이

-- 실습: employee에서, 직원명, 입사일, 입사 후 3개월이 된 날짜 조회
SELECT emp_name, hire_date, ADDDATE(hire_date, INTERVAL 3 MONTH) FROM employee WHERE ent_date IS NULL;

-- 실습: employee에서 직원명, 입사일, 근무일수 조회
SELECT emp_name, hire_date, DATEDIFF(NOW(), hire_date) FROM employee WHERE ent_date IS NULL;


-- 변환
SELECT DATE_FORMAT(NOW(), '%M %D(%W), %Y - %p%h:%i:%s');

SELECT YEAR(NOW()), MONTH(NOW()), DAY(NOW()), DATE(NOW()),
SELECT HOUR(NOW()), MINUTE(NOW()), SECOND(NOW()), TIME(NOW());  -- YEAR() ~ SECOND() 까지 정수형으로 데이터를 가져온다

SELECT DAYOFWEEK(CURDATE()), MONTHNAME(CURDATE()), DAYOFYEAR(CURDATE()), LAST_DAY(CURDATE()), QUARTER(CURDATE());

SELECT MAKEDATE(2026, 200), MAKETIME(20, 1, 2), PERIOD_ADD(202601, 10), PERIOD_DIFF(202501, 202612);  -- PERIOD는 정수값으로 나옴
