# 랭체인

## 기초

LLM powered application의 제작을 위한 프레임워크

LLM을 외부 데이터 소스, 다른 기능들과 쉽게 연결 가능하게 하기 위해 만들어졌다.

- LangSmith

    LangChain으로 만든 애플리케이션을 디버깅, 테스트, 모니터링하기 위한 개발자용 플랫폼

    LLM에게 보낸 프롬프트부터 최종 답변까지 모든 단계를 추적하고 시각적으로 보여준다

- LangGraph

    LLM 워크플로우를 그래프로 모델링/실행하는 라이브러리.

    에이전트 시스템을 그래프(노드/엣지)형태로 정의하고 실행함

    **노드** -> (prompt | llm | parser) 체인 하나를 하나의 노드라고 정의함

    첫 노드의 결과를 다음 결과의 노드에 입력하거나, 조건문을 통해 다음 입력할 노드를 선택하는 등 연결을 해주는 게 LangGraph

## LLM

대형 언어 모델(Large Language Model), 수많은 텍스트 데이터를 학습하여 인간의 언어를 이해하고 생성할 수 있도록 만들어진 인공지능 모델

```py
from dotenv import load_dotenv
load_dotenv()
```
미리 `.env` 파일에 사용할 LLM의 API 키를 저장해 놔야 한다.

수업에서는 OpenAI의 LLM을 이용했고, 모델은 보통 `gpt-4.1-nano`를 이용함

```py
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model='gpt-4.1-nano')
```
gpt 4.1 nano 모델을 사용하겠다고 선언하는 것

Grok, Gemini 등의 다른 모델을 사용하고 싶으면 해당하는 랭체인 모듈을 깔아주면 된다.

### temperature

사전적 의미는 랜덤성을 의미한다. temperature가 높을수록 보통 선택되지 않는 단어들(확률이 낮은 단어)도 사용 가능해짐

일반적인 의미로 보면 다음과 같다.

temperature 높음
- 설명을 하기 위해 다양한 언어를 사용
- 지루하고 현학적임
- 창의적, 예술적이거나 생각이 필요한 경우는 temp가 높아야함

temperature 낮음
- 팩트 위주
- 딱 사실만 반영
- 논문 참조, 정보 확인 등 정확성이 중요할 경우 temp가 낮은게 맞음

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