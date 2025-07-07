# SQL 관계형 데이터베이스

## 1. 1:1 관계 (One-to-One Relationship)

### 개념
- 한 테이블의 레코드 하나가 다른 테이블의 레코드 하나와만 연결되는 관계
- 양방향으로 유일한 관계를 가짐

### 특징
- 가장 단순한 관계 형태
- 실제로는 하나의 테이블로 합칠 수 있지만, 보안이나 성능상의 이유로 분리하는 경우가 많음
- 외래키(Foreign Key)에 UNIQUE 제약조건이 필요

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
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
```

### 실제 예시
- 사용자 ↔ 사용자 프로필
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

### 구현 방법
```sql
-- 부서 테이블 (1쪽)
CREATE TABLE departments (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(50) NOT NULL
);

-- 직원 테이블 (N쪽)
CREATE TABLE employees (
    emp_id INT PRIMARY KEY,
    emp_name VARCHAR(50) NOT NULL,
    dept_id INT,  -- 외래키, UNIQUE 제약조건 없음
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id)
);

-- 샘플 데이터
INSERT INTO departments VALUES (1, '개발팀'), (2, '마케팅팀');
INSERT INTO employees VALUES 
    (1, '김개발', 1),
    (2, '이개발', 1),
    (3, '박마케팅', 2);
```

### 조회 예시
```sql
-- 부서별 직원 수 조회
SELECT d.dept_name, COUNT(e.emp_id) as employee_count
FROM departments d
LEFT JOIN employees e ON d.dept_id = e.dept_id
GROUP BY d.dept_id, d.dept_name;

-- 특정 부서의 모든 직원 조회
SELECT d.dept_name, e.emp_name
FROM departments d
JOIN employees e ON d.dept_id = e.dept_id
WHERE d.dept_name = '개발팀';
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
- 연결 테이블(Bridge Table, Junction Table)이 필요
- 연결 테이블은 양쪽 테이블의 기본키를 외래키로 가짐

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

-- 수강 신청 테이블 (연결 테이블)
CREATE TABLE enrollments (
    enrollment_id INT PRIMARY KEY,
    student_id INT,
    subject_id INT,
    enrollment_date DATE,
    grade CHAR(1),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id),
    UNIQUE KEY (student_id, subject_id)  -- 중복 수강 방지
);

-- 샘플 데이터
INSERT INTO students VALUES (1, '김학생'), (2, '이학생'), (3, '박학생');
INSERT INTO subjects VALUES (1, '수학'), (2, '영어'), (3, '과학');
INSERT INTO enrollments VALUES 
    (1, 1, 1, '2024-03-01', 'A'),
    (2, 1, 2, '2024-03-01', 'B'),
    (3, 2, 1, '2024-03-01', 'A'),
    (4, 2, 3, '2024-03-01', 'C'),
    (5, 3, 2, '2024-03-01', 'B');
```

### 조회 예시
```sql
-- 학생별 수강 과목 조회
SELECT s.student_name, sub.subject_name, e.grade
FROM students s
JOIN enrollments e ON s.student_id = e.student_id
JOIN subjects sub ON e.subject_id = sub.subject_id
ORDER BY s.student_name;

-- 과목별 수강 학생 수 조회
SELECT sub.subject_name, COUNT(e.student_id) as student_count
FROM subjects sub
LEFT JOIN enrollments e ON sub.subject_id = e.subject_id
GROUP BY sub.subject_id, sub.subject_name;

-- 특정 학생의 모든 수강 과목 조회
SELECT sub.subject_name, e.grade
FROM subjects sub
JOIN enrollments e ON sub.subject_id = e.subject_id
WHERE e.student_id = 1;
```

### 실제 예시
- 학생 ↔ 과목 (한 학생이 여러 과목 수강, 한 과목을 여러 학생이 수강)
- 제품 ↔ 주문 (한 제품이 여러 주문에 포함, 한 주문에 여러 제품)
- 사용자 ↔ 역할 (한 사용자가 여러 역할, 한 역할을 여러 사용자가 가짐)

---

## 관계 설정 시 고려사항

### 1. 데이터 무결성
- **참조 무결성**: 외래키는 참조하는 테이블의 기본키 값이어야 함
- **개체 무결성**: 기본키는 NULL이나 중복값을 가질 수 없음
- **도메인 무결성**: 컬럼에 올바른 데이터 타입과 제약조건 설정

### 2. 성능 최적화
- 외래키 컬럼에 인덱스 생성 권장
- 자주 조인되는 컬럼들에 대한 인덱스 설정
- 대용량 데이터에서는 파티셔닝 고려

### 3. 설계 원칙
- 정규화를 통한 데이터 중복 최소화
- 비즈니스 로직에 맞는 적절한 관계 설정
- 확장성을 고려한 유연한 구조 설계

---

## 요약

| 관계 타입 | 특징 | 구현 방법 | 예시 |
|-----------|------|-----------|------|
| **1:1** | 양방향 유일 관계 | 외래키 + UNIQUE 제약조건 | 사용자 ↔ 프로필 |
| **1:N** | 한쪽이 여러 개 | 외래키 (UNIQUE 없음) | 부서 ↔ 직원 |
| **M:N** | 양방향 다대다 관계 | 중간 테이블 사용 | 학생 ↔ 과목 |

각 관계 타입을 올바르게 이해하고 적절히 구현하는 것이 효율적인 데이터베이스 설계의 핵심입니다.