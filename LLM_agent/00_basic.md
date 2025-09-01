# 랭체인

## 기초

LLM powered application 제작을 위한 프레임워크

**LLM** = 대형 언어 모델(Large Language Model), 수많은 텍스트 데이터를 학습하여 인간의 언어를 이해하고 생성할 수 있도록 만들어진 인공지능 모델

LLM을 외부 데이터 소스, 다른 기능들과 쉽게 연결 가능하게 하기 위해 만들어졌다.

- LangSmith

    LangChain으로 만든 애플리케이션을 디버깅, 테스트, 모니터링하기 위한 개발자용 플랫폼

    LLM에게 보낸 프롬프트부터 최종 답변까지 모든 단계를 추적하고 시각적으로 보여준다

- LangGraph

    LLM 워크플로우를 그래프로 모델링/실행하는 라이브러리.

    에이전트 시스템을 그래프(노드/엣지)형태로 정의하고 실행함

## 연결
```py
from dotenv import load_dotenv
load_dotenv()
```
미리 `.env` 파일에 사용할 LLM의 API 키를 저장해 놔야 한다.

```py
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model='gpt-4.1-nano')
```
gpt 4.1 nano 모델을 사용하겠다고 선언하는 것

Grok, Gemini 등의 다른 모델을 사용하고 싶으면 해당하는 랭체인 모듈을 깔아주면 된다.

## 체인

langchain의 각 구성요소를 묶어서(Chaining) 한번에 실행(invoke) 할 수 있는 기능

체인 기능을 이용해 단순한 질문답변이 아닌, 복잡한 워크플로우를 구성할 수 있다.

- `a | b | c | ...`의 형태 -> 파이썬 문법이 아니라, 랭체인 문법(*LangChain Expression Language*, LCEL)

- 기본적으로 프롬프트 - LLM - Parser 순으로 사용

- 파이프 안에 다른게 계속 들어갈 수 있다.

```py
from langchain_core.output_parsers import StrOutputParser

template = '<템플릿 내용>'
prompt = PromptTemplate.from_template(template)
chain = prompt | llm | StrOutputParser()  # 이게 체인
chain.invoke({'<빈칸>': '<물어볼 것>'})
# >> LLM이 생성한 답변
```

체인의 마지막에 `StrOutputParser()`를 묶으면 LLM이 생성한 답변을 **사람이** 보기 편하게 str로 바꿔 준다.

### Runnable

돌릴 수 있다를 의미하는 클래스. 자주 보이고 매우 중요함. 

내부에 invoke, batch, stream 등의 키 메서드가 존재.

체인 단계에서 사용되는 prompt, llm, parser 등은 모두 runnable을 구현한 객체이므로, 같은 방식으로 실행, 디버깅 등이 가능하다.

### invoke

입력 1개, 출력도 1개

```py
res = chain.invoke({'<빈칸>': '<물어볼 것>'})
print(res)
# >> LLM이 생성한 답변
```
대신, 전체 문자열을 다 받아서 한번에 리턴해준다

### stream

invoke와 다르게, 출력을 생성되는 대로 순차적으로 전달함.
```py
for token in chain.stream({'<빈칸>': '<물어볼 것>'}):
    print(token, end='^', flush=True)
# >> LLM^이 ^ 생성^한^ 답^변^
```
llm이 생성한 답변을 토큰 단위로 저장도 할 수 있다.

일반적으로 llm에서 답변 생성 시, 몇글자씩 생성해서 보여주는 것도 토큰 단위로 보여주는 것

### batch

입력 여러개를 받아서 답변 여러개를 출력함.

```py
res = chain.batch([
    {'<빈칸1>': '<물어볼것 1>'},
    {'<빈칸2>': '<물어볼것 2>'},
    {'<빈칸3>': '<물어볼것 3>'}
])
print(res)
# >>[답변1, 답변2, 답변3]
```

## 프롬프트

LLM에게 주는 입력(지시, 맥락, 기억)

- 맥락: 내가 ~~했어 -> 현재 지시를 위해 제공하는 추가정보

- 지시: ~~해줘

- 기억: 지금까지 한 대화내용 / 메모리

### 프롬프트 템플릿

고정된 문자열과 변수를 조합해 프롬프트를 만드는 것

고정된 문자열과 변수를 합쳐서 LLM에 들어갈 템플릿을 만들고, 변수에 들어갈 값을 합치면 프롬프트가 된다.

```py
from langchain_core.prompts import PromptTemplate

template = '{변수}에 대한 문자열'
prompt = PromptTemplate.from_template(template)
```
프롬프트를 만드는 방법은 매우 많음. 다른 코드를 써도 같은 동작을 하는 경우도 많다.

```py
prompt = PromptTemplate(
    template=template,
    input_variables=['변수'],
    partial_variables={'부분변수': '값'}
)
```
Partial Variable, 부분변수는 파이썬에서 매개변수의 기본값처럼, 값이 들어오지 않을 경우 기본값을 설정해줄 수 있다.

Input Variable은 무조건 값이 들어가야함

`prompt.format(변수1='값', 변수2='값')` 처럼 템플릿이 어떻게 될 지 미리보기도 가능하다.

### `PromptTemplate()`

1회성 템플릿을 생성함

단순히 질문에 대한 답변만을 원한다면 충분한 기능을 할 수 있다.

### `ChatPromptTemplate()`

채팅을 주고받기 위한 템플릿을 생성함
- 대화 목록을 LLM에 주입 가능
- 채팅이기 때문에, 기본적으로 **role**과 **message**로 구성되어 있다.

```py
chat_template = ChatPromptTemplate.from_messages(
    [
        ('system', '너는 까칠한 AI 비서야. 이름은 {name}이야.'),
        ('human', '안녕!'),
        ('ai', '무엇을 도와드릴까요?'),
        ('human', '{user_input}'),
    ]
)

messages_str = chat_template.format(name='gaida', user_input='너의 이름은 뭐니?')
messages_cls = chat_template.format_messages(name='gaida', user_input='너의 이름은 뭐니?')
```
만든 템플릿을 그냥 저장하는것과, `messages`로 저장하는 것 사이에는 매우 큰 차이가 생김

- 그냥 저장하면 LLM은 인풋 하나에 한 덩이로 모든 대화 내용을 적게 된다.
    - 대신 템플릿을 출력하면 사람이 보기 훨씬 편함

- 메시지로 저장하면 AI가 이를 human과 AI의 대화 내역으로 인식하게 된다.
    - 템플릿 출력 시 langchain class 형태로 보기 불편하다


### Few Shot Prompting

내가 원하는 답변을 설명하는 것이 아닌, 예시를 주고 답변을 유도하는 것

- Zero Shot Prompting
  - 예시 없이 질문만 던지는 것

- One Shot Prompting
  - 예시 1개 + 질문을 제공, 예시를 모방해 답변

- **Few Shot Prompting**
  - 예시 여러개 + 질문, 예시들의 패턴을 일반화 해서 답변

당연히 예시가 늘어날수록 원하는 답의 형태가 나올 가능성이 높아진다.

다만, 예시가 늘어날수록 프롬프트도 길어지고 복잡해짐

## [랭체인 허브](https://smith.langchain.com/hub)

다른 사용자들이 열심히 입력해 업로드한 프롬프트를 받아와서 사용 가능하다.

```py
from langchain import hub

prompt = hub.pull('<프롬프트 이름>')
print(prompt.template)
```
반대로, 내가 열심히 작성한 프롬프트를 랭체인 허브에 올리는 것도 가능함

