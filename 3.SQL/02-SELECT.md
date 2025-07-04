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
    SELECT * FROM students WHERE age >= 20;

    SELECT * FROM students WHERE age < 40;

    SELECT * FROM students WHERE age BETWEEN 20 AND 40;
    ```

    ```sql
    -- BETWEEN
    column BETWEEN num1 AND num2
    column >= num1 AND column <= num2
    ```
    `BETWEEN`은 num1 이상 num1 이하 값을 반환함 
    
    그래서 위 두 구문은 같은 구문임

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

- `IS NULL` : 값이 NULL인 경우 반환

    ```sql
    SELECT * FROM userinfo WHERE email IS NULL;

    SELECT * FROM userinfo WHERE email IS NOT NULL;
    ```
    NOT을 이용하면 NULL이 아닌 경우도 반환 가능함

---

### 논리 연산자

- `AND` : 모든 조건이 참인 행 조회 (교집합)  

- `OR` : 하나라도 참인 행 조회 (합집합)  

- `NOT` : 조건이 반대 (여집합)

- 괄호 `()`를 이용해 우선순위 조절 가능  

    ```sql
    SELECT * FROM userinfo WHERE 
        (name LIKE 'e%' OR name LIKE 'g%') AND 
        email LIKE '%@gmail.com';
    -- 결과: 이름에 e 또는 g가 들어가면서 gmail을 쓰는 사람들만 보여줌
    ```

## `ORDER BY` 정렬

- 결과를 특정 컬럼 기준으로 정렬함

- ASC(오름차순, 기본 -> 안써도 됨), DESC(내림차순)  

- 기준을 여러 개 줄 수도 있다. 앞에있는게 먼저 정렬됨

- LIMIT로 조회 개수 제한, OFFSET으로 시작 위치 지정 가능

    ```sql
    -- 나이로 정렬
    SELECT * FROM userinfo WHERE email LIKE '%@gmail.com' ORDER BY age;

    -- 이름으로 정렬, 3개만 출력
    SELECT * FROM userinfo ORDER BY name LIMIT 3;

    -- 나이를 내림차순(가장 큰 값부터)으로 정렬 후 전화번호 순서대로(오름차순) 정렬, 3개만 출력
    SELECT name, phone, age FROM userinfo ORDER BY age DESC, phone LIMIT 3;

    -- 나이로 정렬, 가장 첫(오름차순이니 가장 작은) 줄을 제외하고 3개 출력
    SELECT * FROM userinfo ORDER BY age LIMIT 3 OFFSET 1;
    ```

## UNION

- 위 코드와 아래 코드를 각각 실행 후, 한 테이블에 보여줌

- 위 아래 컬럼 숫자도 맞춰 줘야 한다

- 위아래 AS가 다르다면 순서 기준으로 들어감

    ```sql
    SELECT COUNT(*) AS 개수 FROM sales
    UNION
    SELECT COUNT(*) AS 개수 FROM customers;
    -- 결과: 개수 컬럼에 sales 줄수, customers 줄수가 이어서 나옴
    ```
