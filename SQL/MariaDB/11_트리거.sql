-- 트리거

-- 상품 데이터 테이블
CREATE TABLE products(
    pcode INT AUTO_INCREMENT PRIMARY KEY,  -- 상품번호
    pname VARCHAR(100),                    -- 상품명
    brand VARCHAR(100),                    -- 브랜드명
    price INT,                             -- 상품가격
    stock INT DEFAULT 0,                   -- 재고
    created_at DATE DEFAULT CURDATE()      -- 입력일자
);

INSERT INTO products (pname, brand, price) 
VALUES ('아이폰 15 프로', '애플', 1200000);

INSERT INTO products (pname, brand, price) 
VALUES ('갤럭시 Z 폴드 7', '삼성', 2500000);

SELECT * FROM products;

-- 상품 입출고 이력 테이블
CREATE TABLE product_details(
    dcode INT AUTO_INCREMENT PRIMARY KEY,                 -- 입/출고 이력코드
    status VARCHAR(2) CHECK(status IN ('입고', '출고')),  -- 입/출고 구분
    amount INT,                                           -- 입/출고 수량
    pcode INT REFERENCES products(pcode),                 -- 상품코드(FK)
    created_at DATE DEFAULT CURDATE()                     -- 입/출고 일자
);

-- 1번 10개 입고
INSERT INTO product_details (status, amount, pcode, created_at) 
VALUES ('입고', 10, 1, '2026-01-11');

UPDATE products
SET stock = stock + 10
WHERE pcode = 1;

-- 1번 5개 출고
INSERT INTO product_details (status, amount, pcode, created_at) 
VALUES ('출고', 5, 1, '2026-01-11');

UPDATE products
SET stock = stock -5
WHERE pcode = 1;

-- 2번 20개 입고
INSERT INTO product_details (status, amount, pcode, created_at) 
VALUES ('입고', 20, 2, '2026-01-13');

UPDATE products
SET stock = stock +20
WHERE pcode = 2;


-- 수동으로 하는건 매우 번거로움
-- product_details 데이터 입력 시 products의 stock이 자동으로 업데이트되도록 트리거 생성

DELIMITER $$
CREATE OR REPLACE TRIGGER trg_products_stock
AFTER INSERT ON product_details
FOR EACH ROW
BEGIN
    -- 상품이 입고된 경우 -> 재고 증가
    IF NEW.status = '입고' THEN
        UPDATE products
        SET stock = stock + NEW.amount
        WHERE pcode = NEW.pcode;
    END IF;
        
    -- 상품이 출고된 경우 -> 재고 감소
    IF NEW.status = '출고' THEN
        UPDATE products
        SET stock = stock - NEW.amount
        WHERE pcode = NEW.pcode;
    END IF;    
END $$
DELIMITER ;

-- 입/출고 작동 확인
INSERT INTO product_details (status, amount, pcode, created_at) 
VALUES ('출고', 10, 1, '2026-01-14');


SELECT * FROM products;
SELECT * FROM product_details;

-- 트리거 삭제
DROP TRIGGER trg_products_stock;














