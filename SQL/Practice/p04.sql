USE practice;
SELECT * FROM userinfo;

-- id 3 이하
SELECT * FROM userinfo WHERE id <= 3;
-- 이메일이 gmail로 끝남
SELECT * FROM userinfo WHERE email LIKE '%@gmail.com' OR email LIKE '%@naver.com';
-- alice, bruce
SELECT * FROM userinfo WHERE name IN ('alice','bruce');
-- 이메일 칸이 빈 경우 / 비어있지 않은 경우
SELECT * FROM userinfo WHERE email IS NULL;
SELECT * FROM userinfo WHERE email IS NOT NULL;
-- 이름에 d가 들어가는 경우
SELECT * FROM userinfo WHERE name LIKE '%d%';
-- 전화번호가 016으로 시작하는 경우
SELECT * FROM userinfo WHERE phone LIKE '016%';
-- 이름에 d가 있고, 핸드폰 번호가 010으로 시작하고, gmail을 쓰는 사람
SELECT * FROM userinfo WHERE 
name LIKE '%d%' AND 
phone LIKE '010%' AND 
email LIKE '%@gmail.com';

-- 이름에 e나 g가 들어가고, gmail을 쓰는 사람
SELECT * FROM userinfo WHERE
(
name LIKE 'e%' OR
name LIKE 'g%'
) AND
email LIKE '%@gmail.com';
-- 사칙연산에서 순서대로 적용된 것 -> 괄호 쓰면 됨