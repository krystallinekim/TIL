# Requests

보통 맥도날드에 가서 햄버거를 주문하면, 점원은 햄버거를 요리해서 나에게 갖다준다.

이 때, 내가 주문하는 행위를 request, 점원이 나에게 햄버거를 가져다 주는 행위를 response로 볼 수 있다.

이처럼 웹 세상에서도 무언가 작동할 때, 그 동작은 Request / Response 둘 중 하나로 나뉜다.

## 기본개념

### 클라이언트와 서버

```
      클라이언트           --요청(Request)-->    서버  
(사람, 컴퓨터, 소프트웨어) <--응답(response)-- (소프트웨어)
```

클라이언트와 서버를 나누는 기준은, 대상이 요청을 했는지, 아니면 응답을 했는지로 나눌 수 있다.

클라이언트가 서버에게 무언가를 요청하고, 그에 따라 서버가 클라이언트에게 응답을 보내는 식

웹 세상에서는 모든 동작이 요청/응답 중 하나이기 때문에, 어떠한 행동의 주체는 모두 클라이언트 아니면 서버 둘 중 하나가 될 수 밖에 없다.

내가 브라우저를 통해 구글에 파이썬에 대해 검색을 했다고 생각해 보자.
1. 나는 브라우저에 검색하라고 요청했으므로 나는 클라이언트, 브라우저가 서버가 된다.
1. 브라우저는 구글 서버에 파이썬에 대한 정보를 요청했으므로 클라이언트, 반대로 구글 서버는 서버가 됨
1. 구글 서버는 구글 데이터베이스에 파이썬에 대한 정보를 달라고 할 것이기에 여기서는 구글 서버가 클라이언트가 된다.

이런 식으로, 어떠한 행동이 있다면 그에 따라 클라이언트와 서버가 결정되게 된다.

### URL

URL은 `Uniform Resource Locator`의 약자로, 특정 정보의 위치를 알려주는 주소라고 생각할 수 있다.

구글에 `python`이라고 검색하면, 주소창이 다음과 같이 바뀐다.(?와 &를 통해 정보를 구분할 수 있다.)
```
https://www.google.com/search
?
q=python 
&
oq=python
&gs_lcrp=EgZjaHJvbWUyDggAEEUYJxg5GIAEGIoFMgYIARBFGEEyBggCEEUYOzIGCAMQIxgnMg8IBBAAGBQYhwIYsQMYgAQyDAgFEAAYQxiABBiKBTIMCAYQABhDGIAEGIoFMgYIBxBFGDzSAQgzMzU2ajBqOagCALACAA
&
sourceid=chrome
&
ie=UTF-8
```

엄청 길지만, 결국 중요한건 내가 질문할 사이트(`https://www.google.com/search`), 내가 질문할 내용(`q=python`) 이 두개만 있다는 것을 볼 수 있다.

실제로, 검색창에 이 두개만 작성해서 검색해도 같은 사이트가 나온다.

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

### Get과 Post



## python에서의 request

일반적으로 웹 어딘가에 요청을 하고싶다면, 가장 많이 이용되는 클라이언트는 우리의 웹 브라우저(크롬, 엣지 등)가 될 것이다.

개발자들이 좀 더 편하게 요청을 하기 위해 만든 클라이언트가 `postman`으로, 조금 더 get


### 서버 만들기

현재 파일 위치로 이동 후 main.py가 ls에 나오는 데까지 간다
uvicorn, fastapi

uvicorn main:app --reload
main - 프로그램 이름
app - 변수명
```py
# main.py
from fastapi import FastAPI

app = FastAPI()

@app.get('/hi')
def hi():
    return {'status':'ok'}
```


## 응답코드

404 not found

200?
ok
잘 나왔을 때

400도 봤음
필요한 요청사항을 덜 썼을때
bad requests

얘네는 표처럼 4/04, 2/00, 4/00으로 읽어야 함
앞자리가 더 중요하다

1은 볼일없음
2는 좋음
3은 리다이렉트(볼일없음)
4는 나쁨 - request가 잘못됨 - 클라이언트 잘못
- 없는 url쓰기 등


5도 나쁨임 - response가 잘못됨 - 서버 잘못, 우리가 할수 있는게 없다

00은 그냥 -> 200은 그냥 좋음, 400은 그냥 나쁘다는것 -> 매우 무책임함
01부터는 무언가 정보가 들어있다

