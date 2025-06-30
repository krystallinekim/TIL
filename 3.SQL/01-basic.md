# SQL

서버-클라이언트

서버는 클라이언트(나)가 요청한 무언가를 가져다 주는 존재


## SQL 문법
`;` : 현재 줄의 명령어를 실행, 코드 하나에 하나씩 무조건 있어야 함

-> 하나의 작업과 다음 작업의 구분을 무조건 ;로 하기 때문

`--` : 주석처리

`' '` : 텍스트 처리 -> ***글자는 무조건 감싸야 함!!!***

```sql
-- 버전 확인
SELECT VERSION();
```


**CRUD**

Create - 생성

Read/Retrieve - 조회

Update - 변경

Delete - 삭제

모든 데이터베이스는 이렇게 4개의 operation으로 이루어져 있음




**대문자/소문자**

정해진 명령어는 대문자, 내가 선택한 건 소문자(국룰)

## DB

DB(데이터베이스) = 데이터가 모이면 전부 DB

```sql
-- DB 생성
CREATE DATABASE 이름; 
SHOW DATABASES;
DROP DATABASE 이름;
USE 이름;   
```

## 테이블

`PRIMARY KEY`

어떤 테이블에도 primary key는 어지간하면 존재해야 한다

언제나 하나만 존재함

`AUTO_INCREMENT`

숫자가 자동으로 증가함 -> 주로 ID 등에서 쓰임

`NOT NULL`

빈칸 안됨, 빈칸이면 에러남

`UNIQUE`

절대 중복 불가 -> 스프레드시트에서 검증하던걸 애초에 방지



`DEFAULT`

기본값을 넣어줌 -> NOT NULL과 대체되는 개념임 -> 빈칸일때 특정값을 보여줄지 / 에러를 반환할지

