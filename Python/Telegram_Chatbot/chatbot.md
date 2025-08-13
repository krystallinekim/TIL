# 텔레그램과 OpenAI를 이용한 챗봇 만들기

## 1. 목표

- 텔레그램에서 동작하는 자동 응답 챗봇 제작
- **FastAPI**로 서버 구성
- **Telegram Bot API**로 메시지 송수신
- **OpenAI API**로 대화 응답 생성


## 2 텔레그램 봇 생성
1. **Telegram**에서 `@BotFather` 검색
2. `/newbot` 입력
3. 봇의 이름과 사용자명 입력
4. 발급받은 **봇 토큰** 저장 (API 호출 시 필요)

- [Telegram Bot API 문서](https://core.telegram.org/bots/api) 참고 -> 개발에 필요한 모든 API가 적혀 있음

## 3 개발 도구
- **Postman**: API 요청을 테스트하고 저장·관리하는 툴
- **Ngrok**: 로컬 서버를 외부에서 접근 가능하게 하는 터널링 서비스

## 4. 챗봇 동작 흐름
```
사용자 (Telegram App)
   ↓
텔레그램 서버
   ↓
우리 서버(FastAPI + 챗봇 프로그램)
   ↓
OpenAI API
   ↓
응답 생성 → 텔레그램 서버 → 사용자
```

## 5. 환경 변수 관리

- API 키나 토큰은 `.env`에 보관
- `.gitignore`에 `.env` 추가해 GitHub에 올라가지 않게 함 - [gitignore 관리 사이트](https://www.toptal.com/developers/gitignore) 이용

- `.env` 예시:

    ```env
    TELEGRAM_BOT_TOKEN = <텔레그램 봇 토큰>
    OPENAI_API_KEY = <OpenAI 키>
    ```
- 로드:
    ```python
    from dotenv import load_dotenv
    import os

    load_dotenv()
    BOT_TOKEN = os.getenv('TELEGRAM_BOT_TOKEN')
    OPENAI_API_KEY = os.getenv('OPENAI_API_KEY')
    ```

## 6. Webhook 설정

### 로컬 서버 외부 접근 문제
- 노트북이나 개인 PC는 외부에서 직접 접근할 수 없음
- 이를 해결하려면 **Ngrok**을 사용해 임시로 외부 접속 가능한 URL 생성

### Ngrok 실행

bash에서 직접 실행해야함
```bash
ngrok http 8000
```

1. Ngrok가 제공하는 `https://랜덤주소` 복사
2. 텔레그램 Webhook 등록
    
    `https://api.telegram.org/bot<봇토큰>/setWebhook?url=https://랜덤주소/telegram`
    

## 7. 코드 (`main.py`)
```python
import os, random, requests
from fastapi import FastAPI, Request
from dotenv import load_dotenv
from openai import OpenAI

app = FastAPI()
load_dotenv()

BOT_TOKEN = os.getenv('TELEGRAM_BOT_TOKEN')
OPENAI_API_KEY = os.getenv('OPENAI_API_KEY')

def send_msg(chat_id, text):
    url = f'https://api.telegram.org/bot{BOT_TOKEN}/sendMessage'
    requests.get(url, {'chat_id': chat_id, 'text': text})

def gpt_reply(msg):
    client = OpenAI(api_key=OPENAI_API_KEY)
    res = client.responses.create(model='gpt-4.1-mini',
                                  input=msg,
                                  instructions='너는 츤데레 여고생이야',
                                  temperature=1.0)
    return res.output_text

@app.post('/telegram')
async def telegram(req: Request):
    data = await req.json()
    chat_id = data['message']['chat']['id']
    msg = data['message']['text']
    send_msg(chat_id, gpt_reply(msg))
    return {'status': 'ok'}
```


## 8. 실행
```bash
uvicorn main:app --reload
```
- FastAPI 서버 실행
- Ngrok 실행 상태 유지
- Webhook이 등록되어 있으면 텔레그램 대화창에서 자동 응답 가능

---

## 9. API 흐름

1. **사용자**가 텔레그램 앱에서 봇에게 메시지 전송

1. 텔레그램 서버가 해당 메시지를 JSON으로 변환해 `/telegram` 엔드포인트로 POST 요청

1. FastAPI 서버(내가 연 것)가 메시지를 받아 OpenAI API에 전달

1. OpenAI API가 응답 생성

1. 내 서버가 텔레그램 API의 `/sendMessage`를 호출해 사용자에게 응답 전송

1. **사용자**는 챗봇 응답을 수신