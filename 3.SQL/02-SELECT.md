# SELECT

## 기본 구조

```sql
SELECT 컬럼명 FROM 테이블명 WHERE 조건 ORDER BY 정렬기준 LIMIT 개수 OFFSET 개수;
```

## `WHERE` 

특정 조건의 값들만 반환함



### 비교 연산자

- `=` : 컬럼 값이 지정한 값과 정확히 일치  

- `!=` 또는 `<>` : 컬럼 값이 지정한 값과 일치하지 않음  

```sql
SELECT * FROM students WHERE name = '이도윤';

SELECT * FROM students WHERE id != 1;
```

- `>=`, `<=`, `>`, `<` : 각각 이상, 이하, 초과, 미만 

- `BETWEEN A AND B` : A와 B 사이 값 (포함)  
    ```sql
    column BETWEEN num1 AND num2

    column >= num1 AND column <= num2
    ```
    위 두 구문은 같은 구문임

```sql
SELECT * FROM students WHERE age >= 20;

SELECT * FROM students WHERE age < 40;

SELECT * FROM students WHERE age BETWEEN 20 AND 40;
```


---

### `IN`

- `IN (값1, 값2, ...)` : 값 리스트 중 하나에 해당  

```sql
SELECT * FROM students WHERE id IN (1, 3, 4, 7);

SELECT * FROM userinfo WHERE name IN ('alice', 'bruce');
```


---

### `LIKE`

- `%` : 0개 이상의 임의 문자  
- `_` : 정확히 한 글자  

```sql
SELECT * FROM students WHERE name LIKE '김%';    -- '김'으로 시작  

SELECT * FROM students WHERE name LIKE '%윤%';   -- 이름에 '윤' 포함  

SELECT * FROM students WHERE name LIKE '윤__';    -- '윤'으로 시작하고 총 3글자  
```

---

### NULL 검사

- `IS NULL` : 값이 NULL인 경우  
- `IS NOT NULL` : 값이 NULL이 아닌 경우  

```sql
SELECT * FROM userinfo WHERE email IS NULL;

SELECT * FROM userinfo WHERE email IS NOT NULL;
```

---

### 논리 연산자

- `AND` : 모든 조건이 참인 행 조회 (교집합)  
- `OR` : 하나라도 참인 행 조회 (합집합)  
- `NOT` : 조건이 반대 (여집합)
- 괄호 `()`를 이용해 우선순위 조절 가능  

    ```sql
    SELECT * FROM userinfo WHERE
        name LIKE '%d%' AND 
        phone LIKE '010%' AND 
        email LIKE '%@gmail.com';

    SELECT * FROM userinfo WHERE 
        (name LIKE 'e%' OR name LIKE 'g%') AND 
        email LIKE '%@gmail.com';
    ```

    위 둘은 결과가 다르게 나올 수 있다.

## ORDER BY 정렬

- 결과를 특정 컬럼 기준으로 정렬  
- ASC(오름차순, 기본), DESC(내림차순)  
- 다중 컬럼 정렬 가능 (우선순위에 따라 순서 지정)  
- LIMIT로 조회 개수 제한, OFFSET으로 시작 위치 지정 가능 (페이징 처리)

```sql
SELECT * FROM userinfo ORDER BY name LIMIT 3;

SELECT * FROM userinfo WHERE email LIKE '%@gmail.com' ORDER BY age;

SELECT name, phone, age FROM userinfo ORDER BY age DESC, phone LIMIT 3;

SELECT * FROM userinfo ORDER BY age LIMIT 3 OFFSET 1;
```

