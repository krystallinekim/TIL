USE practice;
DESC userinfo;
SELECT * FROM userinfo;

INSERT INTO userinfo (nickname,phone) VALUES
('harvey','01011111111'),
('laura','01022222222'),
('dale','01033333333'),
('aldous','01044444444'),
('bob','01055555555');

SELECT * FROM userinfo WHERE id=3;

SELECT * FROM userinfo WHERE nickname='bob';

UPDATE userinfo SET phone='01099998888' WHERE id=5;

DELETE FROM userinfo WHERE id=5;