# Requests

보통 맥도날드에 가서 햄버거를 주문하면, 점원은 햄버거를 요리해서 나에게 갖다준다.

이 때, 내가 주문하는 행위를 request, 점원이 나에게 햄버거를 가져다 주는 행위를 response로 볼 수 있다.

이처럼 웹 세상에서도 무언가 작동할 때, 그 동작은 Request / Response 둘 중 하나로 나뉜다.

## 기본개념

### 클라이언트와 서버

```
      클라이언트          -- 요청(Request) -->           서버  
(사람, PC, 앱, 브라우저)  <--응답(Response) --  (웹 서버, API 서버 등)
```

- **클라이언트(Client)**  
  - 요청을 보내는 주체  
  - 웹 브라우저(크롬, 엣지), 앱, 다른 서버 등  
- **서버(Server)**  
  - 요청을 받아 처리하고 응답하는 주체  
  - 웹 서버, 데이터베이스 서버, API 서버 등

> 같은 프로그램이라도 상황에 따라 클라이언트/서버 역할이 바뀔 수 있다.  
> 예: 브라우저가 구글 서버에 요청할 땐 클라이언트지만, 브라우저가 내 컴퓨터에서 HTML을 보여줄 땐 서버처럼 작동하기도 한다.


### URL (Uniform Resource Locator)

URL은 인터넷 자원의 **위치(주소)**를 나타낸다.

```
프로토콜://호스트/경로?쿼리스트링
https://www.google.com/search?q=python&hl=ko
```

- **프로토콜**: `https` → 데이터 전송 규칙  
- **호스트**: `www.google.com` → 서버 주소  
- **경로**: `/search` → 서버 내부에서 자원을 찾는 경로  
- **쿼리스트링(Query String)**: `q=python&hl=ko` → 요청에 필요한 추가 데이터

### API

API는 `Application Programming Interface`을 의미한다.

즉, 응용프로그램(application program)이 사용할 수 있는 인터페이스, 서로 다른 프로그램 간의 통신 규약이라고 할 수 있다.

예를 들어, URL에 `https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=1`처럼 검색하면

```py
{
    "totSellamnt":3681782000,       # Total Sell Amount, 총판매액
    "returnValue":"success",
    "drwNoDate":"2002-12-07",       # 추첨일
    "firstWinamnt":0,               # 1게임당 당첨금액
    "firstPrzwnerCo":0,             # 당첨게임 수
    "firstAccumamnt":863604600,
    "drwNo":1,                      # 추첨회차
    "drwtNo1":10,
    "drwtNo2":23,
    "drwtNo3":29,
    "drwtNo4":33,
    "drwtNo5":37,
    "drwtNo6":40,
    "bnusNo":16,                    # 당첨번호
}
```
동행복권 서버가 URL에서 주문한 내용을 알아듣고 필요한 데이터를 JSON 형식으로 보내주는 것을 볼 수 있다. 

우리는 분명 브라우저의 주소창에 입력을 했지만, 실제로 응답한 것은 동행복권 서버가 되는 식으로, 브라우저와 동행복권 서버 간에 통신이 일어난 것이다.

### HTTP 메서드 - GET/POST

HTTP 메서드는 "서버에 어떤 행동을 요청하는지"를 나타내는 방법이다.  
그중 **GET**과 **POST**가 가장 많이 쓰인다.

| 구분 | GET | POST |
|------|-----|------|
| **용도** | 데이터 조회 | 데이터 전송/저장 |
| **데이터 위치** | URL 쿼리스트링 | HTTP 요청 본문(Body) |
| **보안성** | 낮음 (주소창에 노출) | 높음 (본문에 숨김) |
| **캐싱** | 가능 | 일반적으로 불가능 |
| **제한** | URL 길이 제한 존재 | 상대적으로 제한 없음 |
| **예시** | 검색, 뉴스 기사 보기 | 회원가입, 로그인, 글 작성 |

- GET → 식당에서 "메뉴판을 보여달라" (정보를 가져옴)  
- POST → 식당에서 "이 음식 주문할게요" (데이터를 보냄)

#### GET

```python
import requests

url = "https://jsonplaceholder.typicode.com/posts/1"
res = requests.get(url)

print("상태 코드:", res.status_code)
print("응답 데이터:", res.json())
```

#### POST
```python
import requests

url = "https://jsonplaceholder.typicode.com/posts"
data = {
    "title": "Hello World",
    "body": "This is a test post",
    "userId": 1
}
res = requests.post(url, json=data)

print("상태 코드:", res.status_code)
print("응답 데이터:", res.json())
```

## 간단한 서버 만들기 (FastAPI)

```python
# main.py
from fastapi import FastAPI

app = FastAPI()

@app.get("/hi")
def say_hi():
    return {"message": "Hello, FastAPI!"}

@app.post("/echo")
def echo(data: dict):
    return {"you_sent": data}
```
실행:
```
uvicorn main:app --reload
```
- `main` → 파일명 (main.py)  
- `app` → FastAPI 인스턴스 이름

---

## HTTP 응답 코드 (Status Code)

| 상태 코드 | 의미 | 설명 |
|-----------|------|------|
| **200 OK** | 성공 | 요청 정상 처리 |
| **201 Created** | 생성됨 | POST 요청으로 새 자원이 만들어짐 |
| **204 No Content** | 내용 없음 | 성공했지만 응답 본문이 없음 |
| **301 Moved Permanently** | 영구 이동 | 요청한 자원이 다른 URL로 이동 |
| **400 Bad Request** | 잘못된 요청 | 요청 형식 오류, 데이터 누락 |
| **401 Unauthorized** | 인증 필요 | 로그인 필요 |
| **403 Forbidden** | 접근 거부 | 권한 없음 |
| **404 Not Found** | 찾을 수 없음 | 잘못된 URL |
| **500 Internal Server Error** | 서버 오류 | 서버 내부 문제 |
| **503 Service Unavailable** | 서비스 불가 | 서버 과부하, 점검 중 |

> - **1xx**: 정보성 - 볼 일 없음
> - **2xx**: 성공
> - **3xx**: 리다이렉션
> - **4xx**: 클라이언트 오류 (요청 문제)
> - **5xx**: 서버 오류 (응답 문제)
