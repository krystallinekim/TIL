USE practice;

CREATE TABLE userinfo(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nickname VARCHAR(20) NOT NULL,
    phone VARCHAR(11) UNIQUE,
    reg_date DATE DEFAULT(CURRENT_DATE)
);

DESC userinfo;