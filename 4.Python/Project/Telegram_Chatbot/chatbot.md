# 챗봇 만들기

목표: 텔레그램에서 돌아가는 챗봇을 만든다

api에 대한 개념은 lotto.md와 09-requests.md 참조

먼저, 텔레그램 봇을 만들어 보자.

텔레그램 BotFather에게 /newbot(이거도 API)을 통해 새로운 봇을 만들고, 별명과 이름을 설정해 주면 나에게 봇 토큰을 준다.

이 봇 토큰을 따로 변수로 저장해 주고, 텔레그램 공식 홈페이지에 있는 봇 api 사용설명서를 

https://core.telegram.org/bots/api#getupdates




postman: api를 좀 더 편하게 지정할 수 있는 툴

만든 api를 저장/수정하기 편하다








텔레그램 gui를 이용하는 일반사용자 -> 텔레그램 서버의 텔레그램 봇 -> 우리가 만든 서버에서 돌아갈 챗봇 프로그램




사람 손으로 텔레그램 서버에 api를 보내면, 텔레그램 봇이 일반사용자에게 메시지를 보내는 것까진 성공

근데 이게 챗봇이라 하기엔 챗-휴먼이라 조금 이상

진짜 챗봇처럼 자동으로 돌아가게 하려면, 텔레그램 서버를 클라이언트로, 우리 노트북을 서버로 만들어서 우리가 데이터를 받으면 자동으로 반응이 오게끔 만들어 줘야함



127.0.0.1 -> IP, localhost -> 상대적인 '나'라는 뜻

누가 '나'라고 말하는지에 따라 그 대상이 달라지듯, 그냥 자기 각자 컴퓨터라는 뜻이다




/docs
fastapi 기본 제공, 모든 정보 제공

/
home

/hi
거기에 따른 대답



노트북은 외부에서 들어오는 요청을 전부 차단

-> NGROK을 이용, 우회

-> 외부에서 들어온 요청을 ngrok 프로그램으로 쏴주고, ngrok에서 내 노트북 서버로 쏴주는 식

텔레그램 api로 /setWebhook 기능을 이용, url을 ngrok 서버로 설정하면 외부외 연결이 가능하다



## .env 파일

api key 등 중요정보들을 모아놓는 파일

이건 깃헙에 올라가거나 하면 큰일남

git은 이걸 무시해야 함

.gitignore에 넣어두자

gitignore 관리 -> https://www.toptal.com/developers/gitignore

이제 중요 파일에서 비싼 키 같은걸 .env에 정리할 수 있다.

pip install dotenv

누가봐도 .env를 쓰기 위해 만든 모듈

dotenv.loadenv() 에서 .env파일을 로딩 가능하고, os.getenv('')에서 필요한 키 값을 로드 가능함

## LLM과 연결하기

pip install openai

openai 키를 .env에서 가져오고, 이걸 이용해 챗gpt를 챗봇 머리로 설정할 수 있다.
