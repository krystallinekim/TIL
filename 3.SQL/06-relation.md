# SQL 관계형 데이터베이스

## 1. 1:1 관계 (One-to-One Relationship)

### 개념
- 한 테이블의 레코드 하나가 다른 테이블의 레코드 하나와만 연결되는 관계
- 양방향으로 유일한 관계를 가짐

### 특징
- 가장 단순한 관계 형태
- 실제로는 하나의 테이블로 합칠 수 있지만, 보안이나 성능상의 이유로 분리하는 경우가 많음
- 외래키(Foreign Key)에 UNIQUE 제약조건이 필요
- CASCADE를 쓰면 원래 테이블에서 삭제되면 관계로 묶인 테이블에서도 데이터가 삭제됨

### 구현 방법
```sql
-- 사용자 기본 정보 테이블
CREATE TABLE users (
    user_id INT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- 사용자 상세 정보 테이블 (1:1 관계)
CREATE TABLE user_profiles (
    profile_id INT PRIMARY KEY,
    user_id INT UNIQUE NOT NULL,  -- UNIQUE 제약조건으로 1:1 관계 보장
    full_name VARCHAR(100),
    phone VARCHAR(20),
    address TEXT,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE -- CASCADE 옵션으로 데이터의 일관성을 유지
);
```

### 실제 예시
- 사용자 ↔ 사용자 개인정보
- 직원 ↔ 직원 상세정보
- 제품 ↔ 제품 상세설명

---

## 2. 1:N 관계 (One-to-Many Relationship)

### 개념
- 한 테이블의 레코드 하나가 다른 테이블의 여러 레코드와 연결되는 관계
- 가장 일반적인 관계 형태

### 특징
- 부모 테이블(1)과 자식 테이블(N)로 구성
- 자식 테이블에 부모 테이블의 기본키를 외래키로 참조
- 외래키에는 UNIQUE 제약조건을 두지 않음
- 부모를 삭제 시 자식의 데이터를 삭제할 방법을 고려해야 함


### 구현 방법
```sql
-- 부서 테이블 (1)
CREATE TABLE departments (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(50) NOT NULL
);

-- 직원 테이블 (N)
CREATE TABLE employees (
    emp_id INT PRIMARY KEY,
    emp_name VARCHAR(50) NOT NULL,
    dept_id INT,  -- 외래키, UNIQUE 제약조건 없음
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id)
);
```

### 실제 예시
- 고객 ↔ 주문 (한 고객이 여러 주문)
- 카테고리 ↔ 제품 (한 카테고리에 여러 제품)
- 저자 ↔ 책 (한 저자가 여러 책)

---

## 3. M:N 관계 (Many-to-Many Relationship)

### 개념
- 양쪽 테이블의 레코드들이 서로 다대다 관계를 가지는 형태
- 직접적인 구현이 불가능하여 중간 테이블(Junction Table)을 사용

### 특징
- 가장 복잡한 관계 형태
- 반드시 중간 테이블이 필요
- 중간 테이블은 양쪽 테이블의 기본키를 외래키로 가짐
- 중간 테이블에도 추가 속성을 저장할 수 있음

### 구현 방법
```sql
-- 학생 테이블
CREATE TABLE students (
    student_id INT PRIMARY KEY,
    student_name VARCHAR(50) NOT NULL
);

-- 과목 테이블
CREATE TABLE subjects (
    subject_id INT PRIMARY KEY,
    subject_name VARCHAR(50) NOT NULL
);

-- 수강 신청 테이블 (중간 테이블)
CREATE TABLE students_subjects (
    student_id INT,
    subject_id INT,
    enrollment_date DATE,
    grade CHAR(1),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id),
    PRIMARY KEY (student_id, subject_id)  -- 중복 수강 방지, 복합 기본키
);
```


### 실제 예시
- 학생 ↔ 과목 (한 학생이 여러 과목 수강, 한 과목을 여러 학생이 수강)
- 제품 ↔ 주문 (한 제품이 여러 주문에 포함, 한 주문에 여러 제품)
- 사용자 ↔ 역할 (한 사용자가 여러 역할, 한 역할을 여러 사용자가 가짐)
