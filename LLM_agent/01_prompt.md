# 프롬프트

LLM에게 주는 입력
- 맥락: 내가 ~~했어 -> 현재 지시를 위해 제공하는 추가정보
- 지시: ~~해줘
- 기억: 지금까지 한 대화내용 / 메모리

LLM이 최적의 답변을 생성하도록 유도하기 위해 입력을 체계적으로 관리, 생성하는 것이 프롬프트 엔지니어링

##  프롬프트 템플릿

고정된 문자열과 변수를 조합해 프롬프트를 만드는 것

고정된 문자열과 변수를 합쳐서 LLM에 들어갈 템플릿을 만들고, 변수에 들어갈 값을 합치면 프롬프트가 된다.

### `PromptTemplate()`

1회성 템플릿을 생성함

단순히 질문에 대한 답변만을 원한다면 충분한 기능을 할 수 있다.

```py
from langchain_core.prompts import PromptTemplate

template = "{country}의 수도는 어디야?"
prompt = PromptTemplate.from_template(template)
```

```py
chain = prompt | llm | StrOutputParser()
chain.invoke({"country": "대한민국"})
```
`chain.invoke({"country": "대한민국"})` 실행 시 

1. **"대한민국의 수도는 어디야?"** 라는 프롬프트가 생성되고, 
2. LLM에 위 프롬프트가 들어가고
3. 결과를 str로 바꿔서 출력한다.

프롬프트를 만드는 방법은 매우 많고, 다른 코드를 써도 같은 동작을 하는 경우도 많음

#### 부분 변수
Partial Variable(부분변수)는 파이썬에서 매개변수의 기본값처럼 값이 들어오지 않을 경우 기본값을 설정해줄 수 있다.

프롬프트 템플릿의 특정 변수에 미리 값을 할당하거나, 실행 시점에 동적으로 값을 채울 때 유용합니다.

-   **고정값 할당**: `partial_variables`에 딕셔너리 형태로 값을 미리 지정

    ```python
    from langchain_core.prompts import PromptTemplate

    prompt = PromptTemplate(
        template="{country1}과 {country2}의 수도를 각각 알려줘",
        input_variables=['country1'],
        partial_variables={'country2': '미국'}
    )
    chain.invoke({'country1': '한국'})
    ```
    invoke 시 `country1` 값만 넘겨주면 `country2`는 '미국'으로 자동 완성됨
    
    단, `Input Variable`은 무조건 값이 들어가야함

-   **동적 값 할당**: `lambda` 함수 등을 사용하여 프롬프트가 실행될 때마다 동적으로 변수 값을 생성

    ```python
    from langchain_core.prompts import PromptTemplate
    from datetime import datetime

    prompt = PromptTemplate(
        template="오늘은 {today}입니다. 오늘 생일인 유명인을 알려주세요.",
        input_variables=[],
        partial_variables={'today': lambda: datetime.now().strftime('%B %d')}
    )
    ```

    실행 시점의 날짜가 today 변수에 자동으로 채워짐

### `ChatPromptTemplate()`

채팅을 주고받기 위한 템플릿을 생성함

- 대화 목록을 LLM에 주입 가능

- 채팅이기 때문에, 기본적으로 **Role**과 **Message**로 구성되어 있다.
  - Role: 각 화자의 역할을 보여줌. 
    - `system`: AI의 역할, 정체성, 지시사항 등등 (예: "너는 친절한 AI 비서야.")
    - `human`: 사용자의 입력
    - `ai`: AI의 답변
  - message: 이전 대화 기록을 말한다. 직접 지정할 수도 있고, 메모리를 이용해 자동으로 입력할 수도 있음 

```py
from langchain_core.prompts import ChatPromptTemplate

chat_template = ChatPromptTemplate.from_messages([
    ('system', '너는 까칠한 AI 비서야. 이름은 {name}이야.'),
    ('human', '안녕!'),
    ('ai', '무엇을 도와드릴까요?'),
    ('human', '{user_input}'),
])

messages_str = chat_template.format(name='gaida', user_input='너의 이름은 뭐니?')
messages_cls = chat_template.format_messages(name='gaida', user_input='너의 이름은 뭐니?')
```
만든 템플릿을 그냥 저장하는것과, `messages`로 저장하는 것 사이에는 매우 큰 차이가 생김

- 그냥 저장하면 LLM은 인풋 하나에 한 덩이로 모든 대화 내용을 적게 된다.
    - 대신 템플릿을 출력하면 사람이 보기 훨씬 편함

- 메시지로 저장하면 AI가 이를 human과 AI의 대화 내역으로 인식하게 된다.
    - 템플릿 출력 시 langchain class 형태로 보기 불편하다



## Few-Shot Prompting

LLM에게 원하는 결과물의 **예시(Example)**를 여러 개 제공하여, 질문에 대한 답변의 형식이나 스타일을 유도하는 기법

-   **Zero-Shot**: 예시 없이 질문만 하는 방식 (가장 일반적).
-   **One-Shot**: 1개의 예시를 제공하여 모방하게 함.
-   **Few-Shot**: 여러 개의 예시를 제공하여 패턴을 학습하고 일반화하도록 유도.

특정 인물의 말투를 흉내 내거나, 복잡한 형식의 텍스트(예: 회의록 요약)를 일관되게 생성하고 싶을 때 매우 유용함

단, 예시가 늘어날수록 프롬프트가 길어지고 복잡해진다(= 토큰을 많이 쓴다)


## [랭체인 허브](https://smith.langchain.com/hub)

다른 사용자들이 열심히 입력해 업로드한 프롬프트를 받아와서 사용 가능하다.

```py
from langchain import hub

prompt = hub.pull('<프롬프트 이름>')
print(prompt.template)
```
반대로, 내가 열심히 작성한 프롬프트를 랭체인 허브에 올리는 것도 가능함


## 메모리

대화 내용을 기억하는 것을 의미한다.

- LLM은 기본적으로 대화 내용을 기억하지 않음(stateless) - input 1번에 output 1번

- 계속 이전 대화내용을 프롬프트에 넣어줘야함

1. Short-Term Memory(단기기억)

    한 대화 세션 안에 대한 기억

2. Long-Term Memory(장기기억)

    전체 세션에서 추출한 중요한 정보 - GPT에서 메모리 업데이트됨 같은것

### 메모리 저장 위치

1. 디스크

    속도가 느림 / 저장하기 좋다(안정성이 높음)

    기본적인 모든 데이터 저장

2. 램

    엄청 빠름 / 컴퓨터가 꺼졌다 켜지면 사라짐(휘발성)

    자주 확인하는 데이터, 지금 사용하는 데이터들은 램에 저장 => **캐싱**

### 메모리 구동 방식

기본적으로 대화 내용은 전부 프롬프트에 집어넣는다. 

모든 대화 내용을 변수에 저장하는 방법이 `ConversationBufferMemory` 방식

즉, 대화가 길어지면 길어질수록, 소비하는 토큰 숫자가 늘어나고 + 뒤로 갈수록 성능도 낮아짐

이걸 늦추기 위한 다양한 방법이 있음

- 대화 요약 생성 후 요약을 저장(Conversation Summary Memory)
- 최근 K개의 상호작용만 사용함(Conversation Buffer Window Memory)
- 토큰 길이를 이용해 대화내용을 플러시할 시기를 결정(Conversation Token Buffer Memory)
- ...등등

다만, 이젠 볼 일이 없다. 전부 **LangGraph**로 기능이 넘어감.