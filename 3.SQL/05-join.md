# JOIN

## JOIN 개념

여러 테이블 데이터를 **연결**하여 하나의 결과로 합침.  

`ON` 절 뒤에 테이블 연결 조건 지정.

### JOIN의 형식

```sql
SELECT
    t1.column1,
    t2.column2,
    ...
FROM table1 t1
JOIN table2 t2
ON JOIN_연결조건;
```
JOIN 연결조건에는 보통 두 테이블 간의 공통 키이면서 고유키를 사용함


## 왜 JOIN을 써야 하는가

테이블이 2개 있고, 테이블 c(customer)에 고객정보 / 테이블 s(sale)에 판매 데이터가 존재할 때, 한 테이블에 고객정보와 판매데이터를 같이 보고 싶을 때가 있을 수 있다.

```sql
SELECT
    customer_id,
    product_name,
    total_amount,
    (SELECT customer_name FROM customers WHERE customer_id = sales.customer_id) AS customer_name,
    (SELECT customer_type FROM customers WHERE customer_id = sales.customer_id) AS customer_type
FROM sales;
```
이걸 서브쿼리를 이용한다면, 매우 길고 현학적이 된다.

보면, 내가 보고싶은 데이터 하나마다 서브쿼리를 하나씩 짜서 넣는 걸 알 수 있음. 매우 비효율적임

```sql
SELECT
    c.customer_name,
    c.customer_type,
    s.product_name,
    s.total_amount
FROM customers c
INNER JOIN sales s ON c.customer_id = s.customer_id;
```
이걸 JOIN을 이용한다면 짧고 효율적으로 이 코드를 만들 수 있다.

JOIN을 통해 두 테이블을 customer_id를 기준으로 하나로 합치고, 각각 테이블에서 원하는 컬럼을 뽑아오는 것.

## JOIN의 종류

[참고하면 좋은 그림](https://i.stack.imgur.com/1UKp7.png)

| JOIN 종류            | 설명                                                   | 코드                                                        | 집합 개념          |
|-----------------------|---------------------------------------------------------|------------------------------------------------------------------|---------------------|
| **INNER JOIN**        | 양쪽 테이블 모두 존재하는 데이터만 반환               | `(INNER) JOIN`                                                    | A ∩ B (교집합)     |
| **LEFT JOIN**         | 왼쪽 테이블(A)의 모든 데이터 + 오른쪽 매칭 데이터     | `LEFT JOIN`                                                     | A                  |
| **LEFT JOIN + NULL**  | 왼쪽 데이터 중 오른쪽에 매칭되지 않는 데이터만 반환   | `LEFT JOIN ... WHERE b.key IS NULL`                             | A ∩ Bᶜ (차집합)    |
| **RIGHT JOIN**        | 오른쪽 테이블(B)의 모든 데이터 + 왼쪽 매칭 데이터     | `RIGHT JOIN`                                                    | B                  |
| **FULL OUTER JOIN**   | 양쪽 테이블의 모든 데이터 반환                        | `FULL OUTER JOIN`                                               | A ∪ B (합집합)     |

보통 INNER JOIN, LEFT JOIN을 주로 사용한다. RIGHT JOIN은 테이블 순서를 바꾸면 LEFT JOIN과 같기 때문.

## GROUP BY + JOIN

항상 유형별로 데이터를 보고싶은 경우가 생기기 때문


고객정보와 판매정보가 다른 테이블에 존재할 때, 고객유형별로 판매 데이터를 보고 싶을 경우

```sql
SELECT
    c.customer_type AS 고객유형,
    COUNT(*) AS 주문건수,
    AVG(s.total_amount) AS 평균구매금액,
    SUM(s.total_amount) AS 총매출액
FROM customers c
INNER JOIN sales s ON c.customer_id = s.customer_id
GROUP BY c.customer_type
ORDER BY 평균구매금액 DESC;
```

## 자주하는 실수와 해결법

| 실수 | 해석 | 해결법 |
| --- | --- | --- |
| **GROUP BY 누락** | 일반 컬럼을 카테고리화 하지 않았을 경우 |SELECT의 집계함수를 제외한 일반 컬럼은 모두 GROUP BY에 포함해야 함|
| **LEFT JOIN 후 COUNT(*) 사용** | LEFT JOIN하면 교집합이 아닌 경우도 데이터로 들어가는데, NULL이라 데이터가 없어야 하는 상황에서도 *는 데이터를 세어버림 |COUNT(오른쪽테이블.id) 사용, NULL 값 제외 |
| **컬럼 앞에 테이블명 안 쓰기** | 어디서 참조해 올지 몰라 에러가 생김 | 테이블명을 꼭 써주자 |
