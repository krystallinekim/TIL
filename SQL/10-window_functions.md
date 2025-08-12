# Window Functions

집에서 창문 너머로는 전체 주택가를 볼 수 있다.

이처럼, 레코드 안의 데이터만 보는 게 아니라 레코드 밖의 정보를 창문 너머로 보듯이 보는 것이 Window 함수의 기본

Window 함수의 공통점은 `OVER()`구문을 활용한다는 것이다.

## **`OVER()`**

```sql
-- 전체 평균값
SELECT AVG(amount) FROM orders;
-- 전체 데이터
SELECT customer_id, amount FROM orders;
```
전체 평균값은 스칼라, 전체 데이터는 테이블이기 때문에 이 둘을 한 테이블에서 볼 방법은 없었다.

어떻게든 보고 싶다면, CROSS JOIN을 이용해 강제로 모든 데이터 옆에 평균값을 붙여야 했다. 

Window함수가 있으면, 한번에 이걸 편하게 할 수 있음

```sql
SELECT
  customer_id,
  amount,
  AVG(amount) OVER()
FROM orders
```
이 쿼리를 실행하면 각 구매에 대해 | 고객ID | 구매액 | 전체 구매액 평균 | 을 보게 된다.

---

### `OVER()` 안에 들어갈 수 있는 것들

- 안에 아무것도 없으면, 전체 데이터가 기준이 된다.

- `ORDER BY`
  
  윈도우 함수를 어떤 기준으로 적용할 것인지 알려주는 것

  ```sql
  -- 구매액에 대해 정렬해 순위를 구함
  RANK() OVER(ORDER BY amount)
  -- 구매일 기준으로 누적합을 구함
  SUM() OVER(ORDER BY order_date)
  ```

- `PARTITION BY`

  윈도우 함수를 그룹화하는데 사용

  `GROUP BY`를 쓰면 개별 레코드가 묻히고 전체 통계만 구할 수 있지만,

  `PARTITION BY`를 쓰면 개별 레코드를 살리면서 그룹화를 할 수 있음
  ```sql
  -- 지역별 매출 순위를 구함
  RANK() OVER(PARTITION BY region ORDER BY amount)
  ```
  **한번에** 그룹에 대한 정보와 전체에 대한 정보를 같이 볼 수 있다.

- `ROWS BETWEEN`

  결과를 구할 행을 지정한다.

  ```sql
  FIRST_VALUE(name) OVER(
    ORDER BY amount
    ROWS BETWEEN 3 PRECEDING AND 4 FOLLOWING
  )
  ```
  특정 행 기준으로 3행 전 ~ 4행 뒤까지의 데이터 중 가장 amount가 작은 값을 가진 이름을 가져온다.

  기준 1에는 주로 `<n> PRECEDING`, 기준 2에는 `<n> FOLLOWING`이 들어간다.

  n에 값을 주면 n행 전 데이터, UNBOUNDING을 주면 제한 없이 가장 끝 값을 기준으로 한다.

  `CURRENT_ROW`를 쓰면 현재 행이 기준이 된다.

## 윈도우 함수 목록

### **`ROW_NUMBER() OVER()`**

순서대로 줄을 세우는 함수

원래, 그냥 모든 데이터를 줄세우려면 `ORDER BY`를 사용하면 됐다.

```sql
SELECT * FROM orders ORDER BY amount LIMIT 20 OFFSET 20;
```
그런데, 정확하게 n번째 데이터를 찾고 싶을 때는 `LIMIT 1 OFFSET n-1`같은 복잡미묘한 방법을 사용해야 했다.

여기서 윈도우 함수를 이용하면

```sql
SELECT
  *,
  ROW_NUMBER() OVER (ORDER BY amount DESC)
FROM orders;
```
와 같이, 데이터 옆에 이 데이터가 어떤 순서의 몇 번째인지를 붙여서 사용할 수 있다.

### **`RANK()/DENSE_RANK() OVER()`**

`ROW_NUMBER()`와 비슷하지만, 동점자 처리 방법이 다르다.

```sql
SELECT
  ROW_NUMBER() OVER (ORDER BY amount DESC),
  RANK() OVER (ORDER BY amount DESC),
  DENSE_RANK() OVER (ORDER BY amount DESC)
FROM orders;
```
이 쿼리를 돌리면,

|함수|결과|동점자 처리 방식|
|---|---|---|
|`ROW_NUMBER()`|1-2-3-4-5-6-7-8-9|동점자 무시하고 위에서부터 순서대로|
|`RANK()`|1-1-1-1-5-5-7-8-8|동점자 숫자를 고려해서 동점자 처리|
|`DENSE_RANK()`|1-1-1-1-2-2-3-4-4|동점자 숫자는 무시하고 넘버링|

각각 사용처가 다르다.

### **`SUM() OVER()`**

```sql
SUM(일매출) OVER(ORDER BY order_date) AS 누적매출
```
일반적으로 `SUM()`은 전체 총합, 혹은 집계함수에서 카테고리별 총합을 보는 함수이다.

윈도우 함수에서의 `SUM()`은 그 행까지의 **누적 합계**를 보는 함수이다.

`PARTITION BY`를 이용하면, 특정 카테고리별 누적 합계도 볼 수 있음.

### **`AVG() OVER()`**
```sql
AVG(일매출) OVER(ORDER BY order_date) AS 누적평균매출
```
`AVG()`도 똑같이 그 행까지의 **누적 평균**을 보여준다.

이 때, `OVER` 안에 `ROWS BETWEEN <n-1> PRECEDING AND CURRENT ROW`를 적용해 주면

n일동안의 **이동평균**을 볼 수 있게 된다.

모든 행에 대해 n-1일 전부터 현재 행까지 총 n일 간의 평균을 보게 된다는 뜻

### **`LAG()/LEAD() OVER()`**

`LAG()`와 `LEAD()`는 의미만 반대고, 사실상 같은 함수

```sql
LAG(월매출,1) OVER(ORDER BY 월) AS 전월매출
```
`LAG`는 특정 행 기준으로 n열만큼 이전 열 값을 가져오고, `LEAD` 는 반대로 n열 뒤 값을 가져온다.

가장 잘 쓰는 곳은 **증감율**을 볼 때

### **`NTILE()/PERCENT_RANK() OVER()`**

이 두 함수는 전체 데이터 중 이 값이 몇 번쨰에 있는지 보는 함수이다.(윈도우 함수로 분류되는 이유)

```sql
NTILE(4) OVER(ORDER BY 총구매금액) AS 총구매금액_4분위
```
`NTILE(n)`은 전체 데이터를 조건 기준으로 n등분해 지금 행의 데이터가 몇 번째에 있는지를 반환한다. 

순서는 먼저 나온 데이터가 1, 마지막에 나온 데이터가 n을 받는다.

예시의 경우, 총구매금액 기준으로 4등분해 1분위는 가장 구매금액이 낮은 경우, 4분위가 가장 구매금액이 높은 데이터 기준임

```sql
PERCENT_RANK() OVER(ORDER BY price DESC) AS 가격순위
```

`PERCENT_RANK()`는 `NTILE()`보다 자유도가 더 높고, 자세하게 볼 수 있다.

전체 데이터에 0부터 1 사이의 값을 할당하는데, 가장 먼저 나온 데이터에 0, 마지막 데이터에 1을 할당하고 그 사이는 0.2, 0.5, 0.99 등을 준다.

데이터의 **백분위 순위**를 알 수 있다.

원본 데이터가 수정되더라도 다시 백분위 순위를 수정해 줘 계속 최신화된 결과를 받아 볼 수 있음


### **`FIRST_VALUE()/LAST_VALUE()`**

처음/마지막 값을 가져온다.

```sql
FIRST_VALUE(product_name) OVER(
  PARTITION BY 카테고리
  ORDER BY price DESC
  ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING
)
```

주로 `PARTITION BY`와 같이 써서, 어느 구역에서의 최대/최소값, 가장 빠른/늦은 날짜 등을 구할 수 있다.

FIRST와 LAST는 중간에 ORDER BY의 순서를 반대로 주면 똑같은 함수임

ROWS BETWEEN으로 가져올 범위를 전체 데이터로 지정해 줘서 전체 데이터를 기준으로 하는 것이 좋다.

PostgreSQL에서는 뒤의 `UNBOUND FOLLOWING`을 `CURRENT ROW`로 가져왔음