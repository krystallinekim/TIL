# main.py

import os
import random
import requests
from fastapi import FastAPI, Request
from dotenv import load_dotenv
from openai import OpenAI

# FastAPI를 통해 실행
app = FastAPI()

# .env 파일 내용 불러옴
load_dotenv()

# 키값을 따로 빼놓음
BOT_TOKEN = os.getenv('TELEGRAM_BOT_TOKEN')
OPENAI_API_KEY = os.getenv('OPENAI_API_KEY')


# 함수부

# chat_id, message를 받아서 그대로 보내주기
def sendmessage(chat_id, output_msg):
        # 봇에게 답장을 보내기
    URL = f'https://api.telegram.org/bot{BOT_TOKEN}'
    body = {
        'chat_id': chat_id,
        'text': output_msg,
        
    }
    requests.get(URL + '/sendMessage', body)

# 받은 메시지에 따라 챗gpt가 결과 메시지를 보내줌
def message_detail(message):
    client = OpenAI(api_key=OPENAI_API_KEY)
    res = client.responses.create(
        model='gpt-4.1-mini',
        input=message   
    )
    return res.output_text
    
# /telegram 라우팅으로, 텔레그램 서버가 봇에 업데이트가 있을 경우 우리에게 알려줌 -> 이건 post로 쏴준다. app.post 써야됨(setWebhook 설명서에 써있음.)
@app.post('/telegram')
async def telegram(request: Request):
    # print문은 로그에서 그 내용이 보일 것
    print('텔레그램에서 요청이 들어옴')
    # 들어온 메시지의 정보에서 발신자 ID와 발신내용을 뽑음
    
    data = await request.json()
    chat_id = data['message']['chat']['id']
    message = data['message']['text']
    
    # 챗GPT를 이용해 대답을 생성
    output_msg = message_detail(message)
    sendmessage(chat_id, output_msg)
    return {'status':'ok'}



# 이 밑은 텔레그램 말고 따로 쓰는용도 ---------------------------------------

# 라우팅 없을 경우, 홈 화면을 보여줌
@app.get('/')
def home():
    return {'home': 'Sweet home'}

# /hi 라우팅을 통해 status ok를 보내줌
@app.get('/hi')
def hi():
    return {'status':'ok'}

# /lotto 라우팅으로 랜덤한 값 6개를 쏴줌
@app.get('/lotto')
def lotto():
    return {
        'numbers': random.sample(range(1, 46), 6)
    }
    