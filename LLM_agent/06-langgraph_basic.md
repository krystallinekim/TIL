# LangGraph

- [Langchain Academy](https://langchain-ai.github.io/langgraph/concepts/why-langgraph/)
- [Official Documentation](https://python.langchain.com/docs/how_to/)

## 기본 개념

상태(State)를 기반으로 LLM 워크플로우를 구축하기 위한 프레임워크

순환, 조건을 포함하는 복잡한 로직을 에이전트처럼 구현할 수 있게 해준다.

그래프 구조(Node / Edge)를 사용하여 챗봇이나 에이전트의 흐름을 정의

### **State**

그래프의 전체 상태를 나타내는 데이터 구조. 각 노드(Node)가 데이터를 공유하고 업데이트하는 중앙 허브 역할을 한다.
    - 상태 정의 시 `TypedDict`를 사용하면 가볍게 타입 힌트만 제공할 수 있고, `Pydantic` 모델을 사용하면 데이터 유효성 검증(validation)까지 포함하는 더 엄격한 상태 관리가 가능하다.

```py
class State(TypedDict):
    graph_state: str
    history: list
```

### **Node**

그래프의 각 단계를 수행하는 함수 또는 실행 가능한 객체. 각 노드는 현재 `State`를 입력으로 받아 비즈니스 로직을 수행하고, 수정된 `State`의 일부 또는 전체를 반환한다.

```py
# 일반 노드
def node_1(state: State):
    new_str = state['graph_state'] + ' I am '
    state['history'].append('node1')
    return {'graph_state': new_str, 'history': state['history']}

# 조건부 분기 노드
def decide_mood(state: State) -> Literal["node_2", "node_3"]:
    if len(state["graph_state"]) % 2:
        return "node_2"
    else:
        return "node_3"
```
### **Edge**

노드 간의 제어 흐름을 정의하며, 다음에 실행될 노드를 지정함

- **`START`**: 그래프의 시작 노드를 지정합니다.
- **`add_edge()`**: 한 노드에서 다른 노드로의 **무조건적인** 연결을 추가합니다.
- **`add_conditional_edges()`**: 특정 노드의 실행 결과(State)를 바탕으로, **조건에 따라** 다음에 실행될 노드를 동적으로 결정
- **`END`**: 그래프의 종료 지점을 지정합니다.

```py
# Edge 생성 = 그래프 그리기
builder = StateGraph(State)
builder.add_node("node_1", node_1)
builder.add_node("node_2", node_2)
builder.add_node("node_3", node_3)

builder.set_entry_point("node_1")
builder.add_conditional_edges("node_1", decide_mood) # node_1 다음에 decide_mood 결과에 따라 분기
builder.add_edge("node_2", END)
builder.add_edge("node_3", END)
```

그래프를 정의한 후에는 `compile()` 메서드를 호출하여 실행 가능한 객체로 만들고, `invoke()`를 통해 실행
```py
graph = builder.compile()
graph.invoke({"graph_state": "안녕", "history": []})
```

## 대화 기록 누적

LangGraph를 사용하면 대화의 상태(State)를 체계적으로 관리하며 챗봇을 쉽게 만들 수 있습니다. 상태는 그래프의 노드(Node) 사이를 흐르는 데이터의 역할을 합니다. 이를 통해 대화 기록 저장, 특정 페르소나 부여, 컨텍스트 윈도우 관리 등의 기능을 명확하게 구현할 수 있습니다.

대화 기록을 `State`에 추가하게 되면 간단한 챗봇 체인을 만들 수 있다.

- **`MessagesState`**: 대화 기록을 관리하기 위한 `State`입니다

  - `Annotated`와 `add_messages` 같은 헬퍼 함수를 함께 사용하면, 매번 `state['messages'] + new_messages`와 같은 번거로운 작업을 하지 않아도 새로운 메시지가 상태에 자동으로 누적되도록 할 수 있어 편리함

```python
from typing import Annotated
from langgraph.graph.message import add_messages
from langchain_core.messages import AnyMessage

class MessagesState(TypedDict):
    # messages 키에 담기는 리스트에 add_messages가 적용되어 메시지가 자동으로 추가됨
    messages: Annotated[list[AnyMessage], add_messages]

# LLM을 호출하는 노드
def llm_node(state: MessagesState):
    response = llm_with_tools.invoke(state["messages"])
    return {"messages": response}

# 그래프 빌드
builder = StateGraph(MessagesState)
builder.add_node("llm_node", llm_node)
builder.set_entry_point("llm_node")
builder.add_edge("llm_node", END)
graph = builder.compile()
```
이 구조는 사용자 입력을 받아 LLM이 답변하고, 이 대화 과정이 `messages` 상태에 자동으로 누적되는 간단한 체인을 형성합니다.



### 기본 챗봇 구조와 메모리

`MessagesState`와 `MemorySaver`를 사용하여 대화 기록을 관리하는 챗봇을 만들 수 있습니다. 각 대화는 고유한 `thread_id`를 통해 구분되어, 여러 사용자와의 동시적인 대화나 여러 개의 대화 주제를 독립적으로 관리할 수 있습니다.

- **`MessagesState`**: LangGraph의 내장 상태(State) 중 하나로, `TypedDict` 형태로 `messages` 키를 가지고 있습니다. 노드 간에 대화 메시지 목록이 이 상태를 통해 전달되고 업데이트됩니다.
- **`MemorySaver`**: 대화 내용을 인-메모리(in-memory)에 저장하여, 이전 대화를 기억하게 해주는 역할을 합니다. `checkpointer`에 연결되며, 프로토타이핑에 유용합니다. 실제 프로덕션 환경에서는 Redis나 데이터베이스 기반의 영구적인 Checkpointer 사용이 권장됩니다.

```python
from langgraph.checkpoint.memory import MemorySaver
from langgraph.graph import START, END, MessagesState, StateGraph
from langchain_openai import ChatOpenAI

llm = ChatOpenAI(model='gpt-4.1-nano')

# 그래프 빌더 정의. 어떤 상태 스키마를 사용할지 지정합니다.
builder = StateGraph(state_schema=MessagesState)

# LLM을 호출하는 간단한 노드. 상태를 입력받아 처리 후, 변경된 상태를 반환합니다.
def simple_node(state: MessagesState):
    res = llm.invoke(state['messages'])
    return {'messages': res} # 변경된 부분만 반환하면 LangGraph가 상태를 업데이트합니다.

# 노드와 엣지(흐름) 추가
builder.add_node('simple_node', simple_node) # 노드 등록
builder.add_edge(START, 'simple_node')      # 시작점에서 'simple_node'로 이동
builder.add_edge('simple_node', END)        # 'simple_node'가 끝나면 종료

# 메모리 설정
memory = MemorySaver()

# 그래프 컴파일. Checkpointer를 연결하여 대화 기록을 저장할 수 있게 합니다.
graph = builder.compile(checkpointer=memory)

# 실행 (thread_id로 대화 채널을 구분)
# 동일한 thread_id를 사용하면 이전 대화 내용이 이어집니다.
config = {'configurable': {'thread_id': 'user_123'}}
graph.invoke({'messages': [HumanMessage(content='Hi, I am Bob.')]}, config=config)
```

### 프롬프트 템플릿으로 페르소나 부여하기

`ChatPromptTemplate`과 `MessagesPlaceholder`를 사용하면 챗봇에게 시스템 레벨의 지시(예: "해적처럼 말해")를 내려 역할을 부여할 수 있습니다.

- **`MessagesPlaceholder`**: 프롬프트 템플릿 내에서 실제 대화 기록이 동적으로 삽입될 위치를 지정합니다. 이를 통해 시스템 메시지와 대화 기록을 결합하여 LLM에 전달할 수 있습니다.

```python
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder

prompt_template = ChatPromptTemplate.from_messages([
    ('system', '너는 해적처럼 말해야 해. 대항해시대의 해적을 최대한 따라해 봐.'),
    MessagesPlaceholder(variable_name='messages'),
])

# 노드에서 프롬프트와 LLM을 파이프라인(|)으로 연결
def simple_node(state: MessagesState):
    # state 딕셔너리가 'messages' 키를 가지고 있으므로,
    # prompt_template.invoke(state) 호출 시 MessagesPlaceholder가 채워집니다.
    chain = prompt_template | llm
    res = chain.invoke(state)
    return {'messages': res}
```

### 대화 기록 관리 (`trim_messages`)

대화가 길어지면 LLM의 최대 컨텍스트 길이를 초과하여 에러가 발생할 수 있습니다. `trim_messages`를 사용하면 토큰 수를 기준으로 메시지를 동적으로 잘라내어 이 문제를 효과적으로 방지할 수 있습니다.

```python
from langchain_core.messages import trim_messages

trimmer = trim_messages(
    strategy='last',        # 'last': 최근 메시지를 남김. 'first': 오래된 메시지를 남김.
    max_tokens=200,         # 허용할 최대 토큰 수
    token_counter=llm,      # 사용하는 모델에 맞춰 토큰 수 계산
    include_system=True,    # 시스템 메시지는 항상 포함
    allow_partial=False,    # 메시지 중간이 잘리지 않도록 함
    start_on='human',       # 잘린 메시지 목록이 항상 사용자 메시지로 시작하도록 보장
)

# 노드 안에서 LLM 호출 전에 메시지를 자름
def simple_node(state: Mystate):
    # LLM에 전달하기 전에 대화 기록을 적절한 길이로 자릅니다.
    trimmed_messages = trimmer.invoke(state['messages'])
    
    # 잘린 메시지를 포함하여 새로운 state를 구성합니다.
    new_state = state.copy()
    new_state['messages'] = trimmed_messages
    
    chain = prompt_template | llm
    res = chain.invoke(new_state)
    
    # LLM의 응답은 전체 대화 기록에 추가되어야 하므로, 원래 state에서 업데이트합니다.
    return {'messages': res}
```

#### 3. 대화 기록 저장 (LangGraph Memory)

`MemorySaver`를 사용하면 대화의 각 단계를 `Checkpointer`에 저장하여 대화 기록을 유지할 수 있습니다. 이를 통해 사용자가 이전 대화 내용을 참조하는 질문("방금 말한 거 자세히 설명해줘")을 해도 맥락을 이해하고 답변할 수 있습니다.

-   **Checkpointer**: `langgraph.checkpoint.memory.MemorySaver`는 대화 기록을 인-메모리에 저장하는 가장 간단한 Checkpointer입니다.
-   **세션 관리**: `graph.invoke()`를 호출할 때 `config` 딕셔너리에 `thread_id`를 지정합니다. 이 `thread_id`는 각 대화 채널을 구분하는 고유한 식별자 역할을 하여, 여러 사용자와의 대화를 독립적으로 관리할 수 있게 해줍니다.

```python
from langgraph.checkpoint.memory import MemorySaver

# 1. 메모리 설정
memory = MemorySaver()

# 2. 그래프 컴파일 시 checkpointer 연결
# 이전에 정의한 builder를 사용
graph = builder.compile(checkpointer=memory)

# 3. 대화 실행 시 thread_id 지정
# 동일한 thread_id를 사용하면 이전 대화가 이어집니다.
config = {'configurable': {'thread_id': 'my-thread-1'}}

# 첫 번째 질문
input1 = {'messages': [{'role': 'user', 'content': '에이전트 시스템에 대해 알려줘'}]}
res1 = graph.invoke(input1, config)

# 두 번째 질문 (이전 대화 내용을 기억함)
input2 = {'messages': [{'role': 'user', 'content': '방금 설명한 내용에서 Task decomposition이 뭐야?'}]}
res2 = graph.invoke(input2, config) 
```

## 라우터

LLM의 응답 내용에 따라 다음 작업을 동적으로 분기하는 개념. 

- **`llm.bind_tools()`**: LLM에 사용할 도구를 바인딩하여, LLM이 함수 호출(Tool Call) 형식으로 응답을 생성할 수 있게 합니다.
- **`ToolNode`**: LangGraph에서 제공하는 미리 정의된 노드로, LLM이 요청한 도구를 실제로 실행하고 그 결과를 반환하는 역할을 합니다.
- **`tools_condition`**: 미리 정의된 분기 함수로, LLM의 마지막 응답에 `tool_calls`가 포함되어 있는지 여부를 확인합니다.
    - `tool_calls`가 있으면: `ToolNode`로 라우팅하여 도구를 실행합니다.
    - `tool_calls`가 없으면: `END`로 라우팅하여 그래프를 종료하고 사용자에게 최종 답변을 반환합니다.

```python
from langgraph.prebuilt import ToolNode, tools_condition

# 그래프 빌드
builder = StateGraph(MessagesState)
builder.add_node("llm_node", llm_node)
builder.add_node("tools", ToolNode([multiply])) # 도구 실행 노드

builder.set_entry_point("llm_node")

# llm_node의 결과(tool_calls 유무)에 따라 'tools' 노드로 갈지, 끝낼지 분기
builder.add_conditional_edges(
    "llm_node",
    tools_condition,
)
builder.add_edge("tools", END) # 도구 실행 후 종료
graph = builder.compile()
```

### ReAct

라우터 개념의 확장형으로, **(Reason -> Act -> Observe)** 패턴을 의미한다.

에이전트가 여러 도구를 선택적으로 사용할 수 있게 함

- **ReAct 루프(Loop) 구현**:
    1.  **Reason (판단)**: `assistant` 노드에서 LLM이 현재 상태와 사용자 요청을 보고 어떤 행동을 할지 결정합니다. (도구 사용 or 답변)
    2.  **Act (행동)**: `tools_condition`에 의해 `tools` 노드로 라우팅되면, `ToolNode`가 LLM이 요청한 도구를 실행합니다.
    3.  **Observe (관찰)**: `tools` 노드의 실행 결과를 다시 `assistant` 노드로 전달하는 엣지(`builder.add_edge('tools', 'assistant')`)를 추가하여 루프를 만듭니다. LLM은 도구 실행 결과를 보고 다음 판단(Reason)을 이어갑니다.

- **메모리 추가**: `MemorySaver`를 `checkpointer`로 설정하여 그래프를 컴파일하면 대화 기록을 외부(메모리, DB 등)에 저장하고 불러올 수 있습니다. 이를 통해 에이전트는 이전 대화의 맥락을 기억하는 장기 기억을 갖게 됩니다.
- **스레드 ID**: `invoke` 시 `config={"configurable": {"thread_id": "user_123"}}`를 전달하여 각 사용자 또는 세션별로 대화 기록을 분리하여 관리할 수 있습니다.

### Observe
LangGraph 에이전트는 도구 사용 시 발생하는 오류를 스스로 인지하고 대처할 수 있습니다. 예를 들어, 도구가 정수(`int`)를 인자로 기대하는데 소수(`float`)가 입력되면 `ToolNode`에서 유효성 검사 오류가 발생합니다. 이 오류 메시지는 루프를 통해 다시 LLM(`assistant` 노드)에게 전달됩니다. LLM은 이 오류를 **관찰(Observe)**하고, 사용자에게 문제를 설명하며 어떻게 처리할지 되묻는 방식으로 상황을 해결해 나갑니다. 이는 에이전트가 더 똑똑하고 안정적으로 작동하게 만드는 중요한 기능입니다.

```python
# 그래프 빌드
builder = StateGraph(MessagesState)
builder.add_node("assistant", assistant_node) # Reason
builder.add_node("tools", ToolNode(tools))    # Act

builder.set_entry_point("assistant")
builder.add_conditional_edges(
    "assistant",
    tools_condition,
)
# 도구 실행 결과를 다시 assistant에게 전달 (Observe)
builder.add_edge("tools", "assistant") # Loop

# 메모리 추가하여 컴파일
memory = MemorySaver()
graph = builder.compile(checkpointer=memory)

# 스레드 ID를 사용하여 대화 실행
config = {"configurable": {"thread_id": "1"}}
graph.invoke({"messages": ["3에 4를 더해."]}, config=config)
# 이전 대화(결과: 7)를 기억하고 다음 계산을 수행
graph.invoke({"messages": ["거기다 2를 곱해."]}, config=config)
```
