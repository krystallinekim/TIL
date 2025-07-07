USE lecture;

-- m:n 구성


-- Students라는 테이블
CREATE TABLE students (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20)
);
-- Courses라는 테이블
CREATE TABLE courses (
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    classroom VARCHAR(20)
);
-- 이 둘 간에는 당장 관계가 존재하지 않음

-- 중간테이블 (Junction Table) students:courses = M:N
-- 이걸 기반으로 학생과 수업 간에는 관계가 생긴다.
CREATE TABLE students_courses (		-- 보통 이름은 관계가 있는 테이블 이름을 이어서 씀
	student_id INT,
    course_id INT,
    grade VARCHAR(5),				-- 관계 테이블에도 의미있는 데이터를 넣을 수 있다.
    FOREIGN KEY(student_id) REFERENCES students(id),	-- 외래키 참조 표현
    FOREIGN KEY(course_id) REFERENCES courses(id)		-- FOREIGN KEY(컬럼명) REFERENCES 외부테이블(외부테이블 컬럼명)
);														-- 외래키가 있을 경우, DROP TABLE 하려면 외래키가 있는 중간테이블부터 지워야 한다

-- 데이터 삽입
INSERT INTO students VALUES
(1, '김학생'),
(2, '이학생' ),
(3, '박학생');

INSERT INTO courses VALUES
(1, 'MySQL 데이터베이스', 'A관 101호'),
(2, 'PostgreSQL 고급', 'B관 203호'),
(3, '데이터 분석', 'A관 704호');

INSERT INTO students_courses VALUES
(1, 1, 'A'),  -- 김학생이 MySQL 수강
(1, 2,'B+'), -- 김학생이 PostgreSQL 수강
(2, 1, 'A-'), -- 이학생이 MySQL 수강
(2, 3, 'B'),  -- 이학생이 데이터분석 수강
(3, 2, 'A+'), -- 박학생이 PostgreSQL 수강
(3, 3, 'A');  -- 박학생이 데이터분석 수강

-- 학생별 수강 과목
-- JOIN을 2번 해서 중간 테이블에 학생/수강 데이터를 합침 
SELECT *
FROM students_courses sc
JOIN students s ON sc.student_id = s.id	
JOIN courses c ON sc.course_id = c.id;

SELECT
	s.name AS 학생,
    GROUP_CONCAT(c.name) AS 수강과목,
    ROUND(
		AVG(
			CASE
				WHEN grade = 'A+' 	THEN 4.3
				WHEN grade = 'A' 	THEN 4.0
				WHEN grade = 'A-' 	THEN 3.7
				WHEN grade = 'B+' 	THEN 3.3
				WHEN grade = 'B'	THEN 3.0
			END
		)
	,2) AS 학생별_학점평균
FROM students_courses sc
JOIN students s ON sc.student_id = s.id
JOIN courses c ON sc.course_id = c.id
GROUP BY s.name;

-- 과목별 정리?
-- 과목명, 강의실, 수강인원, 수강학생들, 학점평균
SELECT
	c.name AS 과목,
    c.classroom AS 강의실,
    COUNT(DISTINCT sc.student_id) AS 수강인원,
    GROUP_CONCAT(s.name) AS 수강학생들,
	ROUND(AVG(CASE								-- CASE WHEN 구문을 AVG 안에 넣을 수 있음
		WHEN grade = 'A+' 	THEN 4.3
		WHEN grade = 'A' 	THEN 4.0
		WHEN grade = 'A-' 	THEN 3.7
		WHEN grade = 'B+' 	THEN 3.3
		WHEN grade = 'B'	THEN 3.0
	END),2) AS 강의별_학점평균
FROM students_courses sc
JOIN students s ON sc.student_id = s.id
JOIN courses c ON sc.course_id = c.id
GROUP BY c.name, c.classroom;