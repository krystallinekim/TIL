USE lecture;
-- CSV - JSON
-- CSV: Comma Separated Values, 사람이 보기 편하게 표로 만들어놓은 데이터
-- JSON: JavaScript Object Notation, 기계가 보기 편하게 만든 데이터

-- 1. 고객 테이블 생성
DROP TABLE IF EXISTS customers;
CREATE TABLE customers (
    customer_id VARCHAR(10) PRIMARY KEY,
    customer_name VARCHAR(50) NOT NULL,
    customer_type VARCHAR(20) NOT NULL,
    join_date DATE NOT NULL
);

-- 2. 매출 테이블 생성  
DROP TABLE IF EXISTS sales;
CREATE TABLE sales (
    id INT PRIMARY KEY,
    order_date DATE NOT NULL,
    customer_id VARCHAR(10) NOT NULL,
    product_id VARCHAR(10) NOT NULL,
    product_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    unit_price INT NOT NULL,
    total_amount INT NOT NULL,
    sales_rep VARCHAR(50) NOT NULL,
    region VARCHAR(50) NOT NULL,
	-- Foreign key는 외부키, sales의 customer_id 컬럼은 customers에 있는 customer_id 컬럼과 대응되는 컬럼이라는 뜻
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);


-- 3. 데이터 import
-- 왼쪽 SCHEMAS에서 테이블 이름을 우클릭 - Table Data Import Wizard를 이용해서 데이터를 가져오면 됨
-- 주의: 테이블로 가져올 때 Existing Table으로 설정 / 파일 가져올 때 확장자 확인하기

-- 4. 데이터 확인
SELECT * FROM customers;
SELECT COUNT(*) AS 매출건수 FROM sales;

SELECT * FROM sales;