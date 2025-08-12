-- UPDATE
SELECT * FROM members;

-- WHERE는 검색 조건
-- SET는 내가 이걸로 바꾸겠다는 선언
UPDATE members SET name='최최최', email='choi@d.com' WHERE id=2;

-- PK가 아닌 키를 기준으로 바꾸는것도 가능(위험)
UPDATE members SET name='한한한' WHERE name='박박박';

-- DELETE
-- DELETE 해도 
DELETE FROM members WHERE id=3;
-- 테이블 모든 데이터 삭제(위험)
DELETE FROM members;