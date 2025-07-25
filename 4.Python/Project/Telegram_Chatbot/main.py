# main.py
from fastapi import FastAPI, Request
import random
import requests
from dotenv import load_dotenv
import os

# FastAPI를 통해 실행
app = FastAPI()

# .env 파일 내용 불러옴
load_dotenv()

# 함수부

# chat_id, message를 받아서 그대로 보내주기
def sendmessage(chat_id, output_msg):
        # 봇에게 답장을 보내기
    bot_token = os.getenv('TELEGRAM_BOT_TOKEN')
    URL = f'https://api.telegram.org/bot{bot_token}'
    body = {
        'chat_id': chat_id,
        'text': output_msg,
    }
    requests.get(URL + '/sendMessage', body)

# 메시지 내용 따로 함수로 뺐음
def message_detail(message):
    if message[:6] == '/echo ':
        output_msg = message[6:]
    elif message in ['안녕', 'hi']:
        output_msg = '안녕하지 못하다냥'
    else:
        output_msg = '잘 모르겠다냥'
    return output_msg
    
# /telegram 라우팅으로, 텔레그램 서버가 봇에 업데이트가 있을 경우 우리에게 알려줌 -> 이건 post로 쏴준다. app.post 써야됨(setWebhook 설명서에 써있음.)
@app.post('/telegram')
async def telegram(request: Request):
    # print문은 로그에서 그 내용이 보일 것
    print('텔레그램에서 요청이 들어옴')
    # chat_id와 message를 만들어 보자
    data = await request.json()
    chat_id = data['message']['chat']['id']
    message = data['message']['text']
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
    